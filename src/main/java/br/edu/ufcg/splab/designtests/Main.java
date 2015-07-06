package br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.Set;

import org.designwizard.design.ClassNode;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

        DesignWizardDecorator dwd;
        String projectName = "sigest";
        String arquivoJar = "jars/sigest.jar";

        dwd = new DesignWizardDecorator(arquivoJar, projectName);

        Set<ClassNode> classes = dwd.getClassesByEntityAnnotation();

        for (ClassNode classNode : classes) {
            System.out.println(classNode.getClassName());
        }

    }

}
