package tests.br.edu.ufcg.splab.designtests.entities;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperEntityB {

    @Id
    private Integer id;

    public SuperEntityB(Integer id) {
        this.id = id;
    }

    protected Integer getId() {
        return id;
    }

    protected void setId(Integer param) {
        this.id = param;
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
        SuperEntityB other = (SuperEntityB) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
