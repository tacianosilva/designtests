package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;

/**
 *
 * Rule: Both equals(java.lang.Object) and hashCode() in classes of the Model Package
 * doesn't access the field that is identifier property.
 *
 * @author Taciano
 */
public class HashCodeAndEqualsNotUseIdentifierPropertyRule extends AbstractDesignRule implements Rule {

    private PersistenceRuleUtil util;

    public HashCodeAndEqualsNotUseIdentifierPropertyRule(DesignWizardDecorator dwd) {
        super(dwd);
        this.util = new PersistenceRuleUtil();
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            MethodNode equalsMethod = entityNode
                    .getDeclaredMethod("equals(java.lang.Object)");
            MethodNode hashCodeMethod = entityNode
                    .getDeclaredMethod("hashCode()");

            FieldNode field = util.getIdentifierProperty(entityNode);
            Set<FieldNode> accessedFieldsEquals = null;
            Set<FieldNode> accessedFieldsHash = null;

            if (equalsMethod != null) {
                accessedFieldsEquals = equalsMethod.getAccessedFields();
            }
            if (hashCodeMethod != null) {
                accessedFieldsHash = hashCodeMethod.getAccessedFields();
            }

            boolean contem = false;
            if (accessedFieldsEquals != null && accessedFieldsEquals.contains(field)) {
                this.report += "The class <" + entityNode.getName() + "> contains the identifier property <"
                        + field.getShortName() + "> in the equals method.\n";
                contem = true;
            }

            if (accessedFieldsHash != null && accessedFieldsHash.contains(field)) {
                this.report += "The class <" + entityNode.getName() + "> contains the identifier property <"
                        + field.getShortName() + "> in the hashCode method.\n";
                contem = true;
            }

            if (contem) {
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
