package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;

import br.edu.ufcg.splab.designtests.util.TypesOfCollections;

/**
 *
 * Rule: Declaration of Collection of the type Set or List in classes of the Model
 * Package.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class UseInterfaceSetOrListRule extends AbstractDesignRule {

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     */
    public UseInterfaceSetOrListRule(final DesignWizard dw) {
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

                if (isCollection(type) && !type.equals(TypesOfCollections.SET)
                            && !type.equals(TypesOfCollections.LIST)) {
                    this.addReport("The field <"
                        + fieldNode.getName()
                        + "> of the class <" + entityNode.getName()
                        + " implements interface Collection but it "
                        + "doesn't implements interface Set or interface List.\n");
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
