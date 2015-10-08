package tests.br.edu.ufcg.splab.designtests.designrules;

import static org.junit.Assert.*;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.splab.designtests.designrules.NoFinalClassRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;

public class NoFinalClassRuleTest {

    DesignWizard dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    PersistenceRuleUtil util = new PersistenceRuleUtil();

    NoFinalClassRule rule;

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
        rule = new NoFinalClassRule(dw);
    }

    @After
    public void tearDown() throws Exception {
        dw = null;
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
        // Não é class final
        rule.setClassNode(entityA);
        assertTrue("1", rule.checkRule());

        // Não é class final
        rule.setClassNode(entityB);
        assertTrue("2", rule.checkRule());

        // É class final
        rule.setClassNode(entityC);
        assertFalse("3", rule.checkRule());

        // Não é classe final
        rule.setClassNode(entityD);
        assertTrue("4", rule.checkRule());

        // Não é classe final
        rule.setClassNode(subEntityA);
        assertTrue("5", rule.checkRule());

        // É classe final
        rule.setClassNode(subEntityB);
        assertFalse("6", rule.checkRule());
    }

    @Test
    public void testGetReport() {
        // Não é classe final
        rule.setClassNode(entityA);
        rule.checkRule();
        assertEquals("1", "", rule.getReport());

        // Não é classe final
        rule.setClassNode(entityB);
        rule.checkRule();
        assertEquals("2", "", rule.getReport());

        // É classe final
        rule.setClassNode(entityC);
        rule.checkRule();
        assertNotSame("3", "", rule.getReport());

        // Não é classe final
        rule.setClassNode(entityD);
        rule.checkRule();
        assertEquals("4", "", rule.getReport());

        // Não é classe final
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertEquals("5", "", rule.getReport());

        // É classe final
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertNotSame("6", "", rule.getReport());
    }
}
