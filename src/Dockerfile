FROM eclipse-temurin:21-alpine

RUN apk add --no-cache maven

WORkDIR /server
COPY . .

RUN mvn install -DskipTests
RUN mvn compile

ENTRYPOINT [ "java", "-cp", "backend/target/classes", "cd.project.backend.Main" ]