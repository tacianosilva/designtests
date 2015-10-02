package tests.br.edu.ufcg.splab.designtests.designrules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    HashCodeAndEqualsNotUseIdentifierPropertyRuleTest.class,
    HashCodeAndEqualsRuleTest.class,
    ImplementsSerializableRuleTest.class,
    ProvideIdentifierPropertyRuleTest.class,
    ProvideGetsSetsFieldsRuleTest.class })
public class AllTests {

}
