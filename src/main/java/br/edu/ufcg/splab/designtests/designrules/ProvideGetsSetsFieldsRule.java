package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.designrules.Rule;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * @author Taciano
 */
public class ProvideGetsSetsFieldsRule extends AbstractDesignRule implements Rule {

    public ProvideGetsSetsFieldsRule(DesignWizard dw) {
        super(dw);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            Set<FieldNode> declaredFields = entityNode.getAllFields();

            boolean passed = true;

            for (FieldNode fieldNode : declaredFields) {

                if (fieldNode.isStatic()) {
                    continue;
                }

                if (!hasGetMethod(fieldNode, entityNode)) {
                    this.report += "The field <" + fieldNode.getName() + "> of the class <" + fieldNode.getName()
                    + " doesn't implement the get method.\n";
                    passed = false;
                }
                if (!hasSetMethod(fieldNode, entityNode)) {
                    this.report += "The field <" + fieldNode.getName() + "> of the class <" + fieldNode.getName()
                    + " doesn't implement the set method.\n";
                    passed = false;
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
