package tests.br.edu.ufcg.splab.designtests.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityD implements Serializable {

    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private List<String> collectionList;

    @Column
    private ArrayList<String> collectionArrayList;

    @Column
    private LinkedList<String> collectionLinkedList;

    public EntityD(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
