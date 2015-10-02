package br.edu.ufcg.splab.designtests.util;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;
import org.designwizard.design.Entity.TypesOfEntities;
import org.designwizard.exception.InexistentEntityException;

/**
 * Class with functions for extracting information of persistent classes.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class PersistenceRuleUtil {

    /**
     * Checks if the class contains a identifier field in the declared fields.
     * The identifier field is annotated for <code>javax.persistence.Id</code>.
     * @param entity A classNode instance.
     * @return The identifier field or <code>Null</code>.
     */
    public FieldNode getIdentifierProperty(ClassNode entity) {
        Set<FieldNode> declaredFields = entity.getAllFields();
        for (FieldNode fieldNode : declaredFields) {
            Set<ClassNode> annotations = fieldNode.getAnnotations();
            ClassNode id = new ClassNode("javax.persistence.Id");
            if (annotations.contains(id)) {
                return fieldNode;
            }
        }
        return null;
    }

    /**
     * Returns the set of <code>ClassNode</code> with the annotated classes to the entity
     * represented by this <code>annotationName</code>.
     * @param dw A DesignWizard instance.
     * @param annotationName The name of the entity that It is an annotation.
     * @return the set of the annotated classes or <code>null</code> if this
     * parameter wasn't an annotation.
     * @throws InexistentEntityException if the annotation cannot be located.
     */
    public Set<ClassNode> getClassesAnnotated(DesignWizard dw, String annotationName) throws InexistentEntityException {
        Set<ClassNode> classes = new HashSet<ClassNode>();
        Set<Entity> entities = dw.getEntitiesAnnotatedBy(annotationName);
        for (Entity entity : entities) {
            if (entity.getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
                classes.add((ClassNode)entity);
            }
        }
        return classes;
    }
}
