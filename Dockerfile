FROM bellsoft/liberica-openjdk-alpine
MAINTAINER lfmm
COPY target/productsapi-1.0.0.jar productsapi-1.0.0.jar
ENTRYPOINT ["java","-jar","/productsapi-1.0.0.jar"]