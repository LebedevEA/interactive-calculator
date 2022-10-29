FROM amazoncorretto:16
COPY . /app
WORKDIR /app
RUN ./gradlew installDist
CMD ./gradlew -q run --console=plain