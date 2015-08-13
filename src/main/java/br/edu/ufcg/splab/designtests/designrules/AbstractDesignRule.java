package br.edu.ufcg.splab.designtests.designrules;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;
import br.edu.ufcg.splab.designtests.util.TypesOfCollections;

public abstract class AbstractDesignRule implements Rule {

    protected DesignWizardDecorator dwd;
    protected TypesOfCollections collections;

    /**
     * The set of classnodes that the rule will be executing
     */
    protected Set<ClassNode> classNodes;
    protected Set<ClassNode> resultTrue;
    protected Set<ClassNode> resultFalse;
    protected String report;

    public AbstractDesignRule(DesignWizardDecorator dwd) {
        this.dwd = dwd;
        this.classNodes = null;
        this.report = "";
        this.collections = new TypesOfCollections();
        this.resultTrue = new HashSet<ClassNode>();
        this.resultFalse = new HashSet<ClassNode>();
    }

    public AbstractDesignRule(DesignWizardDecorator dwd, Set<ClassNode> classes) {
        this(dwd);
        this.setClassNodes(classes);
    }

    public abstract boolean checkRule();

    public Set<ClassNode> getResultsTrue() {
        return resultTrue;
    }

    public Set<ClassNode> getResultsFalse() {
        return resultFalse;
    }

    public void addResultTrue(ClassNode node) {
        resultTrue.add(node);
    }

    public void addResultFalse(ClassNode node) {
        resultFalse.add(node);
    }

    protected boolean hasSetMethod(FieldNode fieldNode, Set<MethodNode> declaredMethods) {
        // TODO Auto-generated method stub
        return false;
    }

    protected boolean hasGetMethod(FieldNode fieldNode, Set<MethodNode> declaredMethods) {
        // TODO Auto-generated method stub
        return false;
    }

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

    public boolean isCollection(ClassNode node) {
        if (collections.isCollection(node)) {
            return true;
        }
        return false;
    }

    public boolean isSet(ClassNode node) {
        if (collections.isSet(node)) {
            return true;
        }
        return false;
    }
}