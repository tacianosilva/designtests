package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * @author Taciano
 */
public class HashCodeAndEqualsRule implements Rule {

    private DesignWizardDecorator dwd;
    /**
     * The set of classnodes that the rule will be executing
     */
    private Set<ClassNode> classNodes;
    private String report;

    public HashCodeAndEqualsRule(DesignWizardDecorator dwd) {
        this.dwd = dwd;
        this.classNodes = null;
        this.report = "";
    }

    @Override
    public boolean checkRule() throws InexistentEntityException {
        // TODO Auto-generated method stub
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            MethodNode equalsMethod = entityNode
                    .getDeclaredMethod("equals(java.lang.Object)");
            MethodNode hashCodeMethod = entityNode
                    .getDeclaredMethod("hashCode()");

            Set<MethodNode> declaredMethods = entityNode.getDeclaredMethods();

            boolean contem = declaredMethods.contains(equalsMethod) && declaredMethods.contains(hashCodeMethod);

            if (!(contem)) {
                System.out.println("ClassNode: " + entityNode.getName());
                System.out.println("Equals Methods: " + declaredMethods.contains(equalsMethod));
                System.out.println("HashCode Methods: " + declaredMethods.contains(hashCodeMethod));
                if (!declaredMethods.contains(equalsMethod)) {
                    this.report += "The class <" + entityNode.getName() + "> doesn't contain the equals method.\n";
                }
                if (!declaredMethods.contains(hashCodeMethod)) {
                    this.report += "The class <" + entityNode.getName() + "> doesn't contain the hashCode method.\n";
                }
            }
        }
        return this.report.equals("") ? true : false;
    }

    /**
     *
     * @return
     * @throws InexistentEntityException
     */
    private Set<ClassNode> getClassNodes() throws InexistentEntityException {
        if (classNodes == null) {
            this.classNodes = dwd.getClassesFromCode();
        }

        return classNodes;
    }

    /**
     *
     * @param classes
     */
    public void setClassNodes(Set<ClassNode> classes) {
        this.classNodes = null;
        if (checkClassNodes(classes)) {
            this.classNodes = classes;
        }
    }

    /**
     * Checks if the parameter belongs to the set of classes of the design {@link DesignWizardDecorator}.
     * @param classes The set of classNodes to check the pertinence.
     * @return True if all classnodes belongs to the design.
     */
    private boolean checkClassNodes(Set<ClassNode> classes) {
        return dwd.getClassesFromCode().containsAll(classes);
    }

    @Override
    public String getReport() {
        return this.report;
    }
}
