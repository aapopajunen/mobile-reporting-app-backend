# mobile-reporting-app-backend

# Setup

1. Clone the repository into the destination of your choice.
   >git clone git@github.com:aapopajunen/mobile-reporting-app-backend.git

2. Open in IntelliJ IDEA.
   >IDEA should setup the project properly.

3. Create application.properties.
   >Create a file called application.properties into mobile-reporting-app-backend/src/main/resources. If the resources folder    does not exist, create it also.
   
4. Configure the database connection using application.
   Copy this to application.properties:
   
   spring.datasource.type=com.zaxxer.hikari.HikariDataSource
   spring.datasource.url="YOUR DATABASE URL HERE"
   spring.datasource.username="YOUR DATABASE USERNAME HERE"
   spring.datasource.password="YOUR DATABASE PASSWORD HERE"
   



   
