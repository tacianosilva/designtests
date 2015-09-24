package br.edu.ufcg.splab.designtests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsNotUseIdentifierPropertyRule;

public class RulesVerifier {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InexistentEntityException {
        System.out.printf("\nConteúdo do arquivo projectsThatUseHibernate.txt\n\n");

        String fileName = "scripts/projects_sample_hibernate_2015-08-10.txt";
        String fileResults = "scripts/tests_results_sample_r1.txt";
        String infoResults = "scripts/tests_info_sample_r1.txt";
        processarArquivo(fileName, fileResults, infoResults);

    }

    public static void processarArquivo(String fileProjects, String fileResults, String infoResults) {
        try {
            FileReader arq = new FileReader(fileProjects);
            BufferedReader lerArq = new BufferedReader(arq);

            FileWriter fw = criarArquivo(fileResults);
            PrintWriter resultsWriter = new PrintWriter(fw);
            resultsWriter.printf("%s,%s,%s,%s\n", "project", "class", "rule", "result");

            FileWriter infoFW = criarArquivo(infoResults);
            PrintWriter infoWriter = new PrintWriter(infoFW);
            infoWriter.printf("%s,%s,%s,%s\n", "project", "num classes", "num model classes", "num fail classes");

            String linha = lerArq.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                System.out.printf("%s\n", linha);

                processarProjeto(linha, resultsWriter, infoWriter);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            arq.close();
            fw.close();
            infoFW.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public static void processarProjeto(String projeto, PrintWriter resultsWriter, PrintWriter infoWriter) {
        String[] split = projeto.split("/");
        //String gitUser = split[0];
        String projectName = split[1];
        String reposDir = "scripts/repos/";
        String classDir = "/target/classes";

        String projectDir = reposDir + projeto + classDir;

        try {
            System.out.println("Diretório do Projeto: " + projectDir);
            DesignWizardDecorator dwd = new DesignWizardDecorator(projectDir, projectName);

            int numClasses = dwd.getDesignWizard().getAllClasses().size();

            // Model Classes from Project
            Set<ClassNode> classes = dwd.getClassesByAnnotation("javax.persistence.Entity");
            int numModelClasses = classes.size();


            List<AbstractDesignRule> regras = getRegras(dwd.getDesignWizard());
            int numFailClasses = 0;
            boolean passedClass = true;
            for (ClassNode classNode : classes) {
                for (AbstractDesignRule rule : regras) {
                    rule.setClassNode(classNode);
                    String ruleName = rule.getName();
                    boolean passed = rule.checkRule();

                    passedClass = passedClass && passed;

                    System.out.println("Report Rule" + ruleName);
                    System.out.println(rule.getReport());

                    System.out.println(">>>>" + projeto + ", " + classNode.getClassName() + ", " + ruleName + ", " + passed);

                    gravarLinha(resultsWriter, projeto, classNode.getClassName(), ruleName, passed);
                }
                if (!passedClass) numFailClasses++;
            }

            System.out.println(">>>>" + projeto + ", " + numClasses + ", " + numModelClasses + ", " + numFailClasses);
            gravarLinha(infoWriter, projeto, numClasses, numModelClasses, numFailClasses);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } catch (InexistentEntityException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private static void gravarLinha(PrintWriter gravar, String projeto, String className, String ruleName,
            boolean checkResult) {
        gravar.printf("%s,%s,%s,%s\n", projeto, className, ruleName, checkResult);
    }

    private static void gravarLinha(PrintWriter gravar, String projeto, int numClasses, int numModelClasses,
            int numFailClasses) {
        gravar.printf("%s,%d,%d,%d\n", projeto, numClasses, numModelClasses, numFailClasses);
    }

    private static List<AbstractDesignRule> getRegras(DesignWizard dw) {
        List<AbstractDesignRule> regras = new ArrayList<AbstractDesignRule>();

        //Regras extraídas do manual
/*        AbstractDesignRule rule1 = new HashCodeAndEqualsRule(dwd);
        regras.add(rule1);
        AbstractDesignRule rule2 = new NoArgumentConstructorRule(dwd);
        regras.add(rule2);
        AbstractDesignRule rule3 = new ProvideIdentifierPropertyRule(dwd);
        regras.add(rule3);
        AbstractDesignRule rule4 = new NoFinalClassRule(dwd);
        regras.add(rule4);
        //AbstractDesignRule rule5 = new ProvideGetsSetsFieldsRule(dwd);
        //regras.add(rule5);

        AbstractDesignRule rule6 = new UseSetCollectionRule(dwd);
        regras.add(rule6);
        AbstractDesignRule rule7 = new ImplementsSerializableRule(dwd);
        regras.add(rule7);*/

        AbstractDesignRule rule8 = new HashCodeAndEqualsNotUseIdentifierPropertyRule(dw);
        regras.add(rule8);

        return regras;
    }

    public static FileWriter criarArquivo(String fileResults) throws IOException {
        FileWriter arq = new FileWriter(fileResults);
        return arq;
    }
}
