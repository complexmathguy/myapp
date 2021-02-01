FROM openjdk:8

RUN mkdir /usr/local/demoApp
COPY /target/*.jar /usr/local/demoApp/app.jar

# run tomcat
CMD ["java", "-jar", "/usr/local/demoApp/app.jar"]
