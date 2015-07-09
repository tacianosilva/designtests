package br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.AnnotationNode;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

import br.edu.ufcg.splab.designtests.design.Architecture;

public class DesignWizardDecorator {

    private DesignWizard dw;
    private String projectName;

    public DesignWizardDecorator(String pathToJar, String projectName) throws IOException, ClassNotFoundException {
        this.dw = new DesignWizard(pathToJar);
        this.projectName = projectName;
    }

    public Architecture getArchitecture() {
        Architecture arch = new Architecture(projectName);
        return null;
    }

    public DesignWizard getDesignWizard() {
        return dw;
    }

    public Set<ClassNode> getClassesByAnnotation(String annotationName) throws IOException, InexistentEntityException {

        Set<ClassNode> allClasses = dw.getAllClasses();
        AnnotationNode annotationNode = dw.getAnnotation(annotationName);

        Set<ClassNode> classes = new HashSet<ClassNode>();

        for (ClassNode classNode : allClasses) {
            if (hasAnnotation(classNode, annotationNode)) {
                classes.add(classNode);
            }
        }
        return classes;
    }

    public Boolean hasAnnotation(ClassNode aClass, AnnotationNode annotation) {
        Set<AnnotationNode> annotations = aClass.getAllAnnotations();

        if (annotations.contains(annotation)) {
                return true;
            }
        return false;
    }

    public Set<ClassNode> getClassesAnnotated(String annotationName) {
        Set<ClassNode> classes = new HashSet<ClassNode>();
        dw.getAllClasses();
        return classes;
    }

    public Set<AnnotationNode> getAllAnnotations() {
        Set<AnnotationNode> annotations = dw.getAllAnnotations();
        return annotations;
    }

    public ClassNode getClass(String className) throws InexistentEntityException {
        return dw.getClass(className);
    }

    public AnnotationNode getAnnotation(String annotationName) throws InexistentEntityException {
        return dw.getAnnotation(annotationName);
    }
}