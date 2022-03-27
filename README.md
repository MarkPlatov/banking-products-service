* Сервис должен __работает__ с данными в формате json
* Правила задаются с помощью POST запросов вида:  
  ```
  /products/{productId}/?min_salary={minSalary}&allow_debtors={allowDebtors}
  /products/{productId}/?min_salary={minSalary}
  /products/{productId}/?allow_debtors={allowDebtors}
  ```
* Сервис реализован с помощью Spring Boot
* Продукты и правила хранятся в PostgreSQL.  
  Продукты связаны с правилами отношением one-to-many
* При старте сервиса предопределено несколько продуктов и правил к ним.  
  Seeder базы можно найти в `src/main/java/com/example/bankingproductsservice/seeds/DBSeed.java`
* Реализованы модульные автотесты JUnit, покрывающие основной функционал сервиса
* У сущностей есть служебные поля: 
  * Дата создания,
  * Дата последнего изменения, 
  * Признак удаленной или активной сущности (Удаленные сущности помечаются флагом и продолжают храниться в бд, но в работе не используются)
* Конфигурационные параметры хранятся в  
  `src/main/resources/application.properties`