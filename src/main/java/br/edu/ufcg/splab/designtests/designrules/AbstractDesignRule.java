package br.edu.ufcg.splab.designtests.designrules;

import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.designrules.Rule;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

public abstract class AbstractDesignRule implements Rule {

    protected DesignWizardDecorator dwd;
    /**
     * The set of classnodes that the rule will be executing
     */
    protected Set<ClassNode> classNodes;
    protected String report;

    public AbstractDesignRule(DesignWizardDecorator dwd) {
        super();
        this.dwd = dwd;
        this.classNodes = null;
        this.report = "";
    }

    public AbstractDesignRule(DesignWizardDecorator dwd, Set<ClassNode> classes) {
        super();
        this.dwd = dwd;
        this.setClassNodes(classes);
        this.report = "";
    }

    public abstract boolean checkRule();

    /**
     *
     * @return
     * @throws InexistentEntityException
     */
    protected Set<ClassNode> getClassNodes() {
        if (classNodes == null) {
            this.classNodes = dwd.getClassesFromCode();
        }
        return classNodes;
    }

    /**
     *
     * @param classes
     */
    public void setClassNodes(Set<ClassNode> classes) {
        this.classNodes = null;
        if (checkClassNodes(classes)) {
            this.classNodes = classes;
        }
    }

    /**
     * Checks if the parameter belongs to the set of classes of the design {@link DesignWizardDecorator}.
     * @param classes The set of classNodes to check the pertinence.
     * @return True if all classnodes belongs to the design.
     */
    protected boolean checkClassNodes(Set<ClassNode> classes) {
        return dwd.getClassesFromCode().containsAll(classes);
    }

    public abstract String getReport();

}