Rest приложение для получения и хранения истории торгов по акциям.

Чтобы запустать приложение необходимо:
1) В файл .env добавить следующие настройки (см. пример .env-example):

- SECRET_KEY - ключ для генерации JWT токенов (base 64)
- POLYGON_TOKEN - api - токен от сервиса polygon.io
- APP_PORT - порт на котором запустить приложение (8080 по умолчанию)
- APP_PROFILE - debug, production, component-test
- DB_PORT 
- DB_USERNAME
- DB_PASSWORD
- DB_URL - по умолчанию jdbc:postgresql://localhost:5432/stocks_db
для Docker: jdbc:postgresql://db:${DB_PORT}/stocks_db

2) Запустить через IDE c переменными окружения или собрать образ для Docker Container

Документация для api находится по /swagger-ui/index.html

Используемые технологии:
- Java 17
- Spring Boot
- Spring Security
- Spring Validation
- SpringDoc OpenApi
- JJWT library
- Postgres DB
- Liquibase
- Lombok
- Testcontainers

