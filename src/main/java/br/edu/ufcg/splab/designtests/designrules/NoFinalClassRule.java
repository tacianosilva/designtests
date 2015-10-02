package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Modifier;
import org.designwizard.designrules.Rule;

/**
 *
 * Rule: The classes can't to be final in classes of the Model Package.
 * The hibernate/Jpa can't to use proxies (lazy loading) with final classes.
 *
 * See more on section 2.1.3 in <a href=
 * "https://docs.jboss.org/hibernate/orm/5.0/userGuide/en-US/html/ch02.html">
 * Hibernate Docs</a>.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class NoFinalClassRule extends AbstractDesignRule implements Rule {

    /**
     * Builds the rule for the designwizard instance.
     * @param dw A {@link DesignWizard} instance.
     */
    public NoFinalClassRule(DesignWizard dw) {
        super(dw);
    }

    /**
     * Checks if the set {@link AbstractDesignRule#getClassNodes()} attends the rule.
     * Checks if the classes contains a modifier <code>final</code>.
     * @see br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule#checkRule()
     */
    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            Collection<Modifier> modifiers = entityNode.getModifiers();

            if (modifiers.contains(Modifier.FINAL)) {
                this.report += "The class <" + entityNode.getName() + "> can't to be a final class.\n";
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
