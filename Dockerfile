
# Stage 1: Build
FROM amazoncorretto:17-alpine AS build

WORKDIR /app

# Copier les fichiers Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Rendre le wrapper Maven exécutable
RUN chmod +x ./mvnw

# Télécharger les dépendances
RUN ./mvnw dependency:go-offline -B

# Copier le code source
COPY src src

# Copier application-prod.properties depuis la racine vers resources
COPY application-prod.properties src/main/resources/application-prod.properties

# Build l'application
RUN ./mvnw clean package -DskipTests

# Stage 2: Production
FROM amazoncorretto:17-alpine

# Installer curl pour healthcheck
RUN apk add --no-cache curl

# Créer utilisateur non-root
RUN addgroup -g 1001 -S spring && adduser -u 1001 -S spring -G spring

WORKDIR /app

# Copier le JAR depuis build
COPY --from=build /app/target/*.jar app.jar

# Permissions
RUN chown spring:spring app.jar

USER spring

# Port dynamique pour Render
EXPOSE ${PORT:-10000}

# Variables d'environnement JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Démarrage avec profile prod
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-10000} -Dspring.profiles.active=prod -jar app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
  CMD curl -f http://localhost:${PORT:-10000}/actuator/health || exit 1
