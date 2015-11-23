#!/usr/bin/env python
# coding: utf-8

import sys
import argparse
import urllib
import os
import os.path
import getpass
import codecs
import shutil
import time
import re
import json
from datetime import date

import github
from random import randint
from Crypto.Random.random import randrange

VERBOSE = True
DEFAULT_OUTPUT_PATH = '/home/taciano/dev/repos'
MAVEN_BIN = '~/dev/apache-maven-3.3.3/bin'
tamanhoPopulacao = -1
tamanhoAmostra = 50
buscarRepositorios = False
STRING_BUSCA = "jpa hibernate language:java"

def parseArgs():
    parser = argparse.ArgumentParser(
        description='Tool for extracting pull requests from GitHub. Downloads all open pull requests of the specified projects.')
    parser.add_argument('-a', '--authenticate', action="store_true", help="ask for username and password to get a higher rate limit from Github API")
    parser.add_argument('-o', '--outputPath', action="store", nargs="?", default=DEFAULT_OUTPUT_PATH, help="output path. If not specified will default to: " + DEFAULT_OUTPUT_PATH)
    parser.add_argument('-f', '--forceRedownload', action="store_true", help="force redownload of all files even if they are already present in the local filesystem")
    parser.add_argument('-n', '--numHibernateReposDesired', action="store", type=int, default=100, help="number of hibernate repositories desired")
    args = parser.parse_args()
    return args


def getCredentials():
    defaultUsername = os.getlogin()
    username = raw_input('username (default: %s): ' % defaultUsername)
    if not username.strip():
        username = defaultUsername

    password = getpass.getpass('password: ')
    return username, password


def initGithub(authenticate):
    if authenticate:
        username, password = getCredentials()
        return github.Github(username, password)
    else:
        return github.Github()


def run(cmd):
    if VERBOSE:
        print >>sys.stderr, cmd
    os.system(cmd)


def getLocalPath(project, downloadPath):
    return os.path.join(downloadPath, project[0])


def cloneGitRepo(project, downloadPath, forceRedownload):
    localPath = getLocalPath(project, downloadPath)

    if forceRedownload:
        shutil.rmtree(localPath, True)

    if os.path.isdir(localPath):
        print >>sys.stderr, 'Repo %s already exists. Not downloading it again...' % project[0]
        return

    run('git clone --depth 1 %s %s' % (project[1], localPath))


def javaCodeHasHibernateReferences(path):
    with open(path) as fin:
        for line in fin.readlines():
            if re.match(".*@Entity.*", line):
                return True
    return False


def isProjectThatUsesHibernate(project, downloadPath):
    print "Parsing java source code of %s..." % (project[0])
    localPath = getLocalPath(project, downloadPath)
    for root, dirs, files in os.walk(localPath):
        for f in files:
            if f.lower().endswith('.java'):
                if javaCodeHasHibernateReferences(os.path.join(root, f)):
                    return True
    return False

def isMavenProject(project):
    print "Verificando a existencia de um arquivo pom.xml no projeto: %s ..." % (project)

    diretorio = DEFAULT_OUTPUT_PATH + "/" + project + "/"
    if os.path.isfile(diretorio + 'pom.xml'):
        return True
    return False

def runMavenCompile(projetos):
    print 'Run Maven Compile ...'
    workspace = os.getcwd()
    for p in projetos:
        if isMavenProject(p):
            print 'Run Maven Compile %s' % p
            proj_dir = DEFAULT_OUTPUT_PATH + "/" + p
            os.chdir(proj_dir)
            run('%s/mvn compiler:compile' % (MAVEN_BIN))
            #run('%s/mvn jar:jar' % (MAVEN_BIN))
            os.chdir(workspace)
        else:
            print 'Nao eh um projeto Maven: %s' % p

def getProjectsThatUseHibernate(args, gh):
    informacoes = []
    hibernateProjects = []
    total = 0
    totalMaven = 0
    totalEntity = 0
    indice = 0
    numProj = 1

    repositorios = getMostPopularJavaRepositories(gh)
    tamanhoPopulacao = len(repositorios)

    informacoes.append("Lista de Projetos com Hibernate/JPA")
    agora = date.today()
    informacoes.append("Data: " + agora.isoformat())
    informacoes.append("String de Busca: " + STRING_BUSCA)
    informacoes.append("Total de Resultados da Busca: %d" % tamanhoPopulacao)
    informacoes.append("Número de Repositorios com Hibernate/JPA: %d\n" % args.numHibernateReposDesired)

    #amostras = sortearAmostra(tamanhoPopulacao, tamanhoAmostra);
    amostras = selecionarTopStar(tamanhoPopulacao, tamanhoAmostra);
    #amostras.sort()

    for p in repositorios:

        if len(hibernateProjects) >= args.numHibernateReposDesired or indice == tamanhoAmostra:
            print "Finalizando Seleção dos Projetos: ", len(hibernateProjects)
            print "Indice: ", indice
            break

        # Verifica se o número deste projeto foi sorteado na amostra
        if (numProj == amostras[indice]):
            total += 1
            indice +=1
            print "\n Analyzing project", p[0]
            #Modificar, o método cloneGit só utiliza o p.full_name e o p.clone_url
            cloneGitRepo(p, args.outputPath, args.forceRedownload)
            hasEntity = "hasEntity:False"
            isMavenProj = "isMavenProject:False"
            if isProjectThatUsesHibernate(p, args.outputPath):
                totalEntity += 1
                hasEntity = "hasEntity:True"
                if isMavenProject(p[0]):
                    totalMaven += 1
                    isMavenProj = "isMavenProject:True"
                    hibernateProjects.append(p[0])

                print "Project %s uses hibernate. Need to find %d more projects that use hibernate..." % (p[0], args.numHibernateReposDesired - indice)
            print p[0] + ", " + isMavenProj + ", " + hasEntity
            informacoes.append(p[0] + ", " + isMavenProj + ", " + hasEntity)

        numProj += 1 #Número do Projeto é incrementado até encontrar o número nos projetos sorteados na amostra

    informacoes.append("\nTotal Projetos Download: " + str(total) + " Maven: " + str(totalMaven) + " Entity: " + str(totalEntity))

    dados = []
    dados.append(informacoes)
    dados.append(hibernateProjects)
    return dados

def sortearAmostra(tamanhoPopulacao, tamanhoAmostra):
    conjunto = set()
    while len(conjunto) < tamanhoAmostra:
        rand = randint(1, tamanhoPopulacao)
        conjunto.add(rand)
    amostra = list(conjunto)
    return amostra

def selecionarTopStar(tamanhoPopulacao, tamanhoAmostra):
    amostra = range(1, tamanhoAmostra+1)
    return amostra

def writeInformations(nomeArq, informacoes):
    with open(nomeArq, 'w') as fout:
        for hp in informacoes:
            print hp
            fout.write(hp)
            fout.write(os.linesep)
    fout.close()

def getMostPopularJavaRepositories(gh):
    repositorios = []
    agora = date.today()
    #fileName = "repositorios_" + agora.isoformat() + ".txt"
    fileName = "repositorios_2015-07-29.txt"

    if (buscarRepositorios):
        file = open(fileName, 'w')
        #busca no repositório github apenas se for necessário
        reps_busca = gh.search_repositories(STRING_BUSCA, sort="stars", order="desc")

        for p in reps_busca:
            repositorios.append([p.full_name, p.clone_url])

        json.dump(repositorios, file)
        file.close()
    else:
        file = open(fileName, 'r')
        repositorios = json.load(file)
        file.close()

    return repositorios

def main():
    args = parseArgs()
    gh = initGithub(args.authenticate)

    dados = getProjectsThatUseHibernate(args, gh)
    writeInformations('projects_star_2015-11-12.txt', dados[0])
    writeInformations('projects_star_hibernate_2015-11-12.txt',dados[1])
    runMavenCompile(dados[1])


if __name__ == '__main__':
    main()
