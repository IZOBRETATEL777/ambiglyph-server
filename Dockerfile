FROM amazoncorretto:17 as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
CMD ["mvn package"]

FROM amazoncorretto:17
COPY --from=build /usr/app/target/ambiglyph-server-0.0.1-SNAPSHOT.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar