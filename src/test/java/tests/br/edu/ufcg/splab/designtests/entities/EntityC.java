package tests.br.edu.ufcg.splab.designtests.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class EntityC {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String field;

    @Column
    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> collectionSet;

    @Column
    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> collectionHashSet;

    public EntityC() {
        super();
    }

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

    public String getField() {
        return field;
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

    public void setCollectionHashSet(HashSet<String> collectionHashSet) {
        this.collectionHashSet = collectionHashSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        EntityC other = (EntityC) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
