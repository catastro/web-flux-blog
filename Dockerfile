FROM openjdk:11-jre
COPY target/blog-*SNAPSHOT.jar /opt/app.jar
ENTRYPOINT ["java","-jar","/opt/app.jar"]