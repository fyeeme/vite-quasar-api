package ${basePackage}.${modelNameLowerCamel}.service;

import ${basePackage}.base.entity.QueryCondition;
import ${basePackage}.base.service.ResourceService;
import ${basePackage}.${modelNameLowerCamel}.entity.${modelNameUpperCamel};

public interface ${modelNameUpperCamel}Service extends ResourceService<${modelNameUpperCamel},QueryCondition> {

    ${modelNameUpperCamel} publish(Long id);

    ${modelNameUpperCamel} undoPublish(Long id);
}