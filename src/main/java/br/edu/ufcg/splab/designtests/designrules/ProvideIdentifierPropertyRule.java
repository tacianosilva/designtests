package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * @author Taciano
 */
public class ProvideIdentifierPropertyRule extends AbstractDesignRule implements Rule {

    public ProvideIdentifierPropertyRule(DesignWizardDecorator dwd) {
        super(dwd);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            FieldNode field = getIdentifierProperty(entityNode);

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

    @Override
    public String getReport() {
        return this.report;
    }
}
