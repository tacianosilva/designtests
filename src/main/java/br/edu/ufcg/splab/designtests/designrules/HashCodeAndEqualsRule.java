package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * Methods must be declared in the persistent class.
 *
 * @author Taciano
 */
public class HashCodeAndEqualsRule extends AbstractDesignRule implements Rule {

    public HashCodeAndEqualsRule(DesignWizard dw) {
        super(dw);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            MethodNode equalsMethod = getEqualsMethod(entityNode);
            MethodNode hashCodeMethod = getHashCodeMethod(entityNode);

            boolean contem = equalsMethod != null && hashCodeMethod != null;

            if (!(contem)) {
                if (equalsMethod == null) {
                    this.report += "The class <" + entityNode.getName() + "> doesn't contain the equals method.\n";
                }
                if (hashCodeMethod == null) {
                    this.report += "The class <" + entityNode.getName() + "> doesn't contain the hashCode method.\n";
                }
                addResultFalse(entityNode);
            } else {
                addResultTrue(entityNode);
            }
        }
        return this.report.equals("") ? true : false;
    }
}