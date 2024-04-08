FROM openjdk:17
WORKDIR /romel-auth-service
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} romel-auth.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","romel-auth.jar"]