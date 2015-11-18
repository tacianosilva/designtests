package tests.br.edu.ufcg.splab.designtests.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SubEntityA extends SuperEntityA {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String fieldSubEntity;

    protected String getFieldSubEntity() {
        return fieldSubEntity;
    }

    protected void setFieldSubEntity(String param) {
        this.fieldSubEntity = param;
    }

}
