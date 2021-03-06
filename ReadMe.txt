Shopify - An online shopping website.
----------------------------------------------------------------------------------------------------------------------------------------------------------
Env Setup:
1. JDK 7 - Update 21. Download and install.
2. Netbeans 7.3 Java EE edition. Install Apache tomcat 7 instead of glass fish during installation.
3. Default user record
	user: admin
	pwd: admin
4. The project downloads the Jquery CSS from internet and libraries from internet, so to run the project you will need to be connected to internet.
5. Captcha - Google captcha is used, need to be connected to internet for this to work.
6. Default Gmail account is shopify.ind@gmail.com
	user: shopify.ind@gmail.com
	pwd: shopify@12345
	Check this for all emails addressed to admin user.
7. If you see an error - "Error connecting to server localhost on port 1527" This means derby db has not started, connect to the db via netbeans and db will start automatically.
	
Code:
1. Maven - A central repository where all libraries reside. They are downloaded on demand. pom.xml holds all the library names required for the project.
2. MVC - Model=Database Object, View = JSP, Controller = Servlet.
3. CSS - Gives extra style/color to web pages.
4. Jquery - A javascript framework that makes it easy to work with html objects. Provides easy way to read HTML object and change them.
5. Ajax - A way to make call to server without refreshing the whole page. Only partial page is refreshed.
6. DAO - Data access objects - Talks to database and assigns the value to Java objects.
7. Domain Objects - Database records are assigned to java objects making it easy to work. Number of items in domain =  Number of tables.
8. Filter - Intercepts each request to make sure user has logged in.
9. Const.java - Holds all the configurable parameters.
10. Derby - A java database. Comes installed by default with JDK.
11. Netbeans - An IDE similar to Visual Studio 2010.

----------------------------------------------------------------------------------------------------------------------------------------------------------
Things working:
1. Sign in with valid username & pwd first time.
2. Invalid sign in, after W attempts captcha comes.
3. Invalid sign in, after X attempts account gets locks and need verification code to unlock. Verification code is generated each time and sent as email. Once account is locked verification code is need to login.
4. Invalid sign in, after Y attempts the IP address is black listed for TTL = Z secs. Once TTL expires user can try login from same ip address.
5. Sign in from different IP with right username & pwd - Same flow as know ip machine.
6. Logout
7. Valid sign in after account is locked.


Hint: Check Const.java for values of W,X,Y,Z
----------------------------------------------------------------------------------------------------------------------------------------------------------
Things Pending:
1. Signup
2. User availability check - Ajax call.
3. Email and Phone number validation.
4. Forgot password.
5. CRUD - Products - Add price,name etc.
6. CRUD - Order - Add to shopping cart and checkout. Send email to admin + user on purchase order.
----------------------------------------------------------------------------------------------------------------------------------------------------------
Things to Read:
1. Java Basics - Detailed study of padma reddy book.
2. JDBC - Know what is JDBC and look at examples online.
3. JSTL - Know what is JSTL and look at examples online.
4. JSP & Servlet- Detailed study online
----------------------------------------------------------------------------------------------------------------------------------------------------------
Getting Code From Server:
First time setup only after installing git: NEEDS TO BE DONE ONLY ONCE.
1. Team -> Git -> Clone
2. Repository Url : https://github.com/arjunsurendra04/shopify.git
3. Next, Next, Next, Finish. (DONT CHANGE ANY DEFAULTS)
4. Open Project
----------------------------------------------------------------------------------------------------------------------------------------------------------
To get latest code from server
1. Select Project
2. Right click -> Git -> Remote -> Pull.
3. Next
4. Select "master -> origin/master"
5. Finish.
----------------------------------------------------------------------------------------------------------------------------------------------------------
Create DB:
1. Database that is used is derby. It is default database that comes with java. Using netbeans services tab create a db with following name
	schema: shopify
	user: shopify_user
	pwd: shopify_pwd
2. Under the project db folder there is a sql script - shopify-db.sql. Execute this script against the db using netbeans. This will create all the tables and insert the first user record

----------------------------------------------------------------------------------------------------------------------------------------------------------
To Run Project after DB tables are created:
1. Build project in netbeans after opeing it - Shift+F11
2. Run project in netbeans - F6. It will ask you to pick server. Select tomcat server and click ok.
3. Dont modify the tomcat server setting in any way. 
----------------------------------------------------------------------------------------------------------------------------------------------------------