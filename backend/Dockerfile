FROM alpine:3.19

VOLUME /tmp
WORKDIR /build
COPY . /build
COPY build/libs/*.jar /app/pet-diag-assist.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/pet-diag-assist.jar"]