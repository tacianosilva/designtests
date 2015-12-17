package br.edu.ufcg.splab.designtests.util;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.ClassNode;

public class TypesOfCollections {

    public static final ClassNode SET = new ClassNode("java.util.Set");
    public static final ClassNode LIST = new ClassNode("java.util.List");

    private Set<String> collections;
    private Set<String> sets;
    private Set<String> lists;

    public TypesOfCollections() {
        this.collections = new HashSet<String>();
        this.sets = new HashSet<String>();
        this.lists = new HashSet<String>();

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
        this.collections.add("java.util.Set");
        this.sets.add("java.util.Set");
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

    public boolean isCollection(ClassNode node) {
        if (node != null && collections.contains(node.getName())) {
            return true;
        }
        return false;
    }

    public boolean isCollection(String name) {
        if (name != null && collections.contains(name)) {
            return true;
        }
        return false;
    }

    public boolean isSet(ClassNode node) {
        if (node != null && sets.contains(node.getName())) {
            return true;
        }
        return false;
    }

    public boolean isSet(String name) {
        if (name != null && sets.contains(name)) {
            return true;
        }
        return false;
    }

    public boolean isList(ClassNode node) {
        if (node != null && lists.contains(node.getName())) {
            return true;
        }
        return false;
    }

    public boolean isList(String name) {
        if (name != null && lists.contains(name)) {
            return true;
        }
        return false;
    }
}
