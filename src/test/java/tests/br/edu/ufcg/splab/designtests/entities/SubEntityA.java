package tests.br.edu.ufcg.splab.designtests.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SubEntityA extends SuperEntityA {

    @Id
    private Integer id;

    @Column
    private String fieldSubEntity;

    protected Integer getId() {
        return id;
    }

    protected void setId(Integer param) {
        this.id = param;
    }

    protected String getMethodSuperEntity() {
        return fieldSubEntity;
    }

    protected void setMethodSuperEntity(String param) {
        this.fieldSubEntity = param;
    }

}
