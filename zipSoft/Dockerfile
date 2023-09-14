FROM openjdk:22-jdk

RUN mkdir -p /data/web
RUN mkdir -p /data/logs

ADD /build/libs/zipSoft-0.0.1-SNAPSHOT.war /data/web/zipSoft-0.0.1-SNAPSHOT.war

WORKDIR /data/web

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "zipSoft-0.0.1-SNAPSHOT.war"]