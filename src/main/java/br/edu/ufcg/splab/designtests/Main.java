package br.edu.ufcg.splab.designtests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import br.edu.ufcg.splab.designtests.designrules.ImplementsSerializableModelRule;
import br.edu.ufcg.splab.designtests.designrules.UseSetCollectionRule;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InexistentEntityException {
        // TODO Auto-generated method stub

        DesignWizardDecorator dwd;
        String projectName = "sigest";
        String arquivoJar = "jars/sigest.jar";

        dwd = new DesignWizardDecorator(arquivoJar, projectName);

        Set<ClassNode> models = dwd.getClassesByAnnotation("javax.persistence.Entity");

        for (ClassNode classNode : models) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAllAnnotations());
        }

        Set<ClassNode> classes = dwd.getClassesFromCode();

        for (ClassNode classNode : classes) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAllAnnotations());
        }

        Set<ClassNode> annotations = dwd.getAllAnnotations();

        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            // System.out.println("Annotations: " +
            // annotationNode.getClassesAnnotated());
            System.out.println("Is Annotation: " + annotationNode.isAnnotationClass());
        }

        ClassNode curso = dwd.getClass("br.ufrn.cerescaico.bsi.sigest.model.Curso");
        System.out.println("Classe >>>> Curso");
        System.out.println("Name: " + curso.getName());
        System.out.println("ClassName: " + curso.getClassName());
        System.out.println("All Annotations:");
        annotations = curso.getAllAnnotations();
        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            System.out.println("Is Annotation: " + annotationNode.isAnnotationClass());
        }

        ClassNode sigest = dwd.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
        System.out.println("Classe >>>> Sigest");
        System.out.println("Name: " + sigest.getName());
        System.out.println("ClassName: " + sigest.getClassName());
        System.out.println("All Annotations:");
        annotations = sigest.getAllAnnotations();
        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            System.out.println("Is Annotation: " + annotationNode.isAnnotationClass());
        }

        ClassNode annotation = dwd.getAnnotation("javax.persistence.Entity");
        System.out.println("Annotations >>>> ");
        System.out.println("Name: " + annotation.getName());
        System.out.println("ClassName: " + annotation.getClassName());
        System.out.println("is Annotation: " + annotation.isAnnotationClass());
        // System.out.println("All Classes: " +
        // annotation.getClassesAnnotated());

        System.out.printf("\n Execution of the Rules \n\n");

        AbstractDesignRule rule1 = new HashCodeAndEqualsRule(dwd);
        rule1.setClassNodes(models);
        System.out.println("Report Rule HashCodeAndEqualsRule");
        if (!rule1.checkRule()) {
            System.out.println(rule1.getReport());
        }

        ImplementsSerializableModelRule rule2 = new ImplementsSerializableModelRule(dwd);
        rule2.setClassNodes(models);
        System.out.println("Report Rule ImplementsSerializableRule");
        if (!rule2.checkRule()) {
            System.out.println(rule2.getReport());
        }

        UseSetCollectionRule rule3 = new UseSetCollectionRule(dwd);
        rule3.setClassNodes(models);
        System.out.println("Report Rule UseSetCollectionRule");
        if (!rule3.checkRule()) {
            System.out.println(rule3.getReport());
        }
    }

    public static void processarArquivo(String fileProjects, String fileResults) {
        try {
            FileReader arq = new FileReader(fileProjects);
            BufferedReader lerArq = new BufferedReader(arq);

            FileWriter fw = criarArquivo(fileResults);
            PrintWriter gravarArq = new PrintWriter(fw);

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
        boolean ocorreuErro = false;
        String msgErro = "";
        int numEntidades = 0;

        try {
            System.out.println("Diretório do Projeto: " + projectDir);
            DesignWizardDecorator dwd = new DesignWizardDecorator(projectDir, projectName);

            Set<ClassNode> classes = dwd.getClassesByAnnotation("javax.persistence.Entity");
            numEntidades = classes.size();

            AbstractDesignRule rule = new HashCodeAndEqualsRule(dwd);
            if (!rule.checkRule()) {
                System.out.println("Report Rule");
                System.out.println(rule.getReport());
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
            gravarLinha(gravar, gitUser, projectName, numEntidades, ocorreuErro, msgErro);
        }
    }

    public static void gravarLinha(PrintWriter gravar, String gitUser, String projectName, int numEntidades,
            boolean ocorreuErro, String msgErro) {
        gravar.printf("%s, %s, %d, %s, %s\n", gitUser, projectName, numEntidades, ocorreuErro, msgErro);
    }

    public static FileWriter criarArquivo(String fileResults) throws IOException {
        FileWriter arq = new FileWriter(fileResults);
        return arq;
    }
}
