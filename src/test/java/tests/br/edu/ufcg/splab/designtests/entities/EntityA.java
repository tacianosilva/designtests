package tests.br.edu.ufcg.splab.designtests.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityB> EntityBSet = new HashSet<EntityB>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityC> EntityCSet = new HashSet<EntityC>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<EntityD> EntityDSet = new HashSet<EntityD>();

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
        return EntityBSet;
    }

    public void setEntityBSet(Set<EntityB> entityBSet) {
        EntityBSet = entityBSet;
    }

    public Set<EntityC> getEntityCSet() {
        return EntityCSet;
    }

    public void setEntityCSet(Set<EntityC> entityCSet) {
        EntityCSet = entityCSet;
    }

    public Set<EntityD> getEntityDSet() {
        return EntityDSet;
    }

    public void setEntityDSet(Set<EntityD> entityDSet) {
        EntityDSet = entityDSet;
    }
}
