FROM bellsoft/liberica-openjdk-alpine-musl:17
ARG JAR_FILE=build/libs/shopping-list.jar
COPY ${JAR_FILE} app.jar
RUN apk add --no-cache tzdata
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
