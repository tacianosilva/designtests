package br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

/**
 * Auxiliary class for execution of design tests.
 * @author Taciano de Morais Silva - tacianosilva@gmail.com
 */
public class DesignWizardDecorator {

    private DesignWizard dw;
    private String projectName;

    public DesignWizardDecorator(String pathToJar, String projectName) throws IOException, ClassNotFoundException {
        this.dw = new DesignWizard(pathToJar);
        this.projectName = projectName;
    }

    public DesignWizard getDesignWizard() {
        return dw;
    }

    public Set<ClassNode> getClassesByAnnotation(String annotationName) throws InexistentEntityException {

        Set<ClassNode> allClasses = dw.getAllClasses();
        ClassNode annotationNode = dw.getAnnotation(annotationName);

        Set<ClassNode> classes = new HashSet<ClassNode>();

        for (ClassNode classNode : allClasses) {
            if (hasAnnotation(classNode, annotationNode)) {
                classes.add(classNode);
            }
        }
        return classes;
    }

    public Boolean hasAnnotation(ClassNode aClass, ClassNode annotation) {
        Set<ClassNode> annotations = aClass.getAnnotations();

        if (annotations.contains(annotation)) {
                return true;
            }
        return false;
    }

    public Set<ClassNode> getAllAnnotations() {
        Set<ClassNode> annotations = dw.getAllAnnotations();
        return annotations;
    }

    public ClassNode getClass(String className) throws InexistentEntityException {
        return dw.getClass(className);
    }

    public ClassNode getAnnotation(String annotationName) throws InexistentEntityException {
        return dw.getAnnotation(annotationName);
    }
}