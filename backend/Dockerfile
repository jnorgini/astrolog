FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

RUN mvn install:install-file \
	-Dfile=libs/swisseph-2.01.00-01.jar \
	-DgroupId=swisseph \
	-DartifactId=swisseph \
	-Dversion=2.01.00-01 \
	-Dpackaging=jar \
	-DgeneratePom=true

RUN mvn clean package -DskipTests

RUN rm -f target/*-plain.jar

FROM eclipse-temurin:17-jre
VOLUME /tmp
WORKDIR /app

COPY --from=build /app/src/main/resources/ephe/ ./ephe/

COPY --from=build /app/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
