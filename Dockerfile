FROM tomcat:11.0.7-jdk21-temurin

ENV CATALINA_HOME=/usr/local/tomcat

WORKDIR $CATALINA_HOME

# Remove default apps
RUN rm -rf webapps/*

# Copy WAR to ROOT.war
COPY ./target/CRUD_MVC_JDBCpgsql_SERVLETJSP.war webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
