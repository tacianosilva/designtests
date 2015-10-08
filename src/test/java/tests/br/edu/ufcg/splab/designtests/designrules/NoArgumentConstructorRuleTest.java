package tests.br.edu.ufcg.splab.designtests.designrules;

import static org.junit.Assert.*;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.splab.designtests.designrules.NoArgumentConstructorRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityC;

public class NoArgumentConstructorRuleTest {

    DesignWizard dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    PersistenceRuleUtil util = new PersistenceRuleUtil();

    NoArgumentConstructorRule rule;

    ClassNode entityA;
    ClassNode entityB;
    ClassNode entityC;
    ClassNode entityD;
    ClassNode subEntityA;
    ClassNode subEntityB;
    ClassNode subEntityC;


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
        subEntityC = dw.getClass(SubEntityC.class.getName());
        rule = new NoArgumentConstructorRule(dw);
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
        subEntityC = null;
    }

    @Test
    public void testCheckRule() {
        // Herda Construtor Padrão do Object
        rule.setClassNode(entityA);
        assertTrue("1", rule.checkRule());

        // Construtor Padrão do Object
        rule.setClassNode(entityB);
        assertTrue("2", rule.checkRule());

        // Construtor Padrão Implementado
        rule.setClassNode(entityC);
        assertTrue("3", rule.checkRule());

        // Construtor com um argumento implementado
        rule.setClassNode(entityD);
        assertFalse("4", rule.checkRule());

        // Herda Construtor Padrão do Object
        rule.setClassNode(subEntityA);
        assertTrue("5", rule.checkRule());

        // Herda e implementa um construtor com um argumento
        rule.setClassNode(subEntityB);
        assertFalse("6", rule.checkRule());

        // Não implementa um construtor mas herda um da super classe
        rule.setClassNode(subEntityC);
        assertTrue("7", rule.checkRule());
    }

    @Test
    public void testGetReport() {
        // Construtor Padrão do Object
        rule.setClassNode(entityA);
        rule.checkRule();
        assertEquals("1", "", rule.getReport());

        // Construtor Padrão do Object
        rule.setClassNode(entityB);
        rule.checkRule();
        assertEquals("2", "", rule.getReport());

        // Construtor Padrão Implementado
        rule.setClassNode(entityC);
        rule.checkRule();
        assertEquals("3", "", rule.getReport());

        // Construtor com um argumento implementado
        rule.setClassNode(entityD);
        rule.checkRule();
        assertNotSame("4", "", rule.getReport());

        // Herda Construtor Padrão do Object
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertEquals("5", "", rule.getReport());

        // Herda e implementa um construtor com um argumento
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertNotSame("6", "", rule.getReport());

        // Não implementa um construtor mas herda um da super classe
        rule.setClassNode(subEntityC);
        rule.checkRule();
        assertEquals("7", "", rule.getReport());
    }

}
