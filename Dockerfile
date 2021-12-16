FROM adoptopenjdk/openjdk16:ubi

EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM ubuntu
#RUN ["apt-get", "update"]
#RUN ["apt-get", "install", "-y", "vim"]