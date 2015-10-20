package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.designrules.Rule;

/**
 *
 * Rule: Defines getters and setters in classes of the Model Package.
 *
 * JPA requires JavaBean conventions by defining getters and setters for you entities persistent
 * attributes.
 * Hibernate can also directly access the entity's fields..
 *
 * * See more on section 2.1.4 in
 * <a href="https://docs.jboss.org/hibernate/orm/5.0/userGuide/en-US/html/ch02.html">
 * Hibernate Docs</a>.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class ProvideGetsSetsFieldsRule extends AbstractDesignRule implements Rule {

    /**
     * Builds the rule for the designwizard instance.
     * @param dw A {@link DesignWizard} instance.
     */
    public ProvideGetsSetsFieldsRule(DesignWizard dw) {
        super(dw);
    }

    /**
     * Checks the class fields (no static) if contains getters and setters.
     * @see br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule#checkRule()
     */
    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            Set<FieldNode> declaredFields = entityNode.getDeclaredFields();

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
}
