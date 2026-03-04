---
title: "Building a basic JNDI JDBC report"
date: 2024-01-01T00:00:00Z
draft: false
---

# Building a basic JNDI JDBC report

Maven Redeployment[Updated Jan 10, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491523)*
![](https://kagura.atlassian.net/wiki/aa-avatar/5fb2a5cf-84ca-49d7-a911-29b3c89fb2d7)

# Maven Redeployment

Jan 10, 2014[Cloud editor]()

This is more involved process however it's basically the [Karaf Deployment](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491538) but allows you to take the project entirely out of the Kagura project directory, which is useful if you want to keep your reports in some form of SCM. (Highly recommended.)

I have an example project setup at[https://github.com/arranubels/kagura-custom-assembly](https://github.com/arranubels/kagura-custom-assembly)

We will check this out and get the container started. Once it has been started we can go to the next step, building a report.

##### Terminal 1

```
git clone git@github.com:arranubels/kagura-custom-assembly.git
```

The project has the following layout:

pom.xml
backend/pom.xml
backend/src/main/descriptors/unix-bin-dev.xml
backend/src/main/descriptors/unix-bin.xml
backend/src/main/descriptors/windows-bin.xml
backend/src/main/filtered-resources/etc/com.base2.kagura.services.camel.cfg
backend/src/main/filtered-resources/etc-dev/com.base2.kagura.services.camel.cfg

```
backend/src/main/reports/.keep
```

This project pulls down the Kagura Assembly file from Maven, unpacks it, modifies then repacks it. So from this point you can insert your own configuration, and reports into the bundle, which provides an single-file deploy for your operations team or CI pipeline.

The files are as follows:

```
 
```

File
--

mvn clean compile package
cd backend/target
tar xzf kagura-assembly-1.3.tar.gz
cd kagura-assembly-1.3-SNAPSHOT/bin

```
./karaf debug
```

In terminal 2, to create the web server for the content you can do the following:

cd frontend

```
mvn org.mortbay.jetty:jetty-maven-plugin:8.1.12.v20130726:run 
```

Once both services are up you will be able to see and use the page at:

[http://localhost:8000/](http://localhost:8000/)

You can find the test users details and report configuration in the "users.yaml" file in the kagura/services/kagura-camel/src/main/resources/TestReports directory.

Maven Redeployment
[Updated Jan 10, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491523)

Maven Redeployment
Jan 10, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491523/www.atlassian.com)

This is more involved process however it's basically the [Karaf Deployment](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491538) but allows you to take the project entirely out of the Kagura project directory, which is useful if you want to keep your reports in some form of SCM. (Highly recommended.)

I have an example project setup at
https://github.com/arranubels/kagura-custom-assembly

We will check this out and get the container started. Once it has been started we can go to the next step, building a report.
Terminal 1
git clone git@github.com:arranubels/kagura-custom-assembly.git

The project has the following layout:
pom.xml
backend/pom.xml
backend/src/main/descriptors/unix-bin-dev.xml
backend/src/main/descriptors/unix-bin.xml
backend/src/main/descriptors/windows-bin.xml
backend/src/main/filtered-resources/etc/com.base2.kagura.services.camel.cfg
backend/src/main/filtered-resources/etc-dev/com.base2.kagura.services.camel.cfg
backend/src/main/reports/.keep

This project pulls down the Kagura Assembly file from Maven, unpacks it, modifies then repacks it. So from this point you can insert your own configuration, and reports into the bundle, which provides an single-file deploy for your operations team or CI pipeline.

The files are as follows:



File

Description

pom.xml


The parent pom file. You can customise the Artifact ID and versions

backend/pom.xml


Handles the repackaging of the files

backend/src/main/descriptors/unix-bin-dev.xml
backend/src/main/descriptors/unix-bin.xml
backend/src/main/descriptors/windows-bin.xml


Contains the file layout for the repackaging.

backend/src/main/filtered-resources/etc/com.base2.kagura.services.camel.cfg
backend/src/main/filtered-resources/etc-dev/com.base2.kagura.services.camel.cfg


Configuration files that are overwritten. The DEV version points to the report files in the source directory so you don't need to restart & reassemble the package every change. (Allows for live changes.)

backend/src/main/reports/.keep


This is the location you place your users.yaml, groups.yaml if using the file provider and the reports if you are using a file based report provider.

frontend/pom.xml
frontend/src/main/webapp/.keep


The frontend. Added to the project for simplicity of these instructions. POM takes the Example-Javascript file and writes it into (as an overlay) the war file of "frontend" then allows you to deploy it to Jetty. To extend this, you can overwrite the imported files on an as needed basis, start your own version, or copy the values. More options will exist in the future.

Most of these files you will not have to modify, you will only need to create a users.yaml, groups.yaml and some reports.

Once you have customised what you need to, to continue bringing the environment up do the following;
Terminal 1
mvn clean compile package
cd backend/target
tar xzf kagura-assembly-1.3.tar.gz
cd kagura-assembly-1.3-SNAPSHOT/bin
./karaf debug

In terminal 2, to create the web server for the content you can do the following:
cd frontend
mvn org.mortbay.jetty:jetty-maven-plugin:8.1.12.v20130726:run

Once both services are up you will be able to see and use the page at:

http://localhost:8000/

You can find the test users details and report configuration in the "users.yaml" file in the kagura/services/kagura-camel/src/main/resources/TestReports directory.