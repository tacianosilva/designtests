package tests.br.edu.ufcg.splab.designtests.designrules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.splab.designtests.designrules.UseListCollectionRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;

public class UseListCollectionRuleTest {

    DesignWizard dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    PersistenceRuleUtil util = new PersistenceRuleUtil();

    UseListCollectionRule rule;

    ClassNode entityA;
    ClassNode entityB;
    ClassNode entityC;
    ClassNode entityD;
    ClassNode subEntityA;
    ClassNode subEntityB;


    @Before
    public void setUp() throws Exception {
        dw = new DesignWizard("target/test-classes/tests/br/edu/ufcg/splab/designtests/entities/");
        classes = dw.getAllClasses();
        entities = util.getClassesAnnotated(dw, "javax.persistence.Entity");
        entityA = dw.getClass(EntityA.class.getName());
        entityB = dw.getClass(EntityB.class.getName());
        entityC = dw.getClass(EntityC.class.getName());
        entityD = dw.getClass(EntityD.class.getName());
        subEntityA = dw.getClass(SubEntityA.class.getName());
        subEntityB = dw.getClass(SubEntityB.class.getName());
        rule = new UseListCollectionRule(dw);
    }

    @After
    public void tearDown() throws Exception {
        dw = null;
        rule = null;
        classes = null;
        entities = null;
        entityA = null;
        entityB = null;
        entityC = null;
        entityD = null;
        subEntityA = null;
        subEntityB = null;
    }

    @Test
    public void testCheckRule() {
        // Usa coleções do tipo Set e HashSet.
        rule.setClassNode(entityA);
        assertFalse("1", rule.checkRule());

        // Não usa coleções do tipo Set - Utiliza List, ArrayList e LinkedList.
        rule.setClassNode(entityB);
        assertTrue("2", rule.checkRule());

        // Usa coleções do tipo Set e HashSet.
        rule.setClassNode(entityC);
        assertFalse("3", rule.checkRule());

        // Usa coleções do tipo Set e List.
        rule.setClassNode(entityD);
        assertFalse("4", rule.checkRule());

        // Não usa coleções
        rule.setClassNode(subEntityA);
        assertTrue("5", rule.checkRule());

        // Não usa coleções
        rule.setClassNode(subEntityB);
        assertTrue("6", rule.checkRule());
    }

    @Test
    public void testGetReport() {
        // Usa coleções do tipo Set e HashSet.
        rule.setClassNode(entityA);
        rule.checkRule();
        assertNotSame("1", "", rule.getReport());

        // Não usa coleções do tipo Set - Utiliza List, ArrayList e LinkedList.
        rule.setClassNode(entityB);
        rule.checkRule();
        assertEquals("2", "", rule.getReport());

        // Usa coleções do tipo Set e HashSet.
        rule.setClassNode(entityC);
        rule.checkRule();
        assertNotSame("3", "", rule.getReport());

        // Não usa coleções do tipo Set - Utiliza List, ArrayList e LinkedList.
        rule.setClassNode(entityD);
        rule.checkRule();
        assertNotSame("4", "", rule.getReport());

        // Não usa coleções
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertEquals("5", "", rule.getReport());

        // Não usa coleções
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertEquals("6", "", rule.getReport());
    }
}
