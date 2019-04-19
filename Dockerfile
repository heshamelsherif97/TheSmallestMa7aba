FROM ma7abasquad/trial:latest
MAINTAINER ma7abasquad (medhat.hamed96@gmail.com)
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
ADD . /app
RUN mvn package
ENTRYPOINT ["java","-cp","target/cache-1.0-SNAPSHOT.jar"]