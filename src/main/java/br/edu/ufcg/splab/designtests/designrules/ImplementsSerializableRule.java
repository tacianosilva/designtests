package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

/**
 *
 * Rule: The Serializable interface should be implementated for all classes in the Model Package.
 *
 * @author Taciano
 */
public class ImplementsSerializableRule extends AbstractDesignRule {

    public ImplementsSerializableRule(DesignWizard dw) {
        super(dw);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {
            ClassNode serializable = new ClassNode("java.io.Serializable");
            if (!entityNode.implementsInterface(serializable)) {
                this.report += "The class <" + entityNode.getName() + "> "
                            + "doesn't implements interface Serializable.\n";
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
