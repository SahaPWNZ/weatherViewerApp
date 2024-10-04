

FROM tomcat:10.1.25-jdk21-temurin-jammy
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/*
COPY target/ROOT.war webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]