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

import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.EntityD;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityC;

public class HashCodeAndEqualsRuleTest {

    DesignWizard dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    PersistenceRuleUtil util = new PersistenceRuleUtil();
    HashCodeAndEqualsRule rule;

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
        rule = new HashCodeAndEqualsRule(dw);
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
        subEntityC = null;
    }

    @Test
    public void testCheckRule() {
        // Não implementa hashCode e Equals no caso herda os métodos de Object.
        rule.setClassNode(entityA);
        assertFalse("1", rule.checkRule());

        // Sobrescreve os métodos hashCode e Equals que não utilizam o Identificador
        rule.setClassNode(entityB);
        assertTrue("2", rule.checkRule());

        // Sobrescreve os métodos hashCode e Equals que utilizam o Identificador
        rule.setClassNode(entityC);
        assertTrue("3", rule.checkRule());

        // Não implementa hashCode e Equals no caso herda os métodos de Object.
        rule.setClassNode(entityD);
        assertFalse("4", rule.checkRule());

        // Não implementa hashCode e Equals e a superClasse herda os métodos de Object.
        rule.setClassNode(subEntityA);
        assertFalse("5", rule.checkRule());

        // Herda o HashCode e Equals que utilizam o Identificador da SuperClasse.
        rule.setClassNode(subEntityB);
        assertFalse("6", rule.checkRule());

        rule.setClassNode(subEntityC);
        assertFalse("7", rule.checkRule());
    }

    @Test
    public void testGetReport() {
        // Não implementa hashCode e Equals no caso herda os métodos de Object.
        rule.setClassNode(entityA);
        rule.checkRule();
        assertNotSame("1", "", rule.getReport());

        // Sobrescreve os métodos hashCode e Equals que não utilizam o Identificador
        rule.setClassNode(entityB);
        rule.checkRule();
        assertEquals("2", "", rule.getReport());

        // Sobrescreve os métodos hashCode e Equals que utilizam o Identificador
        rule.setClassNode(entityC);
        rule.checkRule();
        assertEquals("3", "", rule.getReport());

        // Não implementa hashCode e Equals no caso herda os métodos de Object.
        rule.setClassNode(entityD);
        rule.checkRule();
        assertNotSame("4", "", rule.getReport());

        // Não implementa hashCode e Equals e a superClasse herda os métodos de Object.
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertNotSame("5", "", rule.getReport());

        // Herda o HashCode e Equals que utilizam o Identificador da SuperClasse.
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertNotSame("6", "", rule.getReport());

        rule.setClassNode(subEntityC);
        rule.checkRule();
        assertNotSame("7", "", rule.getReport());
    }

    @Test
    public void testResults() {
        // Existem classes que não atendem a regra
        rule.setClassNodes(entities);
        assertFalse("1", rule.checkRule());

        Set<ClassNode> falhas = rule.getResultsFalse();
        for (ClassNode classNode : falhas) {
            System.out.println("Falha: " + classNode.getShortName());
        }
        assertEquals("2", 5, falhas.size());

        Set<ClassNode> acertos = rule.getResultsTrue();
        for (ClassNode classNode : acertos) {
            System.out.println("Acerto: " + classNode.getShortName());
        }
        assertEquals("3", 2, acertos.size());
    }
}