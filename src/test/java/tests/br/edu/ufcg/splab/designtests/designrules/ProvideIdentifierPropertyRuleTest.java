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

import br.edu.ufcg.splab.designtests.designrules.ProvideIdentifierPropertyRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;

public class ProvideIdentifierPropertyRuleTest {

    DesignWizard dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    PersistenceRuleUtil util = new PersistenceRuleUtil();
    ProvideIdentifierPropertyRule rule;

    ClassNode entityA;
    ClassNode entityB;
    ClassNode entityC;
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
        subEntityA = dw.getClass(SubEntityA.class.getName());
        subEntityB = dw.getClass(SubEntityB.class.getName());
        rule = new ProvideIdentifierPropertyRule(dw);
    }

    @After
    public void tearDown() throws Exception {
        dw = null;
        classes = null;
        entities = null;
        entityA = null;
        entityB = null;
        entityC = null;
        subEntityA = null;
        subEntityB = null;
    }

    @Test
    public void testCheckRule() {
        // Contém o atributo identificador
        rule.setClassNode(entityA);
        assertTrue("1", rule.checkRule());

        // Contém o atributo identificador
        rule.setClassNode(entityB);
        assertTrue("2", rule.checkRule());

        // Contém o atributo identificador
        rule.setClassNode(entityC);
        assertTrue("3", rule.checkRule());

        // Contém o atributo identificador
        rule.setClassNode(subEntityA);
        assertTrue("4", rule.checkRule());

        // Não Contém o atributo identificador na subclasse mas contém na superclasse
        rule.setClassNode(subEntityB);
        assertTrue("5", rule.checkRule());
    }

    @Test
    public void testGetReport() {
        // Contém o atributo identificador
        rule.setClassNode(entityA);
        rule.checkRule();
        assertEquals("1", "", rule.getReport());

        // Contém o atributo identificador
        rule.setClassNode(entityB);
        rule.checkRule();
        assertEquals("2", "", rule.getReport());

        // Contém o atributo identificador
        rule.setClassNode(entityC);
        rule.checkRule();
        assertEquals("3", "", rule.getReport());

        // Contém o atributo identificador
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertEquals("4", "", rule.getReport());

        // Não Contém o atributo identificador na subclasse mas contém na superclasse
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertEquals("5", "", rule.getReport());
    }

}
