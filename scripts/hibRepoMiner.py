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
from datetime import date

import github

VERBOSE = True
DEFAULT_OUTPUT_PATH = 'repos'
MAVEN_BIN = '~/dev/apache-maven-3.3.3/bin'
tamanhoPopulacao = -1
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
    return os.path.join(downloadPath, project.full_name)


def cloneGitRepo(project, downloadPath, forceRedownload):
    project.localPath = getLocalPath(project, downloadPath)

    if forceRedownload:
        shutil.rmtree(project.localPath, True)

    if os.path.isdir(project.localPath):
        print >>sys.stderr, 'Repo %s already exists. Not downloading it again...' % project.full_name
        return
    print 'Clone URL %s <<<<<' % project.clone_url
    run('git clone --depth 1 %s %s' % (project.clone_url, project.localPath))


def javaCodeHasHibernateReferences(path):
    with open(path) as fin:
        for line in fin.readlines():
            if re.match(".*javax.persistence.Entity.*", line):
                return True
    return False


def isProjectThatUsesHibernate(project):
    print "Parsing java source code of %s..." % (project.full_name)
    for root, dirs, files in os.walk(project.localPath):
        for f in files:
            if f.lower().endswith('.java'):
                if javaCodeHasHibernateReferences(os.path.join(root, f)):
                    return True
    return False

def isMavenProject(project):
    print "Verificando a existencia de um arquivo pom.xml no projeto: %s ..." % (project)

    diretorio = DEFAULT_OUTPUT_PATH + "/" + project + "/"

    if os.path.isfile(diretorio + 'pom.xml'):
        print 'isMavenProject True'
        return True
    print 'isMavenProject False'
    return False

def runMavenCompile(projetos):
    print 'Run Maven Compile ...'
    workspace = os.getcwd()
    for p in projetos:
        if isMavenProject(p):
            print 'Run Maven Compile %s' % p
            proj_dir = workspace + "/" + DEFAULT_OUTPUT_PATH + "/" + p
            os.chdir(proj_dir)
            run('%s/mvn compiler:compile' % (MAVEN_BIN))
            #run('%s/mvn jar:jar' % (MAVEN_BIN))
            os.chdir(workspace)
        else:
            print 'Nao eh um projeto Maven: %s' % p

def getProjectsThatUseHibernate(args, gh):
    informacoes = []
    hibernateProjects = []

    informacoes.append("Lista de Projetos com Hibernate/JPA")
    agora = date.today()
    informacoes.append("Data: " + agora.isoformat())
    informacoes.append("String de Busca: " + STRING_BUSCA)
    informacoes.append("Total de Resultados da Busca: %d" % tamanhoPopulacao)
    informacoes.append("Número de Repositorios com Hibernate/JPA: %d" % args.numHibernateReposDesired)

    for p in getMostPopularJavaRepositories(gh):
        if len(hibernateProjects) >= args.numHibernateReposDesired:
            break

        print "Analyzing project", p.full_name
        cloneGitRepo(p, args.outputPath, args.forceRedownload)
        if isProjectThatUsesHibernate(p):
            isMavenProj = "isMavenProject:False"

            if isMavenProject(p.full_name):
                isMavenProj = "isMavenProject:True"
                hibernateProjects.append(p.full_name)
            informacoes.append(p.full_name + ", " + isMavenProj)
            print "Project %s uses hibernate. Need to find %d more projects that use hibernate..." % (p.full_name, args.numHibernateReposDesired - len(hibernateProjects))
            print "Is Maven Project %s" % isMavenProject(p.full_name)

    dados = []
    dados.append(informacoes)
    dados.append(hibernateProjects)
    return dados


def writeListOfProjectsThatUseHibernate(hibernateProjects):
    with open('projectsThatUseHibernate.txt', 'w') as fout:
        for hp in hibernateProjects:
            print hp
            fout.write(hp)
            fout.write(os.linesep)
    fout.close()

def writeInformations(informacoes):
    with open('projectsInformations.txt', 'w') as fout:
        for hp in informacoes:
            print hp
            fout.write(hp)
            fout.write(os.linesep)
    fout.close()

def getMostPopularJavaRepositories(gh):
    repositorios = gh.search_repositories(STRING_BUSCA, sort="stars", order="desc")
    tamanhoPopulacao = repositorios.totalCount
    return repositorios


def main():
    args = parseArgs()
    gh = initGithub(args.authenticate)

    dados = getProjectsThatUseHibernate(args, gh)
    writeInformations(dados[0])
    writeListOfProjectsThatUseHibernate(dados[1])
    runMavenCompile(dados[1])


if __name__ == '__main__':
    main()
