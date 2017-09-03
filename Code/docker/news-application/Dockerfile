FROM maven:3.5.0-jdk-8

RUN apt-get update && apt-get install mysql-client -y

ARG source
ARG destination
ARG startScript

ADD ${startScript} /run.sh
ADD ${source} ${destinationv}

EXPOSE 8080

ENTRYPOINT ["/run.sh"]