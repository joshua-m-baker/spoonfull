# Spoonfull

Usage:

```
./gradlew run
```

Access OpenApi Spec:

```
./gradlew run
http://localhost:8080/swagger/spoonfull-0.0.yml
(or same endpoint in hosted version)
```

sample docker-compose.yml

```yaml
services:
  spoonfull:
    image: joshuabaker97/spoonfull:8
    container_name: spoonfull
    restart: unless-stopped
    environment:
      DB_URL: jdbc:postgresql://spoonfull-postgres:5432/postgres
      DB_USER: spoonfull
      DB_PASS: spoonfull

  spoonfull-postgres:
    image: postgres
    container_name: spoonfull-postgres
    restart: unless-stopped
    expose:
      - "5432"
    environment:
      POSTGRES_USER: spoonfull
      POSTGRES_PASSWORD: spoonfull
```

## Micronaut 4.7.6 Documentation

- [User Guide](https://docs.micronaut.io/4.7.6/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.7.6/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.7.6/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature kotest documentation

- [Micronaut Test Kotest5 documentation](https://micronaut-projects.github.io/micronaut-test/latest/guide/#kotest5)

- [https://kotest.io/](https://kotest.io/)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature ksp documentation

- [Micronaut Kotlin Symbol Processing (KSP) documentation](https://docs.micronaut.io/latest/guide/#kotlin)

- [https://kotlinlang.org/docs/ksp-overview.html](https://kotlinlang.org/docs/ksp-overview.html)


