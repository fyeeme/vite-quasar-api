## TODO

- [x] auditing
-
- [x] cors
- [x] csrf
-
- [x] exception
  - [ ] 优化错误码的逻辑，解决基于数字错误码冲突的问题
- [x] api result
-
- [x] generic jpa specification builder

  - filter white list

- [ ] dto to entity

## auditing

```java
class Customer {

  @CreatedBy
  private User user;

  @CreatedDate
  private Instant createdDate;

  // … further properties omitted
}
```

- [spring-data auditing official doc](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing)
- [www.programmingmitra.com](https://www.programmingmitra.com/2017/02/automatic-spring-data-jpa-auditing-saving-CreatedBy-createddate-lastmodifiedby-lastmodifieddate-automatically.html)

## cors with security

use global cors replace @CrossOrigin on Controller level

[spring security cors official doc](https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/cors.html)

## csrf

[spring security csrf official doc](https://docs.spring.io/spring-security/reference/6.0.0-M5/features/exploits/csrf.html#csrf-protection-ssa)
use SameSite replace csrf token

## security

## JUnit 5 Jupiter

In JUnit 5 the package name has changed and the Assertions are at org.junit.jupiter.api.Assertions and Assumptions at org.junit.jupiter.api.Assumptions

So you have to add the following static import:

```java
import static org.junit.jupiter.api.Assertions.*;
```

See also http://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions

## Exception

```json
{
  "code": 405,
  "status": "fail",
  "data": "MethodNotAllowed",
  "message": "Request method 'GET' not supported"
}
```

```json
{
  "timestamp": "2021-12-27T07:12:40.562+00:00",
  "status": 405,
  "error": "Method Not Allowed",
  "message": "Request method 'GET' not supported",
  "path": "/users/"
}
```

## generic jpa specification builder

a generic way to use specification support your jpa custom query, support **and**, **or** ,**join**

- [self-learning-java-tutorial.blogspot.com](https://self-learning-java-tutorial.blogspot.com/2020/08/spring-jpa-specification-to-join-tables.html)

```http request

POST  http://localhost:8600/users/search HTTP/1.1
Content-Type: application/json

{
  "page": "1",
  "pageSize": "10",
  "andConditions": [
    {
      "field": "nickname",
      "operator": "eq",
      "value": "ly"
    }
  ],
  "orConditions":[
   {
      "field": "username",
      "operator": "like",
      "value": "ly"
    }
  ],
  "joinConditions": [
   {
     "joinField": "roles",
     "condition": {
      "field": "name",
      "operator": "eq",
      "value": "user"
    }
   }
  ]
}

```

## custom profile with gradle bootRun

```shell
 gradle bootRun  --args='--spring.profiles.active=test -sprot=9000'
```

## Open-in-view

https://www.baeldung.com/hibernate-initialize-proxy-exception

```text
Using Join Fetching
We can also use a JOIN FETCH directive in JPQL to fetch the associated collection on-demand:

SELECT u FROM User u JOIN FETCH u.roles

Or we can use the Hibernate Criteria API:

Criteria criteria = session.createCriteria(User.class);
criteria.setFetchMode("roles", FetchMode.EAGER);
```

## spring doc

disable https://petstore.swagger.io/v2/swagger.json

1.  create `src/main/resources/static/swagger-config.json` and content is

```json
{
  "urls": [
    {
      "url": "/v3/api-docs",
      "name": "kms"
    }
  ]
}
```

2. create bean `SwaggerIndexTransformer`

```java
    @Value("${springdoc.api-docs.path}")
    private String apiDocsUrl;

    @Bean
    public SwaggerIndexTransformer indexPageTransformer(
            SwaggerUiConfigProperties swaggerUiConfig, SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerWelcomeCommon swaggerWelcomeCommon, ObjectMapperProvider objectMapperProvider
    ) {
        return new SwaggerIndexPageTransformer(swaggerUiConfig, swaggerUiOAuthProperties, swaggerUiConfigParameters, swaggerWelcomeCommon, objectMapperProvider) {
            @Override
            protected String overwriteSwaggerDefaultUrl(String html) {
                return html.replace(Constants.SWAGGER_UI_DEFAULT_URL, apiDocsUrl);
            }
        };
    }
```

2.1 disable `disable-swagger-default-url`

```yaml
disable-swagger-default-url: true
```

## code generator

创建专有数据库用户

[create mysql user doc](https://www.hostinger.com/tutorials/mysql/how-create-mysql-user-and-grant-permissions-command-line)

```sql
CREATE USER 'quasar'@'localhost' IDENTIFIED BY 'quasar';

GRANT ALL PRIVILEGES ON `quasar`.* TO 'quasar'@'localhost';

FLUSH PRIVILEGES;
```

需要修改 数据配置 `com/fyeeme/quasar/generator/CodeGenerator.java`

将以下配置改成实际配置

```java
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quasar";
    private static final String JDBC_USERNAME = "quasar";
    private static final String JDBC_PASSWORD = "quasar";
```
