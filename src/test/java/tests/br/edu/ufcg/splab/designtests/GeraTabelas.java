package tests.br.edu.ufcg.splab.designtests;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;

public class GeraTabelas {

    static EntityManagerFactory factory;
    static EntityManager manager;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory("designtestsPU");
        manager = factory.createEntityManager();

        EntityA a = new EntityA();
        a.setName("Estudar JPA");
        Set<String> phones = new TreeSet<String>();
        phones.add("fone1");
        phones.add("fone2");
        a.setCollectionSet(phones);
        HashSet<String> emails = new HashSet<String>();
        emails.add("email1");
        emails.add("email2");
        a.setCollectionHashSet(emails);

        manager.getTransaction().begin();
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("ID da EntityA: " + a.getId());

        EntityB b = createB();
        EntityC c = createC();
        EntityD d = createD();

        manager.getTransaction().begin();
        manager.persist(b);
        manager.persist(c);
        manager.persist(d);
        manager.getTransaction().commit();

        System.out.println("ID da EntityB: " + b);
        System.out.println("ID da EntityC: " + c.getId());
        System.out.println("ID da EntityD: " + d.getId());

        manager.close();
        factory.close();
      }

    private static EntityB createB() {
        EntityB b = new EntityB();
        return b;
    }

    private static EntityC createC() {
        EntityC c = new EntityC();
        return c;
    }

    private static EntityD createD() {
        EntityD d = new EntityD("Entidade D");
        return d;
    }
}
