## TODO
 - [x] auditing
 - [x] cors
 - [x] csrf
 - [x] exception
 - [x] api result
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
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing
https://www.programmingmitra.com/2017/02/automatic-spring-data-jpa-auditing-saving-CreatedBy-createddate-lastmodifiedby-lastmodifieddate-automatically.html

## cors with security
use global cors replace @CrossOrigin on Controller level

https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/cors.html

## csrf
https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/csrf.html
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