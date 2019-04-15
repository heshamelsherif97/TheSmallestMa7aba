FROM ma7abasquad/trial:FirstTrial
MAINTAINER ma7abasquad (medhat.hamed96@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn package
ENTRYPOINT ["java","-cp","target/cache-1.0-SNAPSHOT.jar"]