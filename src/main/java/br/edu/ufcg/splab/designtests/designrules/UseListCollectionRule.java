package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;

/**
 *
 * Rule: Declaration of Collection of the type List in classes of the Model
 * Package.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class UseListCollectionRule extends AbstractDesignRule {

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     */
    public UseListCollectionRule(final DesignWizard dw) {
        super(dw);
    }

    @Override
    public final boolean checkRule() {
        Collection<ClassNode> allModelClasses = getClassNodes();

        for (ClassNode entityNode : allModelClasses) {

            Set<FieldNode> declaredFields = entityNode.getAllFields();
            boolean passed = true;

            for (FieldNode fieldNode : declaredFields) {
                ClassNode type = fieldNode.getType();

                if (isCollection(type) && !isList(type)) {
                    this.addReport("The field <" + fieldNode.getName()
                        + "> of the class <" + fieldNode.getName()
                        + " implements interface Collection but "
                        + "it doesn't implements interface Set.\n");
                    passed = false;
                    addResultFalse(entityNode);
                }
            }

            if (!passed) {
                addResultFalse(entityNode);
            } else {
                addResultTrue(entityNode);
            }
        }
        return this.isEmptyReport();
    }
}
