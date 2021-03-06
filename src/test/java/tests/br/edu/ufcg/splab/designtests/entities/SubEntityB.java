package tests.br.edu.ufcg.splab.designtests.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public final class SubEntityB extends SuperEntityB {

    public SubEntityB(Integer id) {
        super(id);
    }

    @Column
    private String fieldSubEntity;

    @Column
    private String field;

    protected String getMethodSuperEntity() {
        return fieldSubEntity;
    }

    protected void setMethodSuperEntity(String param) {
        this.fieldSubEntity = param;
    }

}
