package tests.br.edu.ufcg.splab.designtests.entities;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name="colSeq", allocationSize=1)
public class EntityA implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="colSeq")
    private Integer id;

    @Column
    private String name;

    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> collectionSet;

    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> collectionHashSet;

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

    public Set<String> getCollectionHashSet() {
        return collectionHashSet;
    }

    public void setCollectionHashSet(Set<String> collectionHashSet) {
        this.collectionHashSet = collectionHashSet;
    }
}
