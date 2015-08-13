package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

            Set<FieldNode> declaredFields = entityNode.getAllFields();
            boolean passed = false;

            for (FieldNode fieldNode : declaredFields) {
                Set<ClassNode> annotations = new HashSet<ClassNode>(); //TODO Modificar para fieldNode.getAnnotations();
                ClassNode id = new ClassNode("javax.persistence.Id");
                if (annotations.contains(id)) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
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
