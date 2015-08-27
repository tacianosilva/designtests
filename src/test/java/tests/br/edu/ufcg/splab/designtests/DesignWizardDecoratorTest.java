package tests.br.edu.ufcg.splab.designtests;

import static org.junit.Assert.*;

import java.util.Set;

import org.designwizard.design.PackageNode;
import org.designwizard.api.DesignWizard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.splab.designtests.DesignWizardDecorator;
import br.edu.ufcg.splab.designtests.design.Architecture;
import br.edu.ufcg.splab.designtests.design.ModuleNode;

public class DesignWizardDecoratorTest {

    DesignWizard dw;
    DesignWizardDecorator dwd;
    String projectName = "sigest";
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
        assertEquals("2", 9, packages.size());

        for (PackageNode packageNode : packages) {
            System.out.println("package" + packageNode);
        }

        assertNotNull("3", dwd);
    }

    @Test
    public final void testGetArchtecture() {
        Architecture arch = dwd.getArchitecture();
        ModuleNode model = arch.getModule("model");
        assertNotNull("1", dwd);
    }
}
