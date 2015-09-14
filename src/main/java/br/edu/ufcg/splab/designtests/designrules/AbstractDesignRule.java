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
    protected String name;

    /**
     * The set of classnodes that the rule will be executing
     */
    protected Set<ClassNode> classNodes;
    protected Set<ClassNode> resultTrue;
    protected Set<ClassNode> resultFalse;
    protected String report;

    public AbstractDesignRule(DesignWizardDecorator dwd) {
        this.name = this.getClass().getSimpleName();
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

    public String getName() {
        return this.name;
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

    protected boolean hasSetMethod(FieldNode fieldNode, ClassNode entityNode) {
        String name = fieldNode.getShortName();
        ClassNode type = fieldNode.getType();
        ClassNode voidType = new ClassNode("void");
        String getName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1)+"("+type.getName()+")";

        MethodNode methodNode = entityNode.getDeclaredMethod(getName);

        if (methodNode == null) return false;

        String methodName = methodNode.getShortName();
        ClassNode methodType = methodNode.getReturnType();

        if (methodName.equals(getName) && methodType.equals(voidType)) {
            return true;
        }
        return false;
    }

    protected boolean hasGetMethod(FieldNode fieldNode, ClassNode entityNode) {
        String name = fieldNode.getShortName();
        ClassNode type = fieldNode.getType();
        String getName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1)+"()";

        MethodNode methodNode = entityNode.getDeclaredMethod(getName);

        if (methodNode == null) return false;

        String methodName = methodNode.getShortName();
        ClassNode methodType = methodNode.getReturnType();

        if (methodName.equals(getName) && methodType.equals(type)) {
            return true;
        }

        return false;
    }

    /**
     *
     * @return
     * @throws InexistentEntityException
     */
    protected Set<ClassNode> getClassNodes() {
        return classNodes;
    }

    /**
     *
     * @param classes
     */
    public void setClassNodes(Set<ClassNode> classes) {
        resetCollections();
        if (checkClassNodes(classes)) {
            this.classNodes = classes;
        }
    }

    private void resetCollections() {
        this.classNodes = new HashSet<ClassNode>();
        this.resultTrue = new HashSet<ClassNode>();
        this.resultFalse = new HashSet<ClassNode>();
        this.report = "";
    }

    /**
    *
    * @param classes
    */
   public void setClassNode(ClassNode classe) {
       resetCollections();
       Set<ClassNode> classes = new HashSet<ClassNode>();
       classes.add(classe);
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