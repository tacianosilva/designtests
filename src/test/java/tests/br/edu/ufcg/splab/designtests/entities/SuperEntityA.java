package tests.br.edu.ufcg.splab.designtests.entities;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperEntityA {

    @Id
    private String fieldSuperEntity;

    protected String getMethodSuperEntity() {
        return fieldSuperEntity;
    }

    protected void setMethodSuperEntity(String param) {
        this.fieldSuperEntity = param;
    }

}
