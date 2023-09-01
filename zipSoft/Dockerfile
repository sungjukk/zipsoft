FROM openjdk:22-jdk

RUN mkdir -p /data2/potal/web
RUN mkdir -p /data2/potal/logs

ADD /build/libs/zipSoft-0.0.1-SNAPSHOT.war /data2/potal/web/zipSoft-0.0.1-SNAPSHOT.war

WORKDIR /data2/potal/web

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "zipSoft-0.0.1-SNAPSHOT.war"]