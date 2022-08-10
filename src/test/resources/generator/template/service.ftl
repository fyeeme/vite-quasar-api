package ${basePackage}.${modelName}.service;

import ${basePackage}.base.entity.QueryCondition;
import ${basePackage}.base.service.ResourceService;
import ${basePackage}.${modelName}.entity.${modelNameUpperCamel};

public interface ${modelNameUpperCamel}Service extends ResourceService<${modelNameUpperCamel},QueryCondition> {

    default ${modelNameUpperCamel} publish(Long id){
        return null;
    }

    default ${modelNameUpperCamel} undoPublish(Long id){
        return null;
    }
}