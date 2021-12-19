## TODO
 - [x] auditing
 - [x] cors
 - [ ] csrf
 - [ ] exception
 - [ ] api result
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

getUser
