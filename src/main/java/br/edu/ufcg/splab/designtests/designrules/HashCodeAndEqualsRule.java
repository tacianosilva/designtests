package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * @author Taciano
 */
public class HashCodeAndEqualsRule extends AbstractDesignRule implements Rule {

    public HashCodeAndEqualsRule(DesignWizardDecorator dwd) {
        super(dwd);
    }

    @Override
    public boolean checkRule() {
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

    @Override
    public String getReport() {
        return this.report;
    }
}
