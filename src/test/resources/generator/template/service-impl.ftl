package ${basePackage}.${modelName}.service;

import ${basePackage}.base.entity.QueryCondition;
import ${basePackage}.base.repository.ResourceRepository;
import ${basePackage}.base.repository.support.GenericSpecificationBuilder;
import ${basePackage}.${modelName}.repository.${modelNameUpperCamel}Repository;
import ${basePackage}.${modelName}.entity.${modelNameUpperCamel};

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    private final ${modelNameUpperCamel}Repository repository;

    public ${modelNameUpperCamel}ServiceImpl(${modelNameUpperCamel}Repository repository) {
        this.repository = repository;
    }

    @Override
    public ResourceRepository<${modelNameUpperCamel}> getRepository() {
        return repository;
    }

    @Override
    public Specification<${modelNameUpperCamel}> buildSpecs(QueryCondition query) {
        return GenericSpecificationBuilder.buildSpecs(query, ${modelNameUpperCamel}.class);
    }

    @Override
    public String[] extendedIgnoreProperties() {
        return extendedIgnoreProperties("pid");
    }
}