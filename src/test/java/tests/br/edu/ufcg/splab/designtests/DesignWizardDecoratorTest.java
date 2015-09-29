package tests.br.edu.ufcg.splab.designtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.PackageNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;

/**
 * Test class for {@link DesignWizardDecorator}.
 * @author Taciano de Morais Silva - tacianosilva@gmail.com
 */
public class DesignWizardDecoratorTest {

    /**
     * DesignWizard instance.
     */
    DesignWizard dw;
    /**
     * Decorator instance.
     */
    DesignWizardDecorator dwd;
    /**
     * Project name on the tests.
     */
    String projectName = "sigest";
    /**
     * Project jar file.
     */
    String arquivoJar = "jars/sigest.jar";

    @Before
    public void setUp() throws Exception {
        dwd = new DesignWizardDecorator(arquivoJar, projectName);
    }

    @After
    public void tearDown() throws Exception {
        dw = null;
        dwd = null;
    }

    @Test
    public final void testNewDesignWizardDecorator() {
        dw = dwd.getDesignWizard();
        assertNotNull("1", dw);

        Set<PackageNode> packages = dw.getAllPackages();
        assertEquals("2", 10, packages.size());

        for (PackageNode packageNode : packages) {
            System.out.println("package" + packageNode);
        }

        assertNotNull("3", dwd);
    }
}
