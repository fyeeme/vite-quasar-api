package ${basePackage}.${modelName}.controller;

import ${basePackage}.base.entity.QueryCondition;
import ${basePackage}.${modelName}.entity.${modelNameUpperCamel};
import ${basePackage}.${modelName}.service.${modelNameUpperCamel}Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${modelNameLowerCamel}s")
@Tag(name = "${modelNameLowerCamel}", description = "${modelNameUpperCamel} Management API")
public class ${modelNameUpperCamel}Controller {

    private final ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    public ${modelNameUpperCamel}Controller(${modelNameUpperCamel}Service ${modelNameLowerCamel}Service) {
        this.${modelNameLowerCamel}Service = ${modelNameLowerCamel}Service;
    }

    @Operation(summary = "Create new ${modelNameLowerCamel}")
    @Parameter(name = "${modelNameLowerCamel}", description = "The ${modelNameLowerCamel} data")
    @PostMapping
    public ${modelNameUpperCamel} create(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameUpperCamel} saved${modelNameUpperCamel} = ${modelNameLowerCamel}Service.create(${modelNameLowerCamel});
        return saved${modelNameUpperCamel};
    }

    @Operation(summary = "Update ${modelNameLowerCamel} by ID")
    @Parameter(name = "id", description = "The ${modelNameLowerCamel} id")
    @Parameter(name = "${modelNameLowerCamel}", description = "The ${modelNameLowerCamel} dto")
    @PutMapping("{id}")
    public ${modelNameUpperCamel} update(@PathVariable Long id, @RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameUpperCamel} saved${modelNameUpperCamel} = ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return saved${modelNameUpperCamel};
    }

    @Operation(summary = "Get existed ${modelNameLowerCamel} by ID")
    @Parameter(name = "id", description = "The ${modelNameLowerCamel} id")
    @GetMapping("/{id}")
    public ${modelNameUpperCamel} get(@PathVariable Long id) {
        return ${modelNameLowerCamel}Service.get(id);
    }

    @Operation(summary = "Delete existed ${modelNameLowerCamel} by ID")
    @Parameter(name = "id", description = "The ${modelNameLowerCamel} id")
    @DeleteMapping("/{id}")
    public ${modelNameUpperCamel} delete(@PathVariable Long id) {
        return ${modelNameLowerCamel}Service.delete(id);
    }

    @Operation(description = "This api is only prepared for admin", summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @GetMapping
    public Page<${modelNameUpperCamel}> findAll(QueryCondition filter) {
        Page<${modelNameUpperCamel}> result = ${modelNameLowerCamel}Service.findAll(filter);
        return result;
    }

    @Operation(summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @GetMapping("/search")
    public Page<${modelNameUpperCamel}> search(@RequestBody QueryCondition query) {
        return ${modelNameLowerCamel}Service.findAll(query);
    }

    @Operation(description = "This api is only prepared for admin", summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @PutMapping("{id}/publish")
        public ${modelNameUpperCamel} publish(@PathVariable Long id) {
        return ${modelNameLowerCamel}Service.publish(id);
    }

    @Operation(summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @DeleteMapping("{id}/publish")
        public ${modelNameUpperCamel} undoPublish(@PathVariable Long id) {
        return ${modelNameLowerCamel}Service.undoPublish(id);
    }
}
