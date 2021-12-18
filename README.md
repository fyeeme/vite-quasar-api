
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

## security

getUser
