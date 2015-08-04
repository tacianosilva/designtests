package br.edu.ufcg.splab.designtests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import br.edu.ufcg.splab.designtests.designrules.ImplementsSerializableModelRule;

public class RulesVerifier {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InexistentEntityException {
        System.out.printf("\nConteúdo do arquivo projectsThatUseHibernate.txt\n\n");

        String fileName = "scripts/projectsThatUseHibernate_2015-07-30.txt";
        String fileResults = "scripts/resultsAnalisyProjects_2015-07-30.txt";
        processarArquivo(fileName, fileResults);

    }

    public static void processarArquivo(String fileProjects, String fileResults) {
        try {
            FileReader arq = new FileReader(fileProjects);
            BufferedReader lerArq = new BufferedReader(arq);

            FileWriter fw = criarArquivo(fileResults);
            PrintWriter gravarArq = new PrintWriter(fw);
            gravarArq.printf("%s, %s, %s, %s, %s, %s\n", "gitUser", "projectName", "numEntidades", "rulecheck", "ocorreuErro", "msgErro");
            String linha = lerArq.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                System.out.printf("%s\n", linha);

                processarProjeto(linha, gravarArq);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            arq.close();
            fw.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public static void processarProjeto(String projeto, PrintWriter gravar) {
        String[] split = projeto.split("/");
        String gitUser = split[0];
        String projectName = split[1];
        String reposDir = "scripts/repos/";
        String classDir = "/target/classes";

        String projectDir = reposDir + projeto + classDir;
        boolean rulecheck = false;
        boolean ocorreuErro = false;
        String msgErro = "";
        int numEntidades = 0;

        try {
            System.out.println("Diretório do Projeto: " + projectDir);
            DesignWizardDecorator dwd = new DesignWizardDecorator(projectDir, projectName);

            Set<ClassNode> classes = dwd.getClassesByAnnotation("javax.persistence.Entity");
            numEntidades = classes.size();

            //TODO Colocar para as regras receberem o conjunto de classes para verificar
            ImplementsSerializableModelRule rule = new ImplementsSerializableModelRule(dwd);
            if (!rule.checkRule()) {
                System.out.println("Report Rule");
                System.out.println(rule.getReport());
            } else {
                rulecheck = true;
            }

        } catch (IOException ioe) {
            // TODO Auto-generated catch block
            ocorreuErro = true;
            msgErro = ioe.getMessage();
            ioe.printStackTrace();
        } catch (ClassNotFoundException ce) {
            // TODO Auto-generated catch block
            ocorreuErro = true;
            msgErro = ce.getMessage();
            ce.printStackTrace();
        } catch (InexistentEntityException e) {
            // TODO Auto-generated catch block
            ocorreuErro = true;
            msgErro = e.getMessage();
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            ocorreuErro = true;
            msgErro = e.getMessage();
            e.printStackTrace();
        }
        if (!ocorreuErro) {
            gravarLinha(gravar, gitUser, projectName, numEntidades, rulecheck, ocorreuErro, msgErro);
        }
    }

    public static void gravarLinha(PrintWriter gravar, String gitUser, String projectName, int numEntidades,
            boolean rulecheck, boolean ocorreuErro, String msgErro) {
        gravar.printf("%s, %s, %d, %s, %s, %s\n", gitUser, projectName, numEntidades, rulecheck, ocorreuErro, msgErro);
    }

    public static FileWriter criarArquivo(String fileResults) throws IOException {
        FileWriter arq = new FileWriter(fileResults);
        return arq;
    }
}
