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

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     */
    public HashCodeAndEqualsRule(final DesignWizard dw) {
        super(dw);
    }

    @Override
    public final boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            MethodNode equalsMethod = getEqualsMethod(entityNode);
            MethodNode hashCodeMethod = getHashCodeMethod(entityNode);

            boolean contem = equalsMethod != null && hashCodeMethod != null;

            if (!(contem)) {
                if (equalsMethod == null) {
                    this.addReport("The class <" + entityNode.getName()
                        + "> doesn't contain the equals method.\n");
                }
                if (hashCodeMethod == null) {
                    this.addReport("The class <" + entityNode.getName()
                        + "> doesn't contain the hashCode method.\n");
                }
                addResultFalse(entityNode);
            } else {
                addResultTrue(entityNode);
            }
        }
        return this.isEmptyReport();
    }
}