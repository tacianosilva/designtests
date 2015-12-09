package br.edu.ufcg.splab.designtests.designrules;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.designrules.Rule;

import br.edu.ufcg.splab.designtests.util.TypesOfCollections;

/**
 * Abstract class to start the properties of the rules for software design in
 * instance of the {@link DesignWizard}.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public abstract class AbstractDesignRule implements Rule {

    protected final ClassNode objectClass = new ClassNode("java.lang.Object");

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

    /**
     * Returns the rule's name.
     * @return The rule's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the set {@link AbstractDesignRule#getClassNodes()} attends the rule.
     * @see org.designwizard.designrules.Rule#checkRule()
     */
    public abstract boolean checkRule();

    /**
     * Returns the set of <code>ClassNode</code> objects that passed the rule.
     * @return A set of <code>ClassNode</code> objects or set empty.
     */
    public Set<ClassNode> getResultsTrue() {
        return resultTrue;
    }

    /**
     * Returns the set of <code>ClassNode</code> objects that did not pass the rule.
     * @return A set of <code>ClassNode</code> objects or set empty.
     */
    public Set<ClassNode> getResultsFalse() {
        return resultFalse;
    }

    /**
     * Add a {@link ClassNode} in the set of true results.
     * @param node The classNode that passed the rule.
     */
    public final void addResultTrue(final ClassNode node) {
        resultTrue.add(node);
    }

    /**
     * Add a {@link ClassNode} in the set of false results.
     * @param node The classNode that did not pass the rule.
     */
    public final void addResultFalse(final ClassNode node) {
        resultFalse.add(node);
    }

    /**
     * Verifies if exists set method for the field.
     * @param fieldNode The field contained in the class.
     * @param entityNode Entity that contains the field.
     * @return True if exists set method in the entity for the field.
     */
    protected final boolean hasSetMethod(final FieldNode fieldNode, final ClassNode entityNode) {
        String fieldName = fieldNode.getShortName();
        ClassNode type = fieldNode.getType();
        ClassNode voidType = new ClassNode("void");
        String setName = "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1) + "(" + type.getName() + ")";

        MethodNode methodNode = entityNode.getDeclaredMethod(setName);

        if (methodNode == null) {
            return false;
        }

        String methodName = methodNode.getShortName();
        ClassNode methodType = methodNode.getReturnType();

        if (methodName.equals(setName) && methodType.equals(voidType)) {
            return true;
        }
        return false;
    }

    /**
     * Verifies if exists get method for the field.
     * @param fieldNode The field contained in the class.
     * @param entityNode Entity that contains the field.
     * @return True if exists get method in the entity for the field.
     */
    protected boolean hasGetMethod(FieldNode fieldNode, ClassNode entityNode) {
        String name = fieldNode.getShortName();
        ClassNode type = fieldNode.getType();
        String getName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1)+"()";

        MethodNode methodNode = entityNode.getDeclaredMethod(getName);

        if (methodNode == null) {
            return false;
        }

        String methodName = methodNode.getShortName();
        ClassNode methodType = methodNode.getReturnType();

        if (methodName.equals(getName) && methodType.equals(type)) {
            return true;
        }

        return false;
    }

    /**
     * Returns the set of <code>ClassNode</code> objects where this design rule will be executed.
     * @return A set of <code>ClassNode</code> objects or set empty.
     */
    public Set<ClassNode> getClassNodes() {
        return classNodes;
    }

    /**
     * Put a set of <code>ClassNode</code> objects where this design rule will be executed.
     * @param classes A set of <code>ClassNode</code> objects where this design rule will be executed.
     */
    public void setClassNodes(Set<ClassNode> classes) {
        resetCollections();
        if (checkClassNodes(classes)) {
            this.classNodes = classes;
        }
    }

    /**
     * Resets collections fields.
     */
    private void resetCollections() {
        this.classNodes = new HashSet<ClassNode>();
        this.resultTrue = new HashSet<ClassNode>();
        this.resultFalse = new HashSet<ClassNode>();
        this.report = "";
    }

    /**
    * Put a <code>ClassNode</code> object where this design rule will be executed.
    * @param classe A <code>ClassNode</code> objects where this design rule will be executed.
    */
    public final void setClassNode(final ClassNode classe) {
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
     * @return True if all classnodes belongs to the design. False if classes is <code>null</code>
     * or {@link DesignWizard#getAllClasses()} is <code>null</code>.
     */
    protected final boolean checkClassNodes(final Set<ClassNode> classes) {
        if (dw.getAllClasses() != null) {
            return dw.getAllClasses().containsAll(classes);
        }
        return false;
    }

    /**
     * The Report with errors messages.
     * @return A string with errors messages.
     */
    public final String getReport() {
        return this.report;
    }

    /**
     * Checks if the classNode implements or inherits from the Collection type of
     * the Java Collection Framework.
     * @param node A classNode.
     * @return True if the classNode implements or inherits from the Collection type.
     */
    public boolean isCollection(ClassNode node) {
        if (collections.isCollection(node)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the classNode implements or inherits from the Set type of
     * the Java Collection Framework.
     * @param node A classNode.
     * @return True if the classNode implements or inherits from the Set type.
     */
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
        for (MethodNode method: classNode.getAllMethods()) {
            if (method.getShortName().equals(methodName)) return method;
        }
        return null;
    }

    protected MethodNode getEqualsMethod(ClassNode classNode) {
        MethodNode equalsMethod = classNode.getDeclaredMethod("equals(java.lang.Object)");
        ClassNode superClass = classNode.getSuperClass();
        if (equalsMethod == null && !objectClass.equals(superClass)) {
            equalsMethod = classNode.getInheritedMethod("equals(java.lang.Object)");
        }
        return equalsMethod;
    }

    protected MethodNode getHashCodeMethod(ClassNode classNode) {
        MethodNode hashCodeMethod = classNode.getDeclaredMethod("hashCode()");
        ClassNode superClass = classNode.getSuperClass();
        if (hashCodeMethod == null && !objectClass.equals(superClass)) {
            hashCodeMethod = classNode.getInheritedMethod("hashCode()");
        }
        return hashCodeMethod;
    }

}