# mobile-reporting-app-backend

# Setup

1. Clone the repository into the destination of your choice.
   git clone git@github.com:aapopajunen/mobile-reporting-app-backend.git

2. Open in IntelliJ IDEA.
   IDEA should setup the project properly.
   
3. Configure src/resources/application.properties.
   In this file you can configure the connection to the database. By default it is set to connect to a mySQL database called      "formDB" running on localhost port 8889. The fastest way to get your database up un running is to install MAMP. 
   
   About MAMP setup:
   Just press the "Start Servers" button, MAMP will start a mySQL database on localhost port 8889.

4. Copy the provided example database "formDB" into the right directory.
   On windows:
   C:\MAMP\db\mysql\
   On mac:
   /Applications/MAMP/db/mysql56

5. Run.
   Run the application, open your browser and type in: localhost:8889/forms. The result should look something like this: 
   [{"id":1,"name":"siisti raportti"},{"id":2,"name":"siistimpi raportti"},{"id":3,"name":"siistein raportti"}]
   
# Managing the database

From MAMP, press the "Open WebStart page", a website should open. Then press the phpMyAdmin link under MySQL, it should take you the phpMyAdmin service. With phpMyAdmin you can manage and create new databases.

For mac users I suggest dowloading Sequel Pro, a free software for easy database management.
   
