
# Shopping list spring-boot web application

This is a simple project which is based on **spring-boot 2.6.3** and uses **H2** database, **JPA** and contains JUnit tests, **Swagger** and **Docker**.

#### Build the application
<pre>
gradle clean build
</pre>
or using wrapper
<pre>
./gradlew clean build
</pre>
##### Skip running tests
<pre>
gradle clean build -x test
</pre>
##### Add running docker and run the application
<pre>
docker-compose up --build
</pre>

It will automatically pull images and run it

Or simply run the application in your IDE using public static void main method.

Open the following Swagger URL in browser to take a look ***products*** and ***shopping-list*** apis.

Sample data automatically inserts when application boots up
<pre>
http://localhost:8080/swagger-ui/index.html
</pre>
Port is configurable in application.yml.

This application uses H2 database and so should run locally (Username and password described in application.yml file)
<pre>
http://localhost:8080/h2-console
</pre>
Remote database connections can also be configured in application.yml.


