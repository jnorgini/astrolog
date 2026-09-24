FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

ENV MAVEN_OPTS="-Xmx512m -XX:MaxMetaspaceSize=256m"

COPY backend/ .

RUN mvn install:install-file \
	-Dfile=libs/swisseph-2.01.00-01.jar \
	-DgroupId=swisseph \
	-DartifactId=swisseph \
	-Dversion=2.01.00-01 \
	-Dpackaging=jar \
	-DgeneratePom=true

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jre-jammy
VOLUME /tmp
WORKDIR /app

COPY --from=build /app/src/main/resources/ephe/ ./ephe/
COPY --from=build /app/target/*[^p][^l][^a][^i][^n].jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
