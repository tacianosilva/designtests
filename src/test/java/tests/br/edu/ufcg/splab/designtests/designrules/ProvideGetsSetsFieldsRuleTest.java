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

import br.edu.ufcg.splab.designtests.designrules.ProvideGetsSetsFieldsRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;

public class ProvideGetsSetsFieldsRuleTest {

    DesignWizard dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    PersistenceRuleUtil util = new PersistenceRuleUtil();
    ProvideGetsSetsFieldsRule rule;

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
        rule = new ProvideGetsSetsFieldsRule(dw);
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

        // Implementa getters and setters
        rule.setClassNode(entityA);
        assertTrue("1", rule.checkRule());

        // Implementa getters and setters
        rule.setClassNode(entityB);
        assertTrue("2", rule.checkRule());

        // Não implementa setters
        rule.setClassNode(entityC);
        assertFalse("3", rule.checkRule());

        // Não implementa setters
        rule.setClassNode(entityD);
        assertFalse("4", rule.checkRule());

        // Implementa getters and setters
        rule.setClassNode(subEntityA);
        assertTrue("5", rule.checkRule());

        // Não implementa getters and setters
        rule.setClassNode(subEntityB);
        assertFalse("6", rule.checkRule());
    }

    @Test
    public void testGetReport() {
        // Implementa getters and setters
        rule.setClassNode(entityA);
        rule.checkRule();
        assertEquals("1", "", rule.getReport());

        // Não implementa getters and setters
        rule.setClassNode(entityB);
        rule.checkRule();
        assertEquals("2", "", rule.getReport());

        // Não implementa setters
        rule.setClassNode(entityC);
        rule.checkRule();
        assertNotSame("3", "", rule.getReport());

        // Não implementa setters
        rule.setClassNode(entityD);
        rule.checkRule();
        assertNotSame("4", "", rule.getReport());

        // Implementa getters and setters
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertEquals("5", "", rule.getReport());

        // Não implementa getters and setters
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertNotSame("6", "", rule.getReport());
    }
}
