package tests.br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.designwizard.design.PackageNode;
import org.designwizard.main.DesignWizard;

import junit.framework.TestCase;

/**
 * Rule: Avoid Cyclic Dependency between packages
 *
 * @author Taciano Morais Silva
 */

public class CyclicDependencyDesignTest extends TestCase {

    public void testCyclicDependency() throws IOException {

        DesignWizard dw = new DesignWizard("jars/sigest.jar");

        Collection<PackageNode> allPackages = dw.getAllPackages();

        for (PackageNode packageNode : allPackages) {

            Set<PackageNode> callersPackages = packageNode.getCallerPackages();

            for (PackageNode caller : callersPackages) {
                assertFalse(caller.getCallerPackages().contains(packageNode));
            }

        }
    }
}
