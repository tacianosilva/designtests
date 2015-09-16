package tests.br.edu.ufcg.splab.designtests.designrules;

import static org.junit.Assert.*;

import java.util.Set;

import org.designwizard.design.ClassNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsNotUseIdentifierPropertyRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import tests.br.edu.ufcg.splab.designtests.entities.EntityA;
import tests.br.edu.ufcg.splab.designtests.entities.EntityB;
import tests.br.edu.ufcg.splab.designtests.entities.EntityC;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityA;
import tests.br.edu.ufcg.splab.designtests.entities.SubEntityB;

public class HashCodeAndEqualsRuleTest {

    DesignWizardDecorator dw;
    Set<ClassNode> entities;
    Set<ClassNode> classes;
    HashCodeAndEqualsRule rule;

    ClassNode entityA;
    ClassNode entityB;
    ClassNode entityC;
    ClassNode subEntityA;
    ClassNode subEntityB;

    @Before
    public void setUp() throws Exception {
        dw = new DesignWizardDecorator("target/test-classes/tests/br/edu/ufcg/splab/designtests/entities/", "designtests");
        classes = dw.getClassesFromCode();
        entities = dw.getClassesAnnotated("javax.persistence.Entity");
        entityA = dw.getClass(EntityA.class.getName());
        entityB = dw.getClass(EntityB.class.getName());
        entityC = dw.getClass(EntityC.class.getName());
        subEntityA = dw.getClass(SubEntityA.class.getName());
        subEntityB = dw.getClass(SubEntityB.class.getName());
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
        subEntityA = null;
        subEntityB = null;
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

        // Não implementa hashCode e Equals e a superClasse herda os métodos de Object.
        rule.setClassNode(subEntityA);
        assertFalse("4", rule.checkRule());

        // Herda o HashCode e Equals que utilizam o Identificador da SuperClasse.
        rule.setClassNode(subEntityB);
        assertFalse("5", rule.checkRule());
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

        // Não implementa hashCode e Equals e a superClasse herda os métodos de Object.
        rule.setClassNode(subEntityA);
        rule.checkRule();
        assertNotSame("4", "", rule.getReport());

        // Herda o HashCode e Equals que utilizam o Identificador da SuperClasse.
        rule.setClassNode(subEntityB);
        rule.checkRule();
        assertNotSame("5", "", rule.getReport());
    }

}
