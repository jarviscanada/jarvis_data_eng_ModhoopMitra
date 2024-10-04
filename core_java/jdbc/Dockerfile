FROM openjdk:8-alpine
WORKDIR /usr/local/app
COPY target/jdbc*.jar ./jdbc/lib/jdbc.jar
COPY src/main/resources/properties.txt ./jdbc/src/main/resources/properties.txt
ENTRYPOINT ["java", "-jar", "./jdbc/lib/jdbc.jar"]
