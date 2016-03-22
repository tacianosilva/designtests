package tests.br.edu.ufcg.splab.designtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
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
import tests.br.edu.ufcg.splab.designtests.entities.EntityE;

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
        manager.close();
        factory.close();
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

        // EntityB implementa hashCode/Equals baseado no nome
        EntityB b1 = createEntityB("B1");
        EntityB b2 = createEntityB("B1");

        // São iguais pelo nome
        assertTrue(b1.equals(b2));

        // adds a new EntityB with id = null and name = B1
        entity.getEntityBSet().add(b1);
        // has id=null and name = B1, so is added other object.
        entity.getEntityBSet().add(b2);

        Set<EntityB> conjuntoB = entity.getEntityBSet();
        assertEquals("0", 1, conjuntoB.size());

        manager.getTransaction().commit();

        conjuntoB = entity.getEntityBSet();

        assertNotNull("1", conjuntoB);
        assertEquals("2", 1, conjuntoB.size());
        assertTrue("3", conjuntoB.contains(b1));
        assertTrue("4", conjuntoB.contains(b2));

        manager.getTransaction().begin();
        manager.remove(a);
        manager.getTransaction().commit();
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

        manager.getTransaction().begin();
        manager.remove(a);
        manager.getTransaction().commit();
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

        manager.getTransaction().begin();
        manager.remove(a);
        manager.getTransaction().commit();
    }

    @Test
    public void testImplementHashCodeWithBuilder() {
        EntityD d = createEntityD("D with Builder");
        a = createEntityA();

        // Inserir entidade EntityA
        manager.getTransaction().begin();
        manager.persist(d);
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("EntityD d.id: " + d.getId());

        // Buscar Entidade a
        manager.getTransaction().begin();

        EntityD entity = manager.find(EntityD.class, d.getId());

        // EntityE implemententa hashCode/Equals usando HashCodeBuilder
        EntityE e1 = createEntityE("E1");
        EntityE e2 = createEntityE("E2");

        EntityB b1 = createEntityB("B1");
        EntityB b2 = createEntityB("B2");

        // TODO Deveria ser diferente (retornar False)
        assertTrue("0", e1.equals(e2));

        // adds a new EntityB with id = null or id = 0
        entity.getEntityESet().add(e1);
        // has id=null, too so overwrites last added object.
        entity.getEntityESet().add(e2);

        entity.getCollectionB().add(b1);
        entity.getCollectionB().add(b2);

        System.out.println("e1.id:>>>>>>> " + e1.getId());
        System.out.println("e1.hash:>>>>>>> " + e1.hashCode());
        System.out.println("e2.id:>>>>>>> " + e2.getId());
        System.out.println("e2.hash:>>>>>>> " + e2.hashCode());

        manager.getTransaction().commit();

      //Para verificar o tipo de Persist Collection usada pelo Hibernate
        Object tipo = entity.getEntityESet().getClass();
        System.out.println("Tipo Set ==> " + tipo);

        tipo = entity.getCollectionB().getClass();
        System.out.println("Tipo Collection ==> " + tipo);

        System.out.println("e1.id:>>>>>>> " + e1.getId());
        System.out.println("e1.hash:>>>>>>> " + e1.hashCode());
        System.out.println("e2.id:>>>>>>> " + e2.getId());
        System.out.println("e2.hash:>>>>>>> " + e2.hashCode());

        Set<EntityE> conjuntoE = entity.getEntityESet();

        assertNotNull("1", conjuntoE);
        assertEquals("2", 1, conjuntoE.size());
        assertFalse("3", conjuntoE.contains(e1));
        assertFalse("4", conjuntoE.contains(e2));

        manager.getTransaction().begin();
        manager.remove(a);
        manager.remove(d);
        manager.getTransaction().commit();
    }

    @Test
    public void testCollectionList() {
        a = createEntityA();

        // Inserir entidade EntityA
        manager.getTransaction().begin();
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("EntityA a.id: " + a.getId());

        // Buscar Entidade a
        manager.getTransaction().begin();

        EntityA entity = manager.find(EntityA.class, a.getId());

        EntityB b1 = createEntityB("Set-B1");
        EntityB b2 = createEntityB("Set-B2");
        EntityB b3 = createEntityB("Set-B3");
        EntityB b4 = createEntityB("Set-B4");

        // EntityE implemententa hashCode/Equals usando HashCodeBuilder
        EntityE e1 = createEntityE("List-E1");
        EntityE e2 = createEntityE("List-E2");
        EntityE e3 = createEntityE("List-E0");
        EntityE e4 = createEntityE("List-E0");

        // TODO Deveria ser diferente (retornar False) mas como id = null, as entidades são consideradas iguais.
        assertTrue("Equals-0", e1.equals(e2));
        // São iguais, id = null e mesmo nome.
        assertTrue("Equals-1", e3.equals(e4));

        entity.getEntityBSet().add(b1);
        entity.getEntityBSet().add(b2);
        entity.getEntityBSet().add(b3);
        entity.getEntityBSet().add(b4);

        // adds a new EntityB with id = null or id = 0
        entity.getEntityEList().add(e1);
        // has id=null, too so overwrites last added object.
        entity.getEntityEList().add(e2);
        entity.getEntityEList().add(e3);
        entity.getEntityEList().add(e4);

        System.out.println("e1.id:>>>>>>> " + e1.getId());
        System.out.println("e1.hash:>>>>>>> " + e1.hashCode());
        System.out.println("e2.id:>>>>>>> " + e2.getId());
        System.out.println("e2.hash:>>>>>>> " + e2.hashCode());
        System.out.println("e3.id:>>>>>>> " + e3.getId());
        System.out.println("e3.hash:>>>>>>> " + e3.hashCode());
        System.out.println("e4.id:>>>>>>> " + e4.getId());
        System.out.println("e4.hash:>>>>>>> " + e4.hashCode());

        manager.getTransaction().commit();

        System.out.println("e1.id:>>>>>>> " + e1.getId());
        System.out.println("e1.hash:>>>>>>> " + e1.hashCode());
        System.out.println("e2.id:>>>>>>> " + e2.getId());
        System.out.println("e2.hash:>>>>>>> " + e2.hashCode());
        System.out.println("e3.id:>>>>>>> " + e3.getId());
        System.out.println("e3.hash:>>>>>>> " + e3.hashCode());
        System.out.println("e4.id:>>>>>>> " + e4.getId());
        System.out.println("e4.hash:>>>>>>> " + e4.hashCode());

        List<EntityE> conjuntoE = entity.getEntityEList();

        assertNotNull("test-1", conjuntoE);
        assertEquals("test-2", 4, conjuntoE.size());
        assertTrue("test-3", conjuntoE.contains(e1));
        assertTrue("test-4", conjuntoE.contains(e2));
        assertTrue("test-5", conjuntoE.contains(e3));
        assertTrue("test-6", conjuntoE.contains(e4));

        // Exemplo de remoção de uma coleção do Tipo List
        manager.getTransaction().begin();
        a.getEntityEList().remove(e2);
        manager.getTransaction().commit();

        // Exemplo de remoção de uma coleção do Tipo Set
        manager.getTransaction().begin();
        a.getEntityBSet().remove(b2);
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        manager.remove(a);
        manager.getTransaction().commit();
    }

    @Test
    public void testCollectionSortedSet() {
        a = createEntityA();

        // Inserir entidade EntityA
        manager.getTransaction().begin();
        manager.persist(a);
        manager.getTransaction().commit();

        System.out.println("EntityA a.id: " + a.getId());

        // EntityE implemententa hashCode/Equals usando HashCodeBuilder
        EntityE e1 = createEntityE("SortedSet-E1");
        EntityE e2 = createEntityE("SortedSet-E2");
        EntityE e3 = createEntityE("SortedSet-E0");
        EntityE e4 = createEntityE("SortedSet-E0");
        EntityE e5 = createEntityE("SortedSet-E5");
        EntityE e6 = createEntityE("SortedSet-E6");

        manager.getTransaction().begin();
        manager.persist(e5);
        manager.persist(e6);
        manager.getTransaction().commit();

        // Buscar Entidade a
        manager.getTransaction().begin();

        EntityA entity = manager.find(EntityA.class, a.getId());


        // TODO Deveria ser diferente (retornar False) mas como id = null, as entidades são consideradas iguais.
        assertTrue("Equals-0", e1.equals(e2));
        // São iguais, id = null e mesmo nome.
        assertTrue("Equals-1", e3.equals(e4));

        // adds a new EntityB with id = null or id = 0
        entity.getEntityESortedSet().add(e1);
        // has id=null, too so overwrites last added object.
        entity.getEntityESortedSet().add(e2);
        entity.getEntityESortedSet().add(e3);
        // Not add e4 because the Comparator is the name
        entity.getEntityESortedSet().add(e4);
        entity.getEntityESortedSet().add(e5);
        entity.getEntityESortedSet().add(e6);

        System.out.println("e1.id:>>>>>>> " + e1.getId());
        System.out.println("e1.hash:>>>>>>> " + e1.hashCode());
        System.out.println("e2.id:>>>>>>> " + e2.getId());
        System.out.println("e2.hash:>>>>>>> " + e2.hashCode());
        System.out.println("e3.id:>>>>>>> " + e3.getId());
        System.out.println("e3.hash:>>>>>>> " + e3.hashCode());
        System.out.println("e4.id:>>>>>>> " + e4.getId());
        System.out.println("e4.hash:>>>>>>> " + e4.hashCode());

        manager.getTransaction().commit();

        System.out.println("e1.id:>>>>>>> " + e1.getId());
        System.out.println("e1.hash:>>>>>>> " + e1.hashCode());
        System.out.println("e2.id:>>>>>>> " + e2.getId());
        System.out.println("e2.hash:>>>>>>> " + e2.hashCode());
        System.out.println("e3.id:>>>>>>> " + e3.getId());
        System.out.println("e3.hash:>>>>>>> " + e3.hashCode());
        System.out.println("e4.id:>>>>>>> " + e4.getId());
        System.out.println("e4.hash:>>>>>>> " + e4.hashCode());


        manager.getTransaction().begin();
        entity = manager.find(EntityA.class, a.getId());
        Set<EntityE> conjuntoE = entity.getEntityESortedSet();

        assertNotNull("test-1", conjuntoE);
        assertEquals("test-2", 3, conjuntoE.size());
        // Não está encontrando por que insere null e ele muda de id quando é colocado.
        assertFalse("test-3", conjuntoE.contains(e1));
        assertFalse("test-4", conjuntoE.contains(e2));
        assertFalse("test-5", conjuntoE.contains(e3));
        //O Contains retorna False porque o comparator é por id;
        assertFalse("test-6", conjuntoE.contains(e4));
        assertTrue("test-7", conjuntoE.contains(e5));
        assertTrue("test-8", conjuntoE.contains(e6));

        e5 = manager.find(EntityE.class, e5.getId());
        a.getEntityESortedSet().remove(e5);
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        manager.remove(a);
        manager.getTransaction().commit();
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

    public EntityE createEntityE(String name) {
        EntityE e = new EntityE();
        e.setName(name);
        return e;
    }
}
