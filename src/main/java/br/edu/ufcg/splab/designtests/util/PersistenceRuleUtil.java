package br.edu.ufcg.splab.designtests.util;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;
import org.designwizard.design.Entity.TypesOfEntities;
import org.designwizard.exception.InexistentEntityException;

public class PersistenceRuleUtil {

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
