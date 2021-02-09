FROM java

COPY ./target/dynamic-ip-relatest-jar-with-dependencies.jar /app/dynamic-ip-relatest.jar

CMD java -jar /app/dynamic-ip-relatest.jar
