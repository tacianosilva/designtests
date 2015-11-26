package tests.br.edu.ufcg.splab.designtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;

public class PersistenceCollectionTest {

    EntityManagerFactory factory;
    EntityManager manager;
    EntityA a;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("designtestsPU");
        manager = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        manager.getTransaction().begin();
        manager.remove(a);
        manager.getTransaction().commit();
        manager.close();
    }

    @Test
    public void testImplementHashCodeEqualsWithoutId() {
        a = createEntityA();

        // Inserir entidade EntityA
        manager.getTransaction().begin();
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("EntityA a.id: " + a.getId());

        // Buscar Entidade a
        manager.getTransaction().begin();

        EntityA entity = manager.find(EntityA.class, a.getId());

        // EntityB não implementa hashCode/Equals
        EntityB b1 = createEntityB("B1");
        EntityB b2 = createEntityB("B1");

        assertTrue(b1.equals(b2));

        // adds a new EntityB with id = null and name = B1
        entity.getEntityBSet().add(b1);
        // has id=null and name = B1, so is added other object.
        entity.getEntityBSet().add(b2);

        manager.getTransaction().commit();

        Set<EntityB> conjuntoB = entity.getEntityBSet();

        assertNotNull("0", conjuntoB);
        assertEquals("1", 1, conjuntoB.size());
        assertTrue("2", conjuntoB.contains(b1));
        assertTrue("3", conjuntoB.contains(b2));
    }

    @Test
    public void testImplementHashCodeEqualsWithId() {
        a = createEntityA();

        // Inserir entidade EntityA
        manager.getTransaction().begin();
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("EntityA a.id: " + a.getId());

        // Buscar Entidade a
        manager.getTransaction().begin();

        EntityA entity = manager.find(EntityA.class, a.getId());

        // EntityC implemententa hashCode/Equals usando @Id
        EntityC c1 = createEntityC("C1");
        EntityC c2 = createEntityC("C2");

        manager.persist(c1);
        manager.persist(c2);

        // TODO Deveria ser diferente (retornar False)
        assertTrue("0", !c1.equals(c2));

        // adds a new EntityB with id = null or id = 0
        entity.getEntityCSet().add(c1);
        // has id=null, too so overwrites last added object.
        entity.getEntityCSet().add(c2);

        System.out.println("c1.id:>>>>>>> " + c1.getId());
        System.out.println("c2.id:>>>>>>> " + c2.getId());

        manager.getTransaction().commit();

        Set<EntityC> conjuntoC = entity.getEntityCSet();

        assertNotNull("1", conjuntoC);
        assertEquals("2", 2, conjuntoC.size());
        assertTrue("3", conjuntoC.contains(c1));
        assertTrue("4", conjuntoC.contains(c2));
    }

    @Test
    public void testNotImplementHashCodeEquals() {
        a = createEntityA();

        // Inserir entidade EntityA
        manager.getTransaction().begin();
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("EntityA a.id: " + a.getId());

        // Buscar Entidade a
        manager.getTransaction().begin();

        EntityA entity = manager.find(EntityA.class, a.getId());

        // EntityD não implemententa hashCode/Equals
        EntityD d1 = createEntityD("D1");
        EntityD d2 = createEntityD("D1");

        // TODO Deveria ser iguais (retornar true) em relação ao banco
        assertTrue("0", !d1.equals(d2));

        // adds a new EntityD with id = null or id = 0
        entity.getEntityDSet().add(d1);
        // has id=null, too so overwrites last added object.
        entity.getEntityDSet().add(d2);

        System.out.println("d1.id:>>>>>>> " + d1.getId());
        System.out.println("d2.id:>>>>>>> " + d2.getId());

        manager.getTransaction().commit();

        Set<EntityD> conjuntoD = entity.getEntityDSet();

        assertNotNull("1", conjuntoD);
        assertEquals("2", 2, conjuntoD.size());
        assertTrue("3", conjuntoD.contains(d1));
        assertTrue("4", conjuntoD.contains(d2));

    }

    public EntityA createEntityA() {
        EntityA a = new EntityA();
        a.setName("A1");
        return a;
    }

    public EntityB createEntityB(String name) {
        EntityB b = new EntityB(name);
        return b;
    }

    public EntityC createEntityC(String name) {
        EntityC c = new EntityC();
        c.setName(name);
        return c;
    }

    public EntityD createEntityD(String name) {
        EntityD d = new EntityD(name);
        return d;
    }
}
