package br.edu.ufcg.splab.designtests.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.design.Entity.TypesOfEntities;
import org.designwizard.design.MethodNode;
import org.designwizard.design.PackageNode;
import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

public class ModuleNode {

    protected String name;
    protected Map<TypesOfRelation, Set<Relation>> relations;

    /**
     * Creates a new <code>ModuleNode</code>.
     *
     * @param name
     *            the name of the <code>ModuleNode</code>.
     */
    public ModuleNode(String name) {
        this.name = name;
        this.relations = new HashMap<TypesOfRelation, Set<Relation>>();
    }

    /**
     * Method that returns all the relations with the specified type.
     *
     * @param type
     *            the type of the relation.
     * @return A Collection containing all the relations with the specified
     *         type. If there is no <code>Relation</code> of the specified type,
     *         an empty Set<Realtion> will be returned.
     *
     */
    public Set<Relation> getRelations(TypesOfRelation type) {
        if (this.relations.get(type) == null) {
            return new HashSet<Relation>();
        }
        return this.relations.get(type);
    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>ClassNode</code>
     * objects reflecting all the classes inside the package represented by this
     * <code>PackageNode</code>.
     *
     * @return a <code>java.util.Set</code> containing <code>ClassNode</code>
     *         objects reflecting all the classes inside the package represented
     *         by this <code>PackageNode</code>.
     */
    public Set<ClassNode> getAllClasses() {

        Set<ClassNode> feedBack = new HashSet<ClassNode>();
        Set<Relation> relations = getRelations(TypesOfRelation.CONTAINS);

        for (Relation relation : relations) {
            Entity node = relation.getCalledEntity();
            if (node.getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
                feedBack.add((ClassNode) node);
            }
        }

        return feedBack;
    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>ClassNode</code>
     * objects reflecting all the classes that reference the package represented
     * by this <code>PackageNode</code>.
     */
    public Set<ClassNode> getCallerClasses() {

        Set<ClassNode> feedBack = new HashSet<ClassNode>();
        Set<ClassNode> classes = this.getAllClasses();

        for (ClassNode classNode : classes) {
            feedBack.addAll(classNode.getCallerClasses());
        }

        return feedBack;
    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>ClassNode</code>
     * objects reflecting all the classes that are referenced by the classes
     * inside the package represented by this <code>PackageNode</code>.
     */
    public Set<ClassNode> getCalleeClasses() {

        Set<ClassNode> classes = this.getAllClasses();
        Set<ClassNode> feedBack = new HashSet<ClassNode>();

        for (ClassNode classNode : classes) {

            feedBack.addAll(classNode.getCalleeClasses());

        }

        return feedBack;

    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code>
     * objects reflecting all the methods that reference the classes or
     * interfaces inside the package represented by this <PackageNode>.
     */
    public Set<MethodNode> getCallerMethods() {

        Set<ClassNode> classes = this.getAllClasses();
        Set<MethodNode> feedBack = new HashSet<MethodNode>();

        for (ClassNode classNode : classes) {

            feedBack.addAll(classNode.getCallerMethods());

        }

        return feedBack;

    }

    public Set<MethodNode> getCalleeMethods() {

        Set<MethodNode> feedBack = new HashSet<MethodNode>();
        Set<ClassNode> allClasses = this.getAllClasses();

        for (ClassNode c : allClasses) {

            feedBack.addAll(c.getCalleeMethods());
        }

        return feedBack;

    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>PackageNode</code>
     * objects reflecting all the packages that reference the package
     * represented by this <code>PackageNode</code>.
     *
     * @return a <code>java.util.Set</code> containing <code>PackageNode</code>
     *         objects reflecting all the packages that reference the package
     *         represented by this <code>PackageNode</code>.
     */
    public Set<PackageNode> getCallerPackages() {

        Set<MethodNode> callers = this.getCallerMethods();
        Set<PackageNode> returnValue = new HashSet<PackageNode>();

        for (MethodNode m : callers) {
            returnValue.add(m.getPackage());
        }
        // FIXME lembrar dessa mudança: um pacote chama ele mesmo!
        returnValue.remove(this);

        return returnValue;

    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>PackageNode</code>
     * objects reflecting all the packages that are referenced by the package
     * represented by this <code>PackageNode</code>.
     *
     * @return a <code>java.util.Set</code> containing <code>PackageNode</code>
     *         objects reflecting all the packages that are referenced by the
     *         package represented by this <code>PackageNode</code>.
     */
    public Set<PackageNode> getCalleePackages() {

        Set<ClassNode> called = this.getCalleeClasses();
        Set<PackageNode> returnValue = new HashSet<PackageNode>();

        for (ClassNode c : called) {
            returnValue.add(c.getPackage());
        }

        // FIXME lembrar dessa mudança: um pacote chama ele mesmo!
        returnValue.remove(this);

        return returnValue;

    }

    /**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code>
     * objects reflecting all the methods inside the package represented by this
     * <code>PackageNode</code>.
     *
     * @return a <code>java.util.Set</code> containing <code>MethodNode</code>
     *         objects reflecting all the methods inside the package represented
     *         by this <code>PackageNode</code>.
     */
    public Set<MethodNode> getAllMethods() {

        Set<MethodNode> feedBack = new HashSet<MethodNode>();

        for (ClassNode classInsidePackage : this.getAllClasses())
            feedBack.addAll(classInsidePackage.getAllMethods());

        return feedBack;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object other) {

        if (!(other instanceof ModuleNode))
            return false;
        ModuleNode aux = (ModuleNode) other;
        return this.name.equals(aux.name);
    }
}