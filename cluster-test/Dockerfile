FROM redis:latest

MAINTAINER ma7abasquad (medhat.hamed96@gmail.com)
WORKDIR /usr/local/etc/redis
RUN ["mkdir", "7000"]
RUN ["mkdir", "7001"]
RUN ["mkdir", "7002"]
RUN ["mkdir", "7003"]
RUN ["mkdir", "7004"]
RUN ["mkdir", "7005"]
COPY ./7000/redis.conf  /usr/local/etc/redis/7000/redis.conf   
COPY ./7001/redis.conf  /usr/local/etc/redis/7001/redis.conf
COPY ./7002/redis.conf  /usr/local/etc/redis/7002/redis.conf
COPY ./7003/redis.conf  /usr/local/etc/redis/7003/redis.conf
COPY ./7004/redis.conf  /usr/local/etc/redis/7004/redis.conf
COPY ./7005/redis.conf  /usr/local/etc/redis/7005/redis.conf
COPY redis-server /usr/local/etc/redis/redis-server
