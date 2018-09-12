# Facebook_OAuth

Get your OAuth application registered in Facebook Developer account and obtain App ID and App Secret values.

The OAuth redirection endpoint of the client application is https://localhost:<port>/callback.

Once you download/clone this project, you need to modify the following two files.

1. src/main/java/com/kasun/facebook/facebookauth/controller/authController.java
------------------------------------------------------------------------------------

This has following code.

            final String REDIRECT_URI = "https://localhost:<port>/callback";
            final String CLIENT_ID = "<your app id>";
            final String CLIENT_SECRET = "<your secret>";

Put your App ID and App Secret in above. If your redirect URL is different, change the redirect URI accordingly.

2. src/main/resources/application.properties
------------------------------------------------------------------------------------

This has following code.

            server.port=<port>
            # Tell Spring Security (if used) to require requests over HTTPS
            security.require-ssl=true
            # The format used for the keystore 
            server.ssl.key-store-type=JKS
            # The path to the keystore containing the certificate
            server.ssl.key-store=classpath:keystore.jks
            # The password used to generate the certificate
            server.ssl.key-store-password=password
            # The alias mapped to the certificate
            server.ssl.key-alias=server
Put your port number in bove.
If you have different keystore.jks file, change above code accordingly and put your .jks file in src/main/resources/

After the above changes, build the project with Maven. (eg: mvn clean package).

Then run your project (eg: java -jar target\facebookauth-0.0.1-SNAPSHOT.war --server.port=<port>)

You can access the application with the URL https://localhost:<port>/
