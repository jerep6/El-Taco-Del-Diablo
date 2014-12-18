FROM debian:wheezy
MAINTAINER jerep6

RUN mkdir /app/ \
&& mkdir -p /app/mongodb/log \
&& mkdir -p /app/mongodb/data \
&& mkdir -p /app/mongodb/conf


#Le dezip est effectué par la commande ADD
ADD mongodb-linux-x86_64.tgz  /tmp

RUN  mv /tmp/mongodb-linux*/* /app/mongodb/

ADD mongodb.cfg /app/mongodb/conf/

VOLUME /app/mongodb/data/
EXPOSE 27017

CMD /app/mongodb/bin/mongod --config /app/mongodb/conf/mongodb.cfg


