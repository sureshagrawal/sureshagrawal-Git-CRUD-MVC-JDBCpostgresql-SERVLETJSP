# -------- STAGE 1: Build WAR using Maven --------
FROM maven:3.8.6-openjdk-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src
COPY web ./web  # Only if you have a separate 'web' folder — optional

# Build the WAR
RUN mvn clean package

# -------- STAGE 2: Deploy WAR in Tomcat --------
FROM tomcat:11.0.7-jdk21-temurin

ENV CATALINA_HOME=/usr/local/tomcat
WORKDIR $CATALINA_HOME

# Remove default apps
RUN rm -rf webapps/*

# Copy the WAR built in the previous stage into Tomcat
COPY --from=build /app/target/CRUD_MVC_JDBCpgsql_SERVLETJSP.war webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
