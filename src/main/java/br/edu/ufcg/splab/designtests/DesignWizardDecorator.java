package br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.main.DesignWizard;

import br.edu.ufcg.splab.designtests.design.Architecture;

public class DesignWizardDecorator {

    private DesignWizard dw;
    private String projectName;
    private String pathToJar;

    public DesignWizardDecorator(String pathToJar, String projectName) throws IOException, ClassNotFoundException {
        this.dw = new DesignWizard(pathToJar);
        this.projectName = projectName;
        this.pathToJar = pathToJar;
    }

    public Architecture getArchitecture() {
        Architecture arch = new Architecture(projectName);
        return null;
    }

    public DesignWizard getDesignWizard() {
        return dw;
    }

    @SuppressWarnings("finally")
    public Set<ClassNode> getClassesByEntityAnnotation() throws IOException {
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        Set<ClassNode> classes = new HashSet<ClassNode>();

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            cl = URLClassLoader.newInstance(urls);
            Class<?> aClass;
            try {
                aClass = cl.loadClass(className);
                if (hasAnnotation(aClass)) {
                    //classes.add(aClass);
                    classes.add(dw.getClass(aClass));
                }
            } catch (ClassNotFoundException ex) {
                //ex.printStackTrace();
            } finally {
                cl.close();
                cl = URLClassLoader.newInstance(urls);
                continue;
            }
        }
        return classes;
    }

    public Boolean hasAnnotation(Class aClass) {
        Annotation[] annotations = aClass.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation instanceof javax.persistence.Entity) {
                return true;
            }
        }
        return false;
    }
}