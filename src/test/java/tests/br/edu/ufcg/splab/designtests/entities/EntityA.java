package tests.br.edu.ufcg.splab.designtests.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityA {

    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private Set<String> collectionSet;

    @Column
    private HashSet<String> collectionHashSet;

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

    public Set<String> getCollectionSet() {
        return collectionSet;
    }

    public void setCollectionSet(Set<String> collectionSet) {
        this.collectionSet = collectionSet;
    }

    public HashSet<String> getCollectionHashSet() {
        return collectionHashSet;
    }

    public void setCollectionHashSet(HashSet<String> collectionHashSet) {
        this.collectionHashSet = collectionHashSet;
    }
}
