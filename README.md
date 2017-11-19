# mobile-reporting-app-backend

# Setup

1. Clone the repository into the destination of your choice.
   >git clone git@github.com:aapopajunen/mobile-reporting-app-backend.git

2. Open in IntelliJ IDEA.
   >IDEA should setup the project properly.
   
3. Install MAMP.

4. Copy the provided example database "formDB" into the right directory.
   >On windows:
   C:\MAMP\db\mysql\
   On mac:
   /Applications/MAMP/db/mysql56
   
5. Open MAMP and press the "Start Servers" button.

6. Run and test.
   >Run the application, open your browser and type in: localhost:8889/forms. The result should look something like this: 
   [{"id":1,"name":"siisti raportti"},{"id":2,"name":"siistimpi raportti"},{"id":3,"name":"siistein raportti"}]
   
# Managing the database

>From MAMP, press the "Open WebStart page", a website should open. Then press the phpMyAdmin link under MySQL, it should take you the phpMyAdmin service. With phpMyAdmin you can manage and create new databases.

>For mac users I suggest dowloading Sequel Pro, a free software for easy database management.

# Configuring the database connection
>If you wish, you can change the database connection properties from src/resources/application.properties.


   
