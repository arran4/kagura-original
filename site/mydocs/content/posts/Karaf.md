---
title: "Karaf"
date: 2024-01-01T00:00:00Z
draft: false
---

# Karaf

Karaf[Updated Feb 11, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491545)*
![](https://kagura.atlassian.net/wiki/aa-avatar/dc5f354c-f0f1-4dfd-9438-bced84c4e622)

# Karaf

Feb 11, 2014[Cloud editor]()

The Kagura Karaf container is designed to work out of the box, with an accompanying HTTP server for the presentation layer. 

Out of the box Kagura's karaf container provides the core Kagura reporting REST services. It comes with an example "Authentication REST" backend. This is a sample REST end point for if you were to use REST configuration. The true power in the Kagura REST container is the flexible container that it runs in, allowing live changes to parameters for instance. It also allows for a central non-code system for changing configuration values, and bundling reports.

# Issues

There are a couple of issues with using the Karaf backend, mostly to do with [http://en.wikipedia.org/wiki/Same-origin_policy](http://en.wikipedia.org/wiki/Same-origin_policy) this can be overcome several ways such as a forward proxy, a by installing the appropriate headers into the http response, or by modifying Kagura.js to not validate. (May not work with all browsers.)

# Bundles and features

Everything is packaged by Kagura-assembly. The available features / bundles are:

What
--

Each authentication method introduces their own configuration options.

#### File Authentication

The file authentication mechanism depends on the com.base2.kagura.reportloc option to determine where to find the user.yaml and groups.yaml files. See [Setting up File Authentication Provider user and groups](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491535) for more details.

#### Rest Authentication

Rest Authentication relies on com.base2.kagura.rest.authUrl for the location of the rest service. The REST endpoint has to match the com.base2.kagura.services.exampleRestAuth.MyAuth object.

#### Hybrid Authentication

Hybrid authentication is for circumstances where you want one system (file) to provide the group and report mappings, and another (rest) to provide the group information.

### File storage Authentication

#### File Report Storage

Currently (1.4) the only supported mechanism for storing a file. It provides a report from a configuration as follows:

<reportName>/reportconf.yaml or <reportName>/reportconf.json

It depends on the com.base2.kagura.reportloc configuration option for the location. More information can be found here: [Building a basic JNDI/JDBC report](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491530)

## Other configuration

Some other configurations you may want to investigate are:

*

Karafs Jetty

# Configuration works

Configuration of the components in the kagura-camel bundle, is by providing Spring end-points on overridden getters and setters of 

# Custom authentication methods

You
 could add a custom spring bean in the kagura package into as it's own
bundle into the karaf container and refer to it via the configuration
file. However this is untested.Karaf
[Updated Feb 11, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491545)

Karaf
Feb 11, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491545/www.atlassian.com)

The Kagura Karaf container is designed to work out of the box, with an accompanying HTTP server for the presentation layer.

Out of the box Kagura's karaf container provides the core Kagura reporting REST services. It comes with an example "Authentication REST" backend. This is a sample REST end point for if you were to use REST configuration. The true power in the Kagura REST container is the flexible container that it runs in, allowing live changes to parameters for instance. It also allows for a central non-code system for changing configuration values, and bundling reports.
Issues

There are a couple of issues with using the Karaf backend, mostly to do with http://en.wikipedia.org/wiki/Same-origin_policy this can be overcome several ways such as a forward proxy, a by installing the appropriate headers into the http response, or by modifying Kagura.js to not validate. (May not work with all browsers.)
Bundles and features

Everything is packaged by Kagura-assembly. The available features / bundles are:

What

Type

Description

Kagura-Camel


Bundle


Provides the CXF REST end-point and camel backend. This is required for the bundle to run correctly.

Kagura-features


Feature


Provides the feature descriptor for the bundles for OSGI containers (Karaf) to use.

example-authrest


Bundle


A Camel backed CXF REST endpoint which provides an example REST Authentication backend. (An authentication option.)
Configuration

The Kagura Karaf requires only one Kagura configuraiton (Used by Kagura-camel) file. There are also a couple of customisations to the Karaf default configuration files you may want to consider.
Kagura-camel config

The default Kagura-camel configuration file can be found in etc/com.base2.kagura.services.camel.cfg the default configuration file (as of 1.4) looks like:
# Report location, must end with a /
com.base2.kagura.reportloc = ${project.basedir}/../kagura-camel/src/main/resources/TestReports/

#Export Limit:
com.base2.kagura.exportLimit=10000

# Change IP settings:
#kagura.camel.rest.ip=0.0.0.0
#kagura.camel.rest.port=8432

# Authentication configuration, these are just beans they need to inherit "AuthenticationProvider". For FILE type:
com.base2.kagura.authtype=fileAuthentication

# For Rest type; uses example rest auth provider substitute with own.  To load sample rest auth module use: karaf@root> features:install example-authrest
#com.base2.kagura.authtype=restAuthentication
#com.base2.kagura.rest.authUrl=http://localhost:8432/exampleAuthRest

# For a mixin of both;
#com.base2.kagura.authtype=hybridAuthentication
#com.base2.kagura.hybridAuth.userAuthenticator=rest
#com.base2.kagura.hybridAuth.groupAuthenticator=file

# If there are ever different report providers this is what you would modify. (Ie ZIP, GIT, S3... )
#com.base2.kagura.reportStorage=fileReportsProvider

All the options here have sane defaults, with the exception of the com.base2.kagura.authtype option. Which simply won't work if you don't use it.
Components

Config option

Default Value

Meaning

com.base2.kagura.reportloc


/TestReports/


This is where you can specify where the reports will be located. Usually etc/Reports is recommended as those are bundled with the package, if you are using a clone of the kagura-custom-assembly package. This is used by fileAuthenticator and fileReportStorage.

com.base2.kagura.exportLimit


10000


Sets the limit on exports, usually 10,000 is fine. however there are circumstances where you would want that to be larger.

kagura.camel.rest.ip


0.0.0.0


IP Address for the rest service to listen on.

kagura.camel.rest.port


8432


Port for the rest services to listen on.

com.base2.kagura.authtype


fileAuthentication


The file authentication selection mechanism.

com.base2.kagura.rest.authUrl
com.base2.kagura.hybridAuth.userAuthenticator
com.base2.kagura.hybridAuth.groupAuthenticator





These depend on the authentication mechanism selected.

com.base2.kagura.reportStorage


fileReportsProvider


The report storage authentication.
Authentication

Each authentication method introduces their own configuration options.
File Authentication

The file authentication mechanism depends on the com.base2.kagura.reportloc option to determine where to find the user.yaml and groups.yaml files. See [Setting up File Authentication Provider user and groups](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491535) for more details.
Rest Authentication

Rest Authentication relies on com.base2.kagura.rest.authUrl for the location of the rest service. The REST endpoint has to match the com.base2.kagura.services.exampleRestAuth.MyAuth object.
Hybrid Authentication

Hybrid authentication is for circumstances where you want one system (file) to provide the group and report mappings, and another (rest) to provide the group information.
File storage Authentication
File Report Storage

Currently (1.4) the only supported mechanism for storing a file. It provides a report from a configuration as follows:

/reportconf.yaml or /reportconf.json

It depends on the com.base2.kagura.reportloc configuration option for the location. More information can be found here: [Building a basic JNDI/JDBC report](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491530)
Other configuration

Some other configurations you may want to investigate are:

    Karafs Jetty

Configuration works

Configuration of the components in the kagura-camel bundle, is by providing Spring end-points on overridden getters and setters of
Custom authentication methods
You could add a custom spring bean in the kagura package into as it's own bundle into the karaf container and refer to it via the configuration file. However this is untested.