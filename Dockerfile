FROM maven:3-openjdk-8

RUN printenv
RUN mvn clean package

RUN mkdir /usr/local/demoApp
COPY ./target/demo-1.0.0.jar /usr/local/demoApp/

# run tomcat
CMD ["java", "-jar", "/usr/local/demoApp/demo-1.0.0.jar"]
