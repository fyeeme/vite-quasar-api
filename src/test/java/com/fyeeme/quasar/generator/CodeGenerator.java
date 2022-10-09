package com.fyeeme.quasar.generator;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.fyeeme.quasar.base.entity.ProjectConstant.*;

/**
 * Code generator.
 * Generator `Controller`、`Service`、`Model`、`DTO`、`Repository` template code by given database tableName and modelName in order to simplify development
 */
@SpringBootTest
public class CodeGenerator {
    public static final Logger log = LoggerFactory.getLogger(CodeGenerator.class);
    /***
     *   JDBC config
     *   Use sql to create a proprietary user and solve the mybatis generator problem
     *   CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
     *   GRANT ALL PRIVILEGES ON quasar. * TO 'root'@'localhost';
     */
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quasar";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template";//模板位置

    private static final String JAVA_PATH = "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

    public static final String CUSTOM_COMMENT_GENERATOR ="com.fyeeme.quasar.generator.mybatis.CustomCommentGenerator";

    public static void main(String[] args) {
        genCode(
                 new String[]{"order", "order"}
//                 new String[]{"order_item", "order"}
        );
    }


    /**
     * Generate code by data table name, Model name is obtained by parsing the data table name, underscore to big hump form.
     * If you enter the table name "user_detail" will generate UserDetail, UserDetailMapper, UserDetailService ...
     *
     * @param tableNames Database table name
     */
    private static void genCode(String[]... tableNames) {

        for (String[] tableName : tableNames) {
            genCodeByCustomModelName(tableName[0], tableName[1]);
        }
        // delete mapper.xml, as we use jpa, so the mapper.xml and mapper.java wo don't need
        String[] entities = Arrays.stream(tableNames)
                .map(item -> item[1]).distinct()
                .toArray(String[]::new);
        delResourceMapperDir(entities);
    }

    /**
     * Generate code by data table name, and custom Model name
     * If you enter the table name "t_user_detail" and the custom Model name "User" it will generate User, UserMapper, UserService ...
     *
     * @param tableName Database table name
     * @param modelName Module name (package name)
     */
    public static void genCodeByCustomModelName(String tableName, String modelName) {
        genModelAndMapper(tableName, modelName);
        genService(tableName, modelName);
        genController(tableName, modelName);
        genRepository(tableName, modelName);
        genDto(tableName, modelName);
    }


    public static void genService(String tableName, String modelName) {
        parseTpl(tableName, modelName, servicePackage(modelName), "service.ftl", "Service.java");
        parseTpl(tableName, modelName, servicePackage(modelName), "service-impl.ftl", "ServiceImpl.java");
    }

    public static void genController(String tableName, String modelName) {
        parseTpl(tableName, modelName, controllerPackage(modelName), "controller.ftl", "Controller.java");
    }

    public static void genRepository(String tableName, String modelName) {
        parseTpl(tableName, modelName, repositoryPackage(modelName), "repository.ftl", "Repository.java");
    }

    public static void genDto(String tableName, String modelName) {
        parseTpl(tableName, modelName, dtoPackage(modelName), "dto.ftl", "Dto.java");
    }

    private static void parseTpl(String tableName, String modelName, String packagePath, String tplName, String postfix) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelName", modelName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + packageConvertPath(packagePath) + modelNameUpperCamel + postfix);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate(tplName).process(data, new FileWriter(file));

            log.info("Finished generating {}{}", modelNameUpperCamel, postfix);
        } catch (Exception e) {
            log.error("Fail to generate {}", postfix, e);
        }
    }

    /**
     * There is no better way to generate Entity except using mybatis generator at the moment
     *
     * @param tableName Database table name
     * @param modelName Module name (package name)
     */
    public static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("MyBatis3Simple");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // custom  model comment
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, "true");
        commentGeneratorConfiguration.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE, "true");
        commentGeneratorConfiguration.addProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS, "true");
        commentGeneratorConfiguration.setConfigurationType(CUSTOM_COMMENT_GENERATOR);
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        // config model info
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(modelPackage(modelName));
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // set table info
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (StringUtils.isEmpty(modelName)) modelName = tableNameConvertUpperCamel(tableName);
        log.info("Finished generate code {}.java", modelName);
    }

    private static void delResourceMapperDir(String[] tableNames) {
        // delete all mapper.xml and mapper folder
        File mapperFolder = new File(PROJECT_PATH + RESOURCES_PATH + File.separator + "mapper");
        FileSystemUtils.deleteRecursively(mapperFolder);

        //delete all Mapper.java
        Arrays.stream(tableNames).forEach(tableName -> {
            File repositoryFolder = new File(PROJECT_PATH + JAVA_PATH + File.separator + packageConvertPath(repositoryPackage(tableName)));
            Arrays.stream(Objects.requireNonNull(repositoryFolder.list()))
                    .filter(path -> path.endsWith("Mapper.java"))
                    .forEach(path -> new File(repositoryFolder.getPath(), path).deleteOnExit());
        });
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//compatible with the use of uppercase table names
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}
