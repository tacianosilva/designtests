package tests.br.edu.ufcg.splab.designtests.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class EntityD implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> collectionList;

    @Column
    @ElementCollection(fetch=FetchType.EAGER)
    private Collection<String> collectionArrayList;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityE> entityESet = new HashSet<EntityE>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Collection<EntityB> collectionB = new HashSet<EntityB>();

    public EntityD(String name) {
        this.name = name;
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

    public Set<EntityE> getEntityESet() {
        return entityESet;
    }

    public void setEntityESet(Set<EntityE> entityESet) {
        this.entityESet = entityESet;
    }

    public Collection<EntityB> getCollectionB() {
        return this.collectionB;
    }

    public void setCollectionB(Collection<EntityB> collection) {
        this.collectionB = collection;
    }
}
