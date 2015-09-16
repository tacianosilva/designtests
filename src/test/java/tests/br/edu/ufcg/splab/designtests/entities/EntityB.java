package tests.br.edu.ufcg.splab.designtests.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityB {

    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private List<String> collectionList;

    @Column
    private ArrayList<String> collectionArrayList;

    @Column
    private LinkedList<String> collectionLinkedList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<String> collectionList) {
        this.collectionList = collectionList;
    }

    public ArrayList<String> getCollectionArrayList() {
        return collectionArrayList;
    }

    public void setCollectionArrayList(ArrayList<String> collectionArrayList) {
        this.collectionArrayList = collectionArrayList;
    }

    public LinkedList<String> getCollectionLinkedList() {
        return collectionLinkedList;
    }

    public void setCollectionLinkedList(LinkedList<String> collectionLinkedList) {
        this.collectionLinkedList = collectionLinkedList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityB other = (EntityB) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
