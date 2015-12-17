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
 * @author Taciano
 */
public class UseInterfaceSetOrListRule extends AbstractDesignRule {

    public UseInterfaceSetOrListRule(DesignWizard dw) {
        super(dw);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> allModelClasses = getClassNodes();

        for (ClassNode entityNode : allModelClasses) {

            Set<FieldNode> declaredFields = entityNode.getAllFields();
            boolean passed = true;

            for (FieldNode fieldNode : declaredFields) {
                ClassNode type = fieldNode.getType();

                if (isCollection(type) && !type.equals(TypesOfCollections.SET) && !type.equals(TypesOfCollections.LIST)) {
                    this.report += "The field <" + fieldNode.getName() + "> of the class <" + entityNode.getName()
                            + " implements interface Collection but it doesn't implements interface Set or interface List.\n";
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
        return this.report.equals("") ? true : false;
    }
}
