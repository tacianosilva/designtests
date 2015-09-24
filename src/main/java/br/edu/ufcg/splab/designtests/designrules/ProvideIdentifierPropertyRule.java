package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * @author Taciano
 */
public class ProvideIdentifierPropertyRule extends AbstractDesignRule implements Rule {

    private PersistenceRuleUtil util;

    public ProvideIdentifierPropertyRule(DesignWizard dw) {
        super(dw);
        this.util = new PersistenceRuleUtil();
    }

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

    @Override
    public String getReport() {
        return this.report;
    }
}
