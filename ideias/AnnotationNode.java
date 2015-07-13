package org.designwizard.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
 *
 * <code>ClassNode</code> objects are constructed automatically by the <code>DesignWizard</code> class when classes
 * are loaded. To get access to a desired class extracted, do not use the constructor of this class. Instead, use the
 * class <code>DesignWizard</code> as it follows:
 *
 * <blockquote><pre>
 *      DesignWizard dw = new DesignWizard("/home/user/application/classes");
 *        ClassNode c = dw.getClass("MyAplicationClassExample");
 * </pre></blockquote>
 *
 * Instances of the class <code>ClassNode</code> represent classes and
 * interfaces in the code extracted.  An enum and array are a kind of
 * class. The primitive Java types (<code>boolean</code>,
 * <code>byte</code>, <code>char</code>, <code>short</code>,
 * <code>int</code>, <code>long</code>, <code>float</code>, and
 * <code>double</code>), and the keyword <code>void</code> are also
 * represented as <code>Class</code> objects.
 *
 * Inner Classes are also represented as <code>Class</code> objects, but with
 * the the special identifier $. For example:
 *
 * <blockquote><pre>
 *         foo.bar.OuterClass$InnerClass
 * </pre></blockquote>
 *
 *
 **/

public class AnnotationNode extends AbstractEntity implements Entity {

    /**
     * Attributes
     */
    private boolean isPrimitive;
    private Set<ClassNode> classesAnnotated;


    /**
     * Creates a new <code>ClassEntity</code>.
     * @param name the name of the <code>ClassEntity</code>.
     * @param visibility the visibility of the <code>ClassEntity</code>.
     */
    public AnnotationNode(String name) {
        super();
        super.name = name;

        this.isPrimitive = isPrimitive(name);

        super.type = TypesOfEntities.ANNOTATION;
        super.relations = new HashMap<TypesOfRelation,Set<Relation>>();

        this.classesAnnotated = new HashSet<ClassNode>();
    }

    /**
     * Determines if the specified <code>ClassNode</code> object represents a
     * primitive type.
     *
     * <p> There are nine predefined <code>ClassNode</code> objects to represent
     * the eight primitive types and void.  These are created by the Java
     * Virtual Machine, and have the same names as the primitive types that
     * they represent, namely <code>boolean</code>, <code>byte</code>,
     * <code>char</code>, <code>short</code>, <code>int</code>,
     * <code>long</code>, <code>float</code>, and <code>double</code>.
     *
     * @return true if and only if this class represents a primitive type
     *
     * @see     java.lang.Boolean#TYPE
     * @see     java.lang.Character#TYPE
     * @see     java.lang.Byte#TYPE
     * @see     java.lang.Short#TYPE
     * @see     java.lang.Integer#TYPE
     * @see     java.lang.Long#TYPE
     * @see     java.lang.Float#TYPE
     * @see     java.lang.Double#TYPE
     * @see     java.lang.Void#TYPE
     */
    private boolean isPrimitive(String name) {

        if (name.equals("void") || name.equals("boolean") || name.equals("byte") || name.equals("char")
            || name.equals("short") || name.equals("int") || name.equals("long") || name.equals("float")
            || name.equals("double") ) return true;

        return false;

    }

    /* (non-Javadoc)
     * @see designwizard.design.entity.AbstractEntity#addRelation(designwizard.design.relation.Relation)
     */
    @Override
    public void addRelation(Relation relation) {
        super.addRelation(relation);
        if (relation.getType().equals(TypesOfRelation.IS_ANNOTATED_BY)) {
            ClassNode classAnnotated = (ClassNode) relation.getCalledEntity();
            this.classesAnnotated.add(classAnnotated);
        }
    }

    /**
     * @return
     */
    public Set<ClassNode> getClassesAnnotated() {
        return classesAnnotated;
    }

    /* (non-Javadoc)
     * @see designwizard.design.entity.AbstractEntity#getShortName()
     */
    @Override
    public String getShortName() {
        return super.getShortName();
    }

    /* (non-Javadoc)
     * @see designwizard.design.entity.Entity#getImpactOfAChange()
     */
    public List<String[]> getImpactOfAChange() {
        List<String[]> result = new ArrayList<String[]>();
        return result;
    }

    /**
     * Converts the object to a string.
     *
     * @return a string representation of this class object.
     */
    public String toString() {
        StringBuffer feedBack = new StringBuffer();
        feedBack.append("#"+this.name+"\n");
        return feedBack.toString();
    }

    /**
     * Determines if the specified <code>ClassNode</code> object represents a
     * primitive type.
     *
     * <p> There are nine predefined <code>ClassNode</code> objects to represent
     * the eight primitive types and void.  These are created by the Java
     * Virtual Machine, and have the same names as the primitive types that
     * they represent, namely <code>boolean</code>, <code>byte</code>,
     * <code>char</code>, <code>short</code>, <code>int</code>,
     * <code>long</code>, <code>float</code>, and <code>double</code>.
     *
     * @return true if and only if this class represents a primitive type.
     *
     */
    public boolean isPrimitive() {
        return this.isPrimitive;
    }

    /**
     * Determines if the specified <code>ClassNode</code> object represents an
     * inner class.
     *
     * @return true if and only if this class represents an inner class.
     *
     */
    public boolean isInnerClass() {
        return this.modifiers.contains(Modifier.INNERCLASS);
    }

    /**
     * Returns <tt>true</tt> if and only if the underlying class
     * is an anonymous class.
     *
     * @return <tt>true</tt> if and only if this class is an anonymous class.
     */
    public boolean isAnonymous() {
        return this.modifiers.contains(Modifier.ANONYMOUS);
    }

     /**
     * Determines if this <code>ClassNode</code> object represents an array class.
     *
     * @return  <code>true</code> if this object represents an array class;
     *          <code>false</code> otherwise.
     */
    public boolean isArray() {

        return name.contains("[");

    }


    /* (non-Javadoc)
     * @see designwizard.design.Entity#getClassNode()
     */
    public ClassNode getClassNode() {
        return null;
    }


     /**
     * Returns the  name of the entity (class, interface, array class,
     * primitive type, or void) represented by this <tt>ClassNode</tt> object,
     * as a <tt>String</tt>.
     *
     * <p> If this class object represents a primitive type or void, then the
     * name returned is the own name (<code>boolean</code>, <code>byte</code>,
     * <code>char</code>, <code>short</code>, <code>int</code>,
     * <code>long</code>, <code>float</code>, and <code>double</code>).
     *
     *
     * @return  the name of the class or interface
     *          represented by this object.
     */
    public String getClassName() {
        return super.name;
    }

    /**
     * Verifies if this entity is equals the other entity.
     * @return true if this entity is equals the other entity or false if not.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        try {
            AnnotationNode parameterEntity = (AnnotationNode)other;
            return this.name.equals(parameterEntity.getName());
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
     * the methods that reference the class or interface represented by this <ClassNode>.
     */
    public Set<MethodNode> getCallerMethods() {
        return null;
    }

    @Override
    public Set<MethodNode> getCalleeMethods() {
        return null;

    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
     * the classes that reference the class or interface represented by this ClassNode.
     */
    @Override
    public Set<ClassNode> getCallerClasses() {
        return null;

    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
     * the classes that are referenced by the class or interface represented by this ClassNode.
     */
    @Override
    public Set<ClassNode> getCalleeClasses() {
        return null;
    }

    @Override
    public Set<PackageNode> getCalleePackages() {
        return null;
    }

    @Override
    public Set<PackageNode> getCallerPackages() {
        return null;
    }

    @Override
    public PackageNode getPackage() {
        // TODO Auto-generated method stub
        return null;
    }
}
