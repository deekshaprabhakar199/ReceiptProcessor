FROM openjdk:17-oracle
COPY target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


#ARG JAR_FILE=target/*.jar
#COPY target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]
