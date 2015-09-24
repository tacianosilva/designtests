package br.edu.ufcg.splab.designtests.designrules;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designtests.util.TypesOfCollections;

/**
 * Abstract class to start the properties of the rules for software design in instance of the {@link DesignWizard}.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public abstract class AbstractDesignRule implements Rule {

    /**
     * Facts of the Software Design.
     */
    protected DesignWizard dw;

    /**
     * Types of Collections from Java Collections Frameworks.
     */
    protected TypesOfCollections collections;

    /**
     * Design Rule Name.
     */
    protected String name;

    /**
     * The set of {@link ClassNode} that the rule will be executing.
     */
    protected Set<ClassNode> classNodes;

    /**
     * The set of {@link ClassNode} that the rule passed.
     */
    protected Set<ClassNode> resultTrue;

    /**
     * The set of {@link ClassNode} that the rule failed.
     */
    protected Set<ClassNode> resultFalse;

    /**
     * The Report with errors messages.
     */
    protected String report;

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     */
    public AbstractDesignRule(DesignWizard dw) {
        this.name = this.getClass().getSimpleName();
        this.dw = dw;
        this.classNodes = null;
        this.report = "";
        this.collections = new TypesOfCollections();
        this.resultTrue = new HashSet<ClassNode>();
        this.resultFalse = new HashSet<ClassNode>();
    }

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     * @param classes The set of {@link ClassNode} that the rule will be executing.
     */
    public AbstractDesignRule(DesignWizard dw, Set<ClassNode> classes) {
        this(dw);
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
     * Checks if the parameter belongs to the set of classes of the design
     * {@link DesignWizard#getAllClasses()}.
     * @param classes The set of classNodes to check the pertinence.
     * @return True if all classnodes belongs to the design.
     * False if classes is <code>null</code> or {@link DesignWizard#getAllClasses()} is <code>null</code>.
     */
    protected boolean checkClassNodes(Set<ClassNode> classes) {
        if (dw.getAllClasses() != null) {
            return dw.getAllClasses().containsAll(classes);
        }
        return false;
    }

    /**
     * The Report with errors messages.
     */
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

    /**
     * Returns a <code>MethodNode</code> object that reflects the specified method
     * of the class or interface represented by <code>ClassNode</code> object.
     * The <code>name</code> parameter is a <code>String</code> specifying the simple
     * name of the desired method.
     *
     * @param classNode the <code>ClassNode</code> object that reflects the specified method.
     * @param methodName the method name.
     * @return  the <code>MethodNode</code> object of this class specified by
     * <code>name</code> or <code>null</code> if a method with the specified name is not found.
     */
    protected MethodNode getMethod(ClassNode classNode, String methodName) {
        // TODO Adicionar issue para a implementação deste método no ClassNode do DesignWizard.
        for (MethodNode method: classNode.getAllMethods()) {
            if (method.getShortName().equals(methodName)) return method;
        }
        return null;
    }
}