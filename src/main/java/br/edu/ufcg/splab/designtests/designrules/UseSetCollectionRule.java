package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

/**
 *
 * Rule: Declaration of Collection of the type Set in classes of the Model
 * Package.
 *
 * @author Taciano
 */
public class UseSetCollectionRule extends AbstractDesignRule {

    public UseSetCollectionRule(DesignWizardDecorator dwd) {
        super(dwd);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> allModelClasses = getClassNodes();

        for (ClassNode entityNode : allModelClasses) {

            Set<FieldNode> declaredFields = entityNode.getAllFields();
            boolean passed = true;

            for (FieldNode fieldNode : declaredFields) {
                ClassNode type = fieldNode.getType();

                if (isCollection(type) && !isSet(type)) {
                    this.report += "The field <" + fieldNode.getName() + "> of the class <" + fieldNode.getName()
                            + " implements interface Collection but it doesn't implements interface Set.\n";
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

    @Override
    public String getReport() {
        return this.report;
    }
}
