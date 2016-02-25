package tests.br.edu.ufcg.splab.designtests.examples.testng;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.edu.ufcg.splab.designtests.designrules.AbstractDesignRule;
import br.edu.ufcg.splab.designtests.designrules.HashCodeAndEqualsRule;
import br.edu.ufcg.splab.designtests.designrules.ImplementsSerializableRule;
import br.edu.ufcg.splab.designtests.designrules.NoArgumentConstructorRule;
import br.edu.ufcg.splab.designtests.designrules.NoFinalClassRule;
import br.edu.ufcg.splab.designtests.designrules.ProvideGetsSetsFieldsRule;
import br.edu.ufcg.splab.designtests.designrules.ProvideIdentifierPropertyRule;
import br.edu.ufcg.splab.designtests.designrules.UseInterfaceSetOrListRule;
import br.edu.ufcg.splab.designtests.designrules.UseListCollectionRule;
import br.edu.ufcg.splab.designtests.designrules.UseSetCollectionRule;
import br.edu.ufcg.splab.designtests.util.PersistenceRuleUtil;

/**
 * Test class with the verification of design rules recommended by the hibernate for persistent classes.
 * This Unit Test Example use TestNG Framework.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
@Test
public class HibernateDesignTests {

    private DesignWizard dw;
    private Set<ClassNode> entities;
    private AbstractDesignRule rule;

    private PersistenceRuleUtil util = new PersistenceRuleUtil();

    private SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws Exception {
        // Design for all classes in the project.
        // Add here binary code or jar file of the project.
        dw = new DesignWizard("target/test-classes/");
        // Persistence classes of the model package of the project.
        entities = util.getClassesAnnotated(dw, "javax.persistence.Entity");
    }

    @BeforeMethod
    public void startTest() {
         softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() throws Exception {
        dw = null;
        entities = null;
    }

    /**
     * The test verifies all entities if they follows the Rule:
     * Override both equals(java.lang.Object) and hashCode().
     * @see HashCodeAndEqualsRule
     */
    public void testHashCodeAndEqualsRuleAll() {
        rule = new HashCodeAndEqualsRule(dw);
        rule.setClassNodes(entities);
        softAssert.assertTrue(rule.checkRule(), "\ncheckRule Failed: ");
        softAssert.assertEquals(rule.getReport(), "", "\nreport: ");

        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * Override both equals(java.lang.Object) and hashCode().
     * @see HashCodeAndEqualsRule
     */
    public void testHashCodeAndEqualsRule() {
        rule = new HashCodeAndEqualsRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * The classes can't to be final in classes of the Model Package.
     * The hibernate/JPA can't to use proxies (lazy loading) with final classes.
     * @see NoFinalClassRule
     */
    public void testNoFinalClassRule() {
        rule = new NoFinalClassRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * A default constructor must be implemented in classes of the Model Package.
     * All persistent classes must have a default constructor (which can be non-public)
     * so that Hibernate can instantiate them using java.lang.reflect.Constructor.newInstance().
     * @see NoArgumentConstructorRule
     */
    public void testNoArgumentConstructorRule() {
        rule = new NoArgumentConstructorRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * The Serializable interface should be implementated for all classes in the Model Package.
     * @see ImplementsSerializableRule
     */
    public void testImplementsSerializableRule() {
        rule = new ImplementsSerializableRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * Defines getters and setters in classes of the Model Package.
     * @see ProvideGetsSetsFieldsRule
     */
    public void testProvideGetsSetsFieldsRule() {
        rule = new ProvideGetsSetsFieldsRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * Provide identifier Properties in classes of the Model Package.
     * @see ProvideIdentifierPropertyRule
     */
    public void testProvideIdentifierPropertyRule() {
        rule = new ProvideIdentifierPropertyRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * Declaration of Collection of the type Set or List in classes of the Model Package.
     * @see UseInterfaceSetOrListRule
     */
    public void testUseInterfaceSetOrListRule() {
        rule = new UseInterfaceSetOrListRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * Declaration of Collection of the type List in classes of the Model Package.
     * @see UseListCollectionRule
     */
    public void testUseListCollectionRule() {
        rule = new UseListCollectionRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }

    /**
     * The test verifies each persistence entity if they follows the Rule:
     * Declaration of Collection of the type Set in classes of the Model Package.
     * @see UseSetCollectionRule
     */
    public void testUseSetCollectionRule() {
        rule = new UseSetCollectionRule(dw);
        for (ClassNode entity : entities) {
            rule.setClassNode(entity);
            softAssert.assertTrue(rule.checkRule(), "\nEntity <" + entity.getShortName() + "> Failed: ");
            softAssert.assertEquals(rule.getReport(), "", "\nreport: ");
        }
        softAssert.assertAll();
    }
}