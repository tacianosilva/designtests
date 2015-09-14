package br.edu.ufcg.splab.designtests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import br.edu.ufcg.splab.designtests.designrules.ImplementsSerializableRule;
import br.edu.ufcg.splab.designtests.designrules.NoArgumentConstructorRule;
import br.edu.ufcg.splab.designtests.designrules.NoFinalClassRule;
import br.edu.ufcg.splab.designtests.designrules.ProvideIdentifierPropertyRule;
import br.edu.ufcg.splab.designtests.designrules.UseSetCollectionRule;

public class RulesVerifier {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InexistentEntityException {
        System.out.printf("\nConteúdo do arquivo projectsThatUseHibernate.txt\n\n");

        String fileName = "scripts/projectsHibernate_star_2015-08-30.txt";
        String fileResults = "scripts/results_star_2015-09-01.txt";
        processarArquivo(fileName, fileResults);

    }

    public static void processarArquivo(String fileProjects, String fileResults) {
        try {
            FileReader arq = new FileReader(fileProjects);
            BufferedReader lerArq = new BufferedReader(arq);

            FileWriter fw = criarArquivo(fileResults);
            PrintWriter gravarArq = new PrintWriter(fw);
            gravarArq.printf("%s,%s,%s,%s\n", "project", "class", "rule", "result");
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
        //String gitUser = split[0];
        String projectName = split[1];
        String reposDir = "scripts/repos/";
        String classDir = "/target/classes";

        String projectDir = reposDir + projeto + classDir;

        try {
            System.out.println("Diretório do Projeto: " + projectDir);
            DesignWizardDecorator dwd = new DesignWizardDecorator(projectDir, projectName);

            Set<ClassNode> classes = dwd.getClassesByAnnotation("javax.persistence.Entity");


            List<AbstractDesignRule> regras = getRegras(dwd);
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

                    gravarLinha(gravar, projeto, classNode.getClassName(), ruleName, passed);
                }
                if (!passedClass) numFailClasses++;
            }

            System.out.println(">>>>" + projeto + ", " + dwd.getClassesFromCode().size() + ", " + classes.size() + ", " + numFailClasses);

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

    private static List<AbstractDesignRule> getRegras(DesignWizardDecorator dwd) {
        List<AbstractDesignRule> regras = new ArrayList<AbstractDesignRule>();

        //Regras extraídas do manual
        AbstractDesignRule rule1 = new HashCodeAndEqualsRule(dwd);
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
        regras.add(rule7);

        return regras;
    }

    public static void gravarLinha(PrintWriter gravar, String gitUser, String projectName, int numEntidades,
            boolean rulecheck, int totalPassou, int totalFalhou, boolean ocorreuErro, String msgErro) {
        gravar.printf("%s, %s, %d, %s, %d, %d, %s, %s\n", gitUser, projectName, numEntidades, rulecheck,
                totalPassou, totalFalhou, ocorreuErro, msgErro);
    }

    public static FileWriter criarArquivo(String fileResults) throws IOException {
        FileWriter arq = new FileWriter(fileResults);
        return arq;
    }
}
