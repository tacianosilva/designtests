package br.edu.ufcg.splab.designtests.designrules;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;

/**
 *
 * Rule: Both equals(java.lang.Object) and hashCode() in classes of the Model Package
 * doesn't access the field that is identifier property.
 *
 * Methods must be declared in the persistent class.
 *
 * @author Taciano
 */
public class HashCodeAndEqualsNotUseIdentifierPropertyRule extends AbstractDesignRule implements Rule {

    private PersistenceRuleUtil util;

    public HashCodeAndEqualsNotUseIdentifierPropertyRule(DesignWizard dw) {
        super(dw);
        this.util = new PersistenceRuleUtil();
    }

    /**
     * Check if the equals(java.lang.Object) method and hashCode() method access the identifier field.
     * @return True if exists methods and if it doesn't access the identifier field. Otherwise, returns false.
     */
    @Override
    public boolean checkRule() {
        Set<ClassNode> classes = getClassNodes();

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

            HashCodeAndEqualsRule rule = new HashCodeAndEqualsRule(dw);
            rule.setClassNode(entityNode);
            if (contem || !rule.checkRule()) {
                this.report += rule.getReport();
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
