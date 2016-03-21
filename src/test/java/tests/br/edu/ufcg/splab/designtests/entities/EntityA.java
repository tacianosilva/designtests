package tests.br.edu.ufcg.splab.designtests.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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

    @Column
    private Boolean verified;

    @Column
    private boolean confirmed;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityB> entityBSet = new HashSet<EntityB>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityC> entityCSet = new HashSet<EntityC>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityD> entityDSet = new HashSet<EntityD>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name = "EntityA_EntityE_List")
    private List<EntityE> entityEList = new ArrayList<EntityE>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name = "EntityA_EntityE_SortedSet")
    @OrderBy // Usará a primary key
    private SortedSet<EntityE> entityESortedSet = new TreeSet<EntityE>();

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

    public Set<EntityB> getEntityBSet() {
        return entityBSet;
    }

    public void setEntityBSet(Set<EntityB> entityBSet) {
        this.entityBSet = entityBSet;
    }

    public Set<EntityC> getEntityCSet() {
        return entityCSet;
    }

    public void setEntityCSet(Set<EntityC> entityCSet) {
        this.entityCSet = entityCSet;
    }

    public Set<EntityD> getEntityDSet() {
        return entityDSet;
    }

    public void setEntityDSet(Set<EntityD> entityDSet) {
        this.entityDSet = entityDSet;
    }

    public List<EntityE> getEntityEList() {
        return entityEList;
    }

    public void setEntityEList(List<EntityE> entityEList) {
        this.entityEList = entityEList;
    }

    public SortedSet<EntityE> getEntityESortedSet() {
        return entityESortedSet;
    }

    public void setEntityESortedSet(SortedSet<EntityE> entityESortedSet) {
        this.entityESortedSet = entityESortedSet;
    }

    public void setVerified(Boolean bool) {
        this.verified = bool;
    }

    public Boolean isVerified() {
        return this.verified;
    }

    public void setConfirmed(boolean bool) {
        this.verified = bool;
    }

    public boolean isConfirmed() {
        return this.verified;
    }
}