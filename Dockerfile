FROM phusion/baseimage:0.9.17
MAINTAINER ma7abasquad (medhat.hamed96@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
ENV JAVA_VER 8
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk
WORKDIR /usr/local/service
RUN mvn package
ENTRYPOINT ["java","-cp","target/cache-1.0-SNAPSHOT.jar"]