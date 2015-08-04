package tests.br.edu.ufcg.splab.designtests;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.PackageNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

import junit.framework.TestCase;

/**
 *
 * Rule: Override both equals(java.lang.Object) and hashCode() in classes of the Model Package.
 *
 * @author Taciano
 */
public class HashCodeEqualsModelDesignTest extends TestCase {

    public void testHashAndEquals() throws IOException, InexistentEntityException {

        DesignWizard dw = new DesignWizard("jars/sigest.jar");

        PackageNode model = dw.getPackage("br.ufrn.cerescaico.bsi.sigest.model");

        Collection<ClassNode> allModelClasses = model.getAllClasses();

        boolean total = true;

        for (ClassNode entityNode : allModelClasses) {

            MethodNode equalsMethod = entityNode
                    .getDeclaredMethod("equals(java.lang.Object)");
            MethodNode hashCodeMethod = entityNode
                    .getDeclaredMethod("hashCode()");

            Set<MethodNode> declaredMethods = entityNode.getDeclaredMethods();

            boolean teste = declaredMethods.contains(equalsMethod) && declaredMethods.contains(hashCodeMethod);

            if (!(teste)) {
                System.out.println("ClassNode: " + entityNode.getName());
                System.out.println("Equals Methods: " + declaredMethods.contains(equalsMethod));
                System.out.println("HashCode Methods: " + declaredMethods.contains(hashCodeMethod));
            }

            total = total && teste;

        }
        assertTrue(total);
    }
}
