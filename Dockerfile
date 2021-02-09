FROM java

COPY ./target/dynamic-ip-relatest-jar-with-dependencies.jar /app/dynamic-ip-relatest.jar
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

CMD java -jar /app/dynamic-ip-relatest.jar
