FROM ma7abasquad/trial:FirstPush
MAINTAINER ma7abasquad (medhat.hamed96@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn package
ENTRYPOINT ["java","-cp","target/cache.jar"] 