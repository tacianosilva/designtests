package br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.Set;

import org.designwizard.design.AnnotationNode;
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

        Set<AnnotationNode> annotations = dwd.getAllAnnotations();

        for (AnnotationNode annotationNode : annotations) {
            System.out.println("Annotations: "+ annotationNode.getName());
            System.out.println("Annotations: "+ annotationNode.getShortName());
            System.out.println("Annotations: "+ annotationNode.getClassName());
            System.out.println("Annotations: "+ annotationNode.getClassesAnnotated());
        }

        ClassNode curso = dwd.getClass("br.ufrn.cerescaico.bsi.sigest.model.Curso");
        System.out.println("Classe >>>> ");
        System.out.println("Name: " + curso.getName());
        System.out.println("ClassName: " + curso.getClassName());
        System.out.println("All Annotations: " + curso.getAllAnnotations());

        AnnotationNode annotation = dwd.getAnnotation("javax.persistence.Entity");
        System.out.println("Annotations >>>> ");
        System.out.println("Name: " + annotation.getName());
        System.out.println("ClassName: " + annotation.getClassName());
        System.out.println("All Classes: " + annotation.getClassesAnnotated());

    }

}
