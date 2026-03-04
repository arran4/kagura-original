---
title: "War Deployment"
date: 2026-03-04T00:58:36+00:00
draft: false
---
# War Deployment

War Deployment

Goto http://tomee.apache.org/downloads.html download the JaxRS version. As of writing we have developed the War file for version 1.6.
% tar xf apache-tomee-1.6.0-jaxrs.tar.gz
% ln -sf ~/.m2/repository/com/base2/kagura/war-rest/1.2/war-rest-1.2.war apache-tomee-jaxrs-1.6.0/webapps/
% cd apache-tomee-jaxrs-1.6.0
% ./bin/startup.sh

When it starts visit:

http://localhost:8080/war-rest-1.2/

Reports are embedded into the war.

I recommend you use JNDI, you can find out how to setup JNDI data sources in tomee here: http://tomee.apache.org/configuring-datasources.html.
