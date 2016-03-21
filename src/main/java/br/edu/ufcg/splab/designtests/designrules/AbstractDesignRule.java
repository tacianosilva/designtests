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

    /**
     * ClassNode for represents the {@link Object} class.
     */
    private final ClassNode OBJECT_CLASS = new ClassNode("java.lang.Object");

    /**
     * ClassNode for represents the boolean primitive type.
     */
    private final ClassNode BOOLEAN_PRIMITIVE = new ClassNode("boolean");

    /**
     * ClassNode for represents the {@link Boolean} class.
     */
    private final ClassNode BOOLEAN_CLASS = new ClassNode("java.lang.Boolean");

    /**
     * Facts of the Software Design.
     */
    private DesignWizard designWizard;

    /**
     * Types of Collections from Java Collections Frameworks.
     */
    private TypesOfCollections collections;

    /**
     * Design Rule Name.
     */
    private String name;

    /**
     * The set of {@link ClassNode} that the rule will be executing.
     */
    private Set<ClassNode> classNodes;

    /**
     * The set of {@link ClassNode} that the rule passed.
     */
    private Set<ClassNode> resultsTrue;

    /**
     * The set of {@link ClassNode} that the rule failed.
     */
    private Set<ClassNode> resultsFalse;

    /**
     * The Report with errors messages.
     */
    private String report;

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     */
    public AbstractDesignRule(final DesignWizard dw) {
        this.name = this.getClass().getSimpleName();
        this.designWizard = dw;
        this.classNodes = null;
        this.report = "";
        this.collections = new TypesOfCollections();
        this.resultsTrue = new HashSet<ClassNode>();
        this.resultsFalse = new HashSet<ClassNode>();
    }

    /**
     * Initiates rule properties for software design in instance of the {@link DesignWizard}.
     * @param dw The instance of the {@link DesignWizard} with the software design.
     * @param classes The set of {@link ClassNode} that the rule will be executing.
     */
    public AbstractDesignRule(final DesignWizard dw, final Set<ClassNode> classes) {
        this(dw);
        this.setClassNodes(classes);
    }

    /**
     * Returns the rule's name.
     * @return The rule's name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * ClassNode for represents the {@link Object} class.
     * @return A ClassNode for <code>java.lang.Object</code>.
     */
    public final ClassNode getObjectClass() {
        return this.OBJECT_CLASS;
    }

    /**
     * Returns a DesignWizard's instance with the design.
     * @return A DesignWizard's instance with the design.
     */
    protected final DesignWizard getDesignWizard() {
        return this.designWizard;
    }

    /**
     * Checks if the set {@link AbstractDesignRule#getClassNodes()} attends the rule.
     * @return True if all classNodes attends the rule.
     * @see org.designwizard.designrules.Rule#checkRule()
     */
    public abstract boolean checkRule();

    /**
     * Returns the set of <code>ClassNode</code> objects that passed the rule.
     * @return A set of <code>ClassNode</code> objects or set empty.
     */
    public final Set<ClassNode> getResultsTrue() {
        return resultsTrue;
    }

    /**
     * Returns the set of <code>ClassNode</code> objects that did not pass the rule.
     * @return A set of <code>ClassNode</code> objects or set empty.
     */
    public final Set<ClassNode> getResultsFalse() {
        return resultsFalse;
    }

    /**
     * Add a {@link ClassNode} in the set of true results.
     * @param node The classNode that passed the rule.
     */
    protected final void addResultTrue(final ClassNode node) {
        resultsTrue.add(node);
    }

    /**
     * Add a {@link ClassNode} in the set of false results.
     * @param node The classNode that did not pass the rule.
     */
    protected final void addResultFalse(final ClassNode node) {
        resultsFalse.add(node);
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
    protected final boolean hasGetMethod(final FieldNode fieldNode, final ClassNode entityNode) {
        String shortFieldName = fieldNode.getShortName();
        ClassNode type = fieldNode.getType();
        String strGetOrIs = "";

        if (type != null && (type.equals(BOOLEAN_PRIMITIVE) || type.equals(BOOLEAN_CLASS))) {
            strGetOrIs = "is";
        } else {
            strGetOrIs = "get";
        }

        String methodGetField = strGetOrIs + shortFieldName.substring(0, 1).toUpperCase()
                + shortFieldName.substring(1) + "()";

        MethodNode methodNode = entityNode.getDeclaredMethod(methodGetField);

        if (methodNode == null) {
            return false;
        }

        String methodName = methodNode.getShortName();
        ClassNode methodType = methodNode.getReturnType();

        if (methodName.equals(methodGetField) && methodType.equals(type)) {
            return true;
        }

        return false;
    }

    /**
     * Returns the set of <code>ClassNode</code> objects where this design rule will be executed.
     * @return A set of <code>ClassNode</code> objects or set empty.
     */
    public final Set<ClassNode> getClassNodes() {
        return classNodes;
    }

    /**
     * Put a set of <code>ClassNode</code> objects where this design rule will be executed.
     * @param classes A set of <code>ClassNode</code> objects.
     */
    public final void setClassNodes(final Set<ClassNode> classes) {
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
        this.resultsTrue = new HashSet<ClassNode>();
        this.resultsFalse = new HashSet<ClassNode>();
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
        if (designWizard.getAllClasses() != null) {
            return designWizard.getAllClasses().containsAll(classes);
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
     * Checks if the report is empty.
     * @return True if it's empty.
     */
    public final boolean isEmptyReport() {
        if (this.getReport().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Add new errors messages at the report.
     * @param messages A string with errors messages.
     */
    protected final void addReport(final String messages) {
        this.report += messages;
    }

    /**
     * Checks if the classNode implements or inherits from the Collection type of
     * the Java Collection Framework.
     * @param node A classNode.
     * @return True if the classNode implements or inherits from the Collection type.
     */
    public final boolean isCollection(final ClassNode node) {
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
    public final boolean isSet(final ClassNode node) {
        if (collections.isSet(node)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the classNode implements or inherits from the List type of
     * the Java Collection Framework.
     * @param node A classNode.
     * @return True if the classNode implements or inherits from the List type.
     */
    public final boolean isList(final ClassNode node) {
        if (collections.isList(node)) {
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
    protected final MethodNode getMethod(final ClassNode classNode, final String methodName) {
        for (MethodNode method: classNode.getAllMethods()) {
            if (method.getShortName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Returns a <code>MethodNode</code> object that reflects the specified equals method
     * of the class or superClass represented by <code>ClassNode</code> object.
     * @param classNode the <code>ClassNode</code> object that reflects the specified method.
     * @return the <code>MethodNode</code> object of this class specified by
     * <code>name</code> or <code>null</code> if a method with the specified name is not found.
     */
    protected final MethodNode getEqualsMethod(final ClassNode classNode) {
        MethodNode equalsMethod = classNode.getDeclaredMethod("equals(java.lang.Object)");
        ClassNode superClass = classNode.getSuperClass();
        if (equalsMethod == null && !getObjectClass().equals(superClass)) {
            equalsMethod = classNode.getInheritedMethod("equals(java.lang.Object)");
        }
        return equalsMethod;
    }

    /**
     * Returns a <code>MethodNode</code> object that reflects the specified hashCode method
     * of the class or superClass represented by <code>ClassNode</code> object.
     * @param classNode the <code>ClassNode</code> object that reflects the specified method.
     * @return the <code>MethodNode</code> object of this class specified by
     * <code>name</code> or <code>null</code> if a method with the specified name is not found.
     */
    protected final MethodNode getHashCodeMethod(final ClassNode classNode) {
        MethodNode hashCodeMethod = classNode.getDeclaredMethod("hashCode()");
        ClassNode superClass = classNode.getSuperClass();
        if (hashCodeMethod == null && !getObjectClass().equals(superClass)) {
            hashCodeMethod = classNode.getInheritedMethod("hashCode()");
        }
        return hashCodeMethod;
    }
}