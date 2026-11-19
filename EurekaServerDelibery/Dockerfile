# ==========================================
# ETAPA 1: COMPILACION
# ==========================================
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copiamos el proyecto
COPY pom.xml .
COPY src ./src

# Generamos el JAR
RUN mvn -B clean package -DskipTests


# ==========================================
# ETAPA 2: EJECUCION
# ==========================================
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto del Config Server
EXPOSE 8761

# Iniciamos Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]