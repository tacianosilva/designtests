package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

/**
 *
 * Rule: Declaration of Collection of the type Set in classes of the Model Package.
 *
 * @author Taciano
 */
public class UseSetCollectionRule extends AbstractDesignRule {

    public UseSetCollectionRule(DesignWizardDecorator dwd) {
        super(dwd);
    }

    @Override
    public boolean checkRule() {
        // TODO Auto-generated method stub
        Collection<ClassNode> allModelClasses = getClassNodes();

        for (ClassNode entityNode : allModelClasses) {

            Set<FieldNode> declaredFields= entityNode.getAllFields();

            for (FieldNode fieldNode : declaredFields) {
                // FIXME Os dados dos tipos dos atributos não estão completos
                ClassNode type = fieldNode.getType();
                ClassNode collection = new ClassNode("java.util.Collection");
                ClassNode set = new ClassNode("java.util.Set");
                if (type.implementsInterface(collection)) {
                    if (!type.implementsInterface(set)) {
                        System.out.println("FieldNode: " + fieldNode.getName());
                        System.out.println("Type FieldNode: " + type.getName());
                        System.out.println("Implements Interfaces? " + type.getImplementedInterfaces());

                        this.report += "The field <" + fieldNode.getName() + "> of the class <" + fieldNode.getName()
                            + " implements interface Collection but it doesn't implements interface Set.\n";
                    }
                }
            }
        }
        return this.report.equals("") ? true : false;
    }

    @Override
    public String getReport() {
        return this.report;
    }
}
