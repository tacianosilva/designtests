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
 * @author Taciano
 */
public class NoFinalClassRule extends AbstractDesignRule implements Rule {

    public NoFinalClassRule(DesignWizard dw) {
        super(dw);
    }

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
