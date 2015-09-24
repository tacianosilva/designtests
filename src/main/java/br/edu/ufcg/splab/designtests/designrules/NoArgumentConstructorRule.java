package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

/**
 *
 * Rule: A default constructor must be implemented in classes of the Model Package.
 * All persistent classes must have a default constructor (which can be non-public)
 * so that Hibernate can instantiate them using java.lang.reflect.Constructor.newInstance().
 *
 * @author Taciano
 */
public class NoArgumentConstructorRule extends AbstractDesignRule implements Rule {

    public NoArgumentConstructorRule(DesignWizard dw) {
        super(dw);
    }

    @Override
    public boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            Set<MethodNode> constructors = entityNode.getConstructors();
            boolean passed = false;

            for (MethodNode methodNode : constructors) {
                //TODO Será necessário verificar os métodos herdados?
                Set<ClassNode> parameters = methodNode.getParameters();
                if (parameters.isEmpty()) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                this.report += "The class <" + entityNode.getName() + "> doesn't contain a default constructor.\n";
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
