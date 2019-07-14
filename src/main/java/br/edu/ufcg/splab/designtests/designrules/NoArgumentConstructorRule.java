package br.edu.ufcg.splab.designtests.designrules;

import java.util.Collection;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

/**
 * Rule: A default constructor must be implemented in classes of the Model Package.
 * All persistent classes must have a default constructor (which can be non-public)
 * so that Hibernate can instantiate them using java.lang.reflect.Constructor.newInstance().
 *
 * JPA requires that this constructor be defined as public or protected.
 *
 * See more on section 2.1.1 in
 * <a href="https://docs.jboss.org/hibernate/orm/5.0/userguide/en-US/html/ch02.html#domainmodel-pojo">
 * Hibernate Docs</a>.
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class NoArgumentConstructorRule extends AbstractDesignRule implements Rule {

    /**
     * Builds the rule for the designwizard instance.
     * @param dw A {@link DesignWizard} instance.
     */
    public NoArgumentConstructorRule(final DesignWizard dw) {
        super(dw);
    }

    /**
     * Checks if the set {@link AbstractDesignRule#getClassNodes()} attends the rule.
     * Checks the class and the inherited methods from the super class.
     * @see br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule#checkRule()
     */
    @Override
    public final boolean checkRule() {
        Collection<ClassNode> classes = getClassNodes();

        for (ClassNode entityNode : classes) {

            // Checks the class and the inherited methods from the super class
            Set<MethodNode> constructors = entityNode.getConstructors();
            boolean passed = false;

            for (MethodNode methodNode : constructors) {
                Set<ClassNode> parameters = methodNode.getParameters();
                if (parameters.isEmpty()) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                this.addReport("The class <" + entityNode.getName()
                    + "> doesn't contain a default constructor.\n");
                addResultFalse(entityNode);
            } else {
                addResultTrue(entityNode);
            }
        }
        return this.isEmptyReport();
    }
}
