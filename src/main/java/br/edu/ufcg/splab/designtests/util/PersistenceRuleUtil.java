package br.edu.ufcg.splab.designtests.util;

import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;

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

}
