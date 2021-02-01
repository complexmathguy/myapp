FROM openjdk:8

ADD . /
RUN ls
RUN mkdir /usr/local/demoApp
COPY target/com/occulue/demo/1.0.0/demo-1.0.0.jar /usr/local/demoApp/

# run tomcat
CMD ["java", "-jar", "/usr/local/demoApp/demo-1.0.0.jar"]
