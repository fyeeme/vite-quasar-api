package com.fyeeme.quasar.base.entity;

public class ProjectConstant {
    public static final String BASE_PACKAGE = "com.fyeeme.quasar";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    //生成的Entity所在包
    public static String modelPackage(String model) {
        return String.format("%s.%s.entity", BASE_PACKAGE, model);
    }

    //生成的Dto所在包
    public static String dtoPackage(String model) {
        return String.format("%s.%s.dto", BASE_PACKAGE, model);
    }

    //生成的Mapper所在包
    public static String repositoryPackage(String module) {
        return String.format("%s.%s.repository", BASE_PACKAGE, module);
    }

    //生成的Service所在包
    public static String servicePackage(String module) {
        return String.format("%s.%s.service", BASE_PACKAGE, module);
    }

    //生成的Controller所在包
    public static String controllerPackage(String module) {
        return String.format("%s.%s.controller", BASE_PACKAGE, module);
    }
}
