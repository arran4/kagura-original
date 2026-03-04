# Karaf Deployment

Karaf Deployment

First we need to checkout the code:
git clone https://github.com/base2Services/kagura.git
cd kagura
mvn compile package install

Open up another terminal:

In terminal 1, we are going to start the Jetty service, this is where we host the
Terminal 1
cd example/javascript
mvn org.mortbay.jetty:jetty-maven-plugin:8.1.12.v20130726:run

Press ^C to end it.

In terminal 2, we are going to start the Camel service which provides the REST backend. All calls the the rest services are routed through Jetty to prevent cross domain scripting issues.
Terminal 2
cd services/kagura-assembly/target
tar xzf kagura-assembly-1.3.tar.gz
cd kagura-assembly-1.3/bin
./karaf debug

Press ^D to end it.

You will need to wait a while for the Karaf container to start up, you can use the Karaf command:
log:tail

To watch the logs until they start up successfully.

Once both services are up you will be able to see and use the page at:

http://localhost:8000/

You can find the test users details and report configuration in the "users.yaml" file in the kagura/services/kagura-camel/src/main/resources/TestReports directory.
