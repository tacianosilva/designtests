package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

/**
 *
 * Rule: The Serializable interface should be implementated for all classes in the Model Package.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class ImplementsSerializableRule extends AbstractDesignRule {

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     */
    public ImplementsSerializableRule(final DesignWizard dw) {
        super(dw);
    }

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     * @param classes The set of {@link ClassNode} that the rule will be executing.
     */
    public ImplementsSerializableRule(final DesignWizard dw, final Set<ClassNode> classes) {
        super(dw, classes);
    }

    @Override
    public final boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {
            ClassNode serializable = new ClassNode("java.io.Serializable");
            ClassNode superClass = entityNode.getSuperClass();
            if (!entityNode.implementsInterface(serializable)
                    && !superClass.implementsInterface(serializable)) {
                this.addReport("The class <" + entityNode.getName() + "> "
                            + "doesn't implements interface Serializable.\n");
                this.addResultFalse(entityNode);
            } else {
                this.addResultTrue(entityNode);
            }
        }
        return this.isEmptyReport();
    }
}
