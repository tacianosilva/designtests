package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;

/**
 *
 * Rule: Provide identifier Properties in classes of the Model Package.
 *
 * See more on section 2.1.2 in <a href=
 * "https://docs.jboss.org/hibernate/orm/5.0/userGuide/en-US/html/ch02.html">
 * Hibernate Docs</a>.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class ProvideIdentifierPropertyRule extends AbstractDesignRule implements Rule {

    /**
     * For extracting information of persistent classes.
     */
    private PersistenceRuleUtil util;

    /**
     * Builds the rule for the designwizard instance.
     * @param dw A {@link DesignWizard} instance.
     */
    public ProvideIdentifierPropertyRule(DesignWizard dw) {
        super(dw);
        this.util = new PersistenceRuleUtil();
    }

    /**
     * Checks if the set {@link AbstractDesignRule#getClassNodes()} attends the rule.
     * Checks if the class contains a identifier field in the declared fields.
     * Doesn't check the inherited fields from the super class.
     * @see br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule#checkRule()
     */
    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            FieldNode field = util.getIdentifierProperty(entityNode);

            if (field == null) {
                this.report += "The class <" + entityNode.getName()
                            + " doesn't provide identifier property.\n";
                addResultFalse(entityNode);
            } else {
                addResultTrue(entityNode);
            }
        }
        return this.report.equals("") ? true : false;
    }
}
