package br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsNotUseIdentifierPropertyRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import br.edu.ufcg.splab.designtests.designrules.ImplementsSerializableRule;
import br.edu.ufcg.splab.designtests.designrules.ProvideGetsSetsFieldsRule;
import br.edu.ufcg.splab.designtests.designrules.ProvideIdentifierPropertyRule;
import br.edu.ufcg.splab.designtests.designrules.UseSetCollectionRule;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InexistentEntityException {
        String projectName = "sigest";
        String arquivoJar = "jars/sigest.jar";

        DesignWizardDecorator dwd = new DesignWizardDecorator(arquivoJar, projectName);
        DesignWizard dw = dwd.getDesignWizard();

        Set<ClassNode> models = dwd.getClassesByAnnotation("javax.persistence.Entity");

        for (ClassNode classNode : models) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAnnotations());
        }

        Set<ClassNode> classes = dwd.getDesignWizard().getAllClasses();

        for (ClassNode classNode : classes) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAnnotations());
        }

        Set<ClassNode> annotations = dwd.getAllAnnotations();

        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            // System.out.println("Annotations: " +
            // annotationNode.getClassesAnnotated());
            System.out.println("Is Annotation: " + annotationNode.isAnnotation());
        }

        ClassNode curso = dwd.getClass("br.ufrn.cerescaico.bsi.sigest.model.Curso");
        System.out.println("Classe >>>> Curso");
        System.out.println("Name: " + curso.getName());
        System.out.println("ClassName: " + curso.getClassName());
        System.out.println("All Annotations:");
        annotations = curso.getAnnotations();
        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            System.out.println("Is Annotation: " + annotationNode.isAnnotation());
        }

        ClassNode sigest = dwd.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
        System.out.println("Classe >>>> Sigest");
        System.out.println("Name: " + sigest.getName());
        System.out.println("ClassName: " + sigest.getClassName());
        System.out.println("All Annotations:");
        annotations = sigest.getAnnotations();
        for (ClassNode annotationNode : annotations) {
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            System.out.println("Is Annotation: " + annotationNode.isAnnotation());
        }

        ClassNode annotation = dwd.getAnnotation("javax.persistence.Entity");
        System.out.println("Annotations >>>> ");
        System.out.println("Name: " + annotation.getName());
        System.out.println("ClassName: " + annotation.getClassName());
        System.out.println("is Annotation: " + annotation.isAnnotation());
        // System.out.println("All Classes: " +
        // annotation.getClassesAnnotated());

        System.out.printf("\n Execution of the Rules \n\n");

        AbstractDesignRule rule0 = new ProvideIdentifierPropertyRule(dw);
        rule0.setClassNodes(models);
        System.out.println("Report Rule ProvideIdentifierPropertyRule");
        if (!rule0.checkRule()) {
            System.out.println(rule0.getReport());
        }
        Set<ClassNode> falsos = rule0.getResultsFalse();
        for (ClassNode classNode : falsos) {
            System.out.println("Falhou: " + classNode.getName());
        }
        Set<ClassNode> verdadeiros = rule0.getResultsTrue();
        for (ClassNode classNode : verdadeiros) {
            System.out.println("Passou: " + classNode.getName());
        }

        AbstractDesignRule rule1 = new HashCodeAndEqualsRule(dw);
        rule1.setClassNodes(models);
        System.out.println("Report Rule HashCodeAndEqualsRule");
        if (!rule1.checkRule()) {
            System.out.println(rule1.getReport());
        }
        falsos = rule1.getResultsFalse();
        for (ClassNode classNode : falsos) {
            System.out.println("Falhou: " + classNode.getName());
        }

        verdadeiros = rule1.getResultsTrue();
        for (ClassNode classNode : verdadeiros) {
            System.out.println("Passou: " + classNode.getName());
        }

        ImplementsSerializableRule rule2 = new ImplementsSerializableRule(dw);
        rule2.setClassNodes(models);
        System.out.println("\nReport Rule ImplementsSerializableRule");
        if (!rule2.checkRule()) {
            System.out.println(rule2.getReport());
        }

        falsos = rule2.getResultsFalse();
        for (ClassNode classNode : falsos) {
            System.out.println("Falhou: " + classNode.getName());
        }

        verdadeiros = rule2.getResultsTrue();
        for (ClassNode classNode : verdadeiros) {
            System.out.println("Passou: " + classNode.getName());
        }

        UseSetCollectionRule rule3 = new UseSetCollectionRule(dw);
        rule3.setClassNodes(models);
        System.out.println("Report Rule UseSetCollectionRule");
        if (!rule3.checkRule()) {
            System.out.println(rule3.getReport());
        }

        falsos = rule3.getResultsFalse();
        for (ClassNode classNode : falsos) {
            System.out.println("Falhou: " + classNode.getName());
        }

        verdadeiros = rule3.getResultsTrue();
        for (ClassNode classNode : verdadeiros) {
            System.out.println("Passou: " + classNode.getName());
        }

        HashCodeAndEqualsNotUseIdentifierPropertyRule rule4 = new HashCodeAndEqualsNotUseIdentifierPropertyRule(dw);
        rule4.setClassNodes(models);
        System.out.println("Report Rule HashCodeAndEqualsNotUseIdentifierPropertyRule");
        if (!rule4.checkRule()) {
            System.out.println(rule4.getReport());
        }

        falsos = rule4.getResultsFalse();
        for (ClassNode classNode : falsos) {
            System.out.println("Falhou: " + classNode.getName());
        }

        verdadeiros = rule4.getResultsTrue();
        for (ClassNode classNode : verdadeiros) {
            System.out.println("Passou: " + classNode.getName());
        }

        ProvideGetsSetsFieldsRule rule5 = new ProvideGetsSetsFieldsRule(dw);
        rule5.setClassNodes(models);
        System.out.println("Report Rule ProvideGetsSetsFieldsRule");
        if (!rule5.checkRule()) {
            System.out.println(rule5.getReport());
        }

        falsos = rule5.getResultsFalse();
        for (ClassNode classNode : falsos) {
            System.out.println("Falhou: " + classNode.getName());
        }

        verdadeiros = rule5.getResultsTrue();
        for (ClassNode classNode : verdadeiros) {
            System.out.println("Passou: " + classNode.getName());
        }
    }
}
