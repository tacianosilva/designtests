package br.edu.ufcg.splab.designtests.util;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.ClassNode;

/**
 * Utility class with functions related to collections to facilitate
 * the implementation of design rules.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class TypesOfCollections {

    /**
     * String for represents the {@link java.util.Set} name interface.
     */
    public static final String SET_NAME = "java.util.Set";

    /**
     * ClassNode for represents the {@link java.util.Set} interface.
     */
    public static final ClassNode SET = new ClassNode(SET_NAME);

    /**
     * ClassNode for represents the {@link java.util.List} interface.
     */
    public static final ClassNode LIST = new ClassNode("java.util.List");

    /**
     * A Set with all names of collections classes.
     */
    private Set<String> collections;

    /**
     * A Set with all classes names of the type <code>Set</code>.
     */
    private Set<String> sets;

    /**
     * A Set with all classes names of the type <code>List</code>.
     */
    private Set<String> lists;

    /**
     * Extract all informations of the collections classes.
     */
    public TypesOfCollections() {
        this.collections = new HashSet<>();
        this.sets = new HashSet<>();
        this.lists = new HashSet<>();

        // Hierarquia de Interfaces do Collection
        this.collections.add("java.util.Collection");
        this.collections.add("java.util.BeanContext");
        this.collections.add("java.util.BeanContextServices");
        this.collections.add("java.util.BlockingDeque");
        this.collections.add("java.util.BlockingQueue");
        this.collections.add("java.util.Deque");
        this.lists.add("java.util.List");
        this.sets.add("java.util.NavigableSet");
        this.collections.add("java.util.Queue");
        this.collections.add(SET_NAME);
        this.sets.add(SET_NAME);
        this.sets.add("java.util.SortedSet");
        this.collections.add("java.util.TransferQueue");

        this.collections.add("java.util.AbstractCollection");
        this.collections.add("java.util.AbstractList");
        this.collections.add("java.util.AbstractQueue");
        this.collections.add("java.util.SequentialList");
        this.sets.add("java.util.AbstractSet");
        this.collections.add("java.util.ArrayBlockingQueue");
        this.collections.add("java.util.ArrayDeque");
        this.collections.add("java.util.ArrayList");
        this.collections.add("java.util.AttributeList");
        this.collections.add("java.util.BeanContextServicesSupport");
        this.collections.add("java.util.BeanContextSupport");
        this.sets.add("java.util.ConcurrentHashMap.KeySetView");
        this.collections.add("java.util.ConcurrentLinkedDeque");
        this.collections.add("java.util.ConcurrentLinkedQueue");
        this.sets.add("java.util.ConcurrentSkipListSet");
        this.collections.add("java.util.CopyOnWriteArrayList");
        this.sets.add("java.util.CopyOnWriteArraySet");
        this.collections.add("java.util.DelayQueue");
        this.sets.add("java.util.EnumSet");
        this.sets.add("java.util.HashSet");
        this.sets.add("java.util.JobStateReasons");
        this.collections.add("java.util.LinkedBlockingDeque");
        this.collections.add("java.util.LinkedBlockingQueue");
        this.sets.add("java.util.LinkedHashSet");
        this.collections.add("java.util.LinkedList");
        this.collections.add("java.util.LinkedTransferQueue");
        this.collections.add("java.util.PriorityBlockingQueue");
        this.collections.add("java.util.PriorityQueue");
        this.collections.add("java.util.RoleList");
        this.collections.add("java.util.RoleUnresolvedList");
        this.collections.add("java.util.Stack");
        this.collections.add("java.util.SynchronousQueue");
        this.sets.add("java.util.TreeSet");
        this.collections.add("java.util.Vector");

        this.collections.addAll(sets);
        this.collections.addAll(lists);
    }

    /**
     * Checks if the classNode represents a java collection.
     * @param node A classNode.
     * @return True if if the classNode is a java collection.
     */
    public final boolean isCollection(final ClassNode node) {
        if (node != null && collections.contains(node.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the class name represents a java collection.
     * @param name A complete class name with your package.
     * @return True if if the class is a java collection.
     */
    public final boolean isCollection(final String name) {
        if (name != null && collections.contains(name)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the classNode represents a collection of the Set type.
     * @param node A classNode.
     * @return True if if the classNode is a Set.
     */
    public final boolean isSet(final ClassNode node) {
        if (node != null && sets.contains(node.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the class name represents a collection of the Set type.
     * @param  name A complete class name with your package.
     * @return True if if the class name is a Set.
     */
    public final boolean isSet(final String name) {
        if (name != null && sets.contains(name)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the classNode represents a collection of the List type.
     * @param node A classNode.
     * @return True if if the classNode is a List.
     */
    public final boolean isList(final ClassNode node) {
        if (node != null && lists.contains(node.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the class name represents a collection of the List type.
     * @param  name A complete class name with your package.
     * @return True if if the class name is a List.
     */
    public final boolean isList(final String name) {
        if (name != null && lists.contains(name)) {
            return true;
        }
        return false;
    }
}