package tests.br.edu.ufcg.splab.designtests.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@TableGenerator(name="colSeq", allocationSize=1)
public class EntityE implements Serializable, Comparable<EntityE> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="colSeq")
    private Integer id;

    @Column
    private String name;

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

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;

        return new EqualsBuilder().append(getId(), ((EntityE) other).getId()).isEquals();
    }


    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("id", getId());
        builder.append("name", getName());
        return builder.toString();
    }


    @Override
    public int compareTo(final EntityE o) {
        if (o == null) {
            return -1;
        }
        if (this.getId() == null ^ o.getId() == null) {
            return (this.getId() == null) ? -1 : 1;
        }

        if (this.getId() == null && o.getId() == null) {
            return 0;
        }

        return this.getId().compareTo(o.getId());
    }
}
