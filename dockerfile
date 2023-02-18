FROM adoptopenjdk/openjdk11
WORKDIR /app
COPY . ./
RUN ./gradlew build
ENTRYPOINT ["java","-jar","build/libs/todo-0.0.1-SNAPSHOT.jar"]