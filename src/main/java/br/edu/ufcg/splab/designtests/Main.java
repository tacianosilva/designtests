package br.edu.ufcg.splab.designtests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InexistentEntityException {
        // TODO Auto-generated method stub

        DesignWizardDecorator dwd;
        String projectName = "sigest";
        String arquivoJar = "jars/sigest.jar";

        dwd = new DesignWizardDecorator(arquivoJar, projectName);

        Set<ClassNode> classes = dwd.getClassesByAnnotation("javax.persistence.Entity");

        for (ClassNode classNode : classes) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAllAnnotations());
        }

        classes = dwd.getClassesFromCode();

        for (ClassNode classNode : classes) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAllAnnotations());
        }

        Set<ClassNode> annotations = dwd.getAllAnnotations();

        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            //System.out.println("Annotations: " + annotationNode.getClassesAnnotated());
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
        //System.out.println("All Classes: " + annotation.getClassesAnnotated());

        System.out.printf("\nConteúdo do arquivo projectsThatUseHibernate.txt\n\n");

        String fileName = "scripts/projectsThatUseHibernate.txt";
        processarArquivo(fileName);

    }

    public static void processarArquivo(String fileProjects) {
        try {
            FileReader arq = new FileReader(fileProjects);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                System.out.printf("%s\n", linha);

                processarProjeto(linha);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public static void processarProjeto(String projeto) {
        String[] split = projeto.split("/");
        String gitUser = split[0];
        String projectName = split[1];
        String reposDir = "scripts/repos/";
        String classDir = "/target/classes";
        String projectDir = reposDir + projeto + classDir;
        try {
            System.out.println("Diretório do Projeto: " + projectDir);
            DesignWizardDecorator dwd = new DesignWizardDecorator(projectDir, projectName);
            Set<ClassNode> classes = dwd.getClassesByAnnotation("javax.persistence.Entity");
            System.out.println(classes.size());
        } catch (IOException ioe) {
            // TODO Auto-generated catch block
            ioe.printStackTrace();
        } catch (ClassNotFoundException ce) {
            // TODO Auto-generated catch block
            ce.printStackTrace();
        } catch (InexistentEntityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
