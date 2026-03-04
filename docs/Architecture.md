# Architecture

# Architecture

Jan 09, 2014[Cloud editor]()* [Library first](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Library-first)
* [Components](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Components)* [Kagura API / Report Core](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Kagura-API-%2F-Report-Core)* [Core components](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Core-components)* [Storage Providers](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Storage-Providers)
* [Parameter Types](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Parameter-Types)
* [Connectors & Freemarker](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Connectors-%26-Freemarker)
* [Configuration Model](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Configuration-Model)

* [Helper Components](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Helper-Components)* [The "Export Handler" ](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#The-%22Export-Handler%22)
* [Authentication Providers & Model](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Authentication-Providers-%26-Model)

* [Karaf](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Karaf)* [Camel / CXF routes Bundle](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Camel-%2F-CXF-routes-Bundle)
* [Assembly](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Assembly)
* [Features](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Features)
* [Example Authrest](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Example-Authrest)

* [Javascript / Jetty](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Javascript-%2F-Jetty)
* [Resources](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Resources)
* [Rest-API](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Rest-API)

# Library first

Kagura is a library first. Which means it was designed to be modular and self contained. It also provides minimal constraints in the way that you can use it, and is intended to be embedded into a larger scale application, such as some of the example components demonstrate. Kagura is designed to be simple in the way you can use it. Many of the components provided inside the core of kagura are optional and provided as helps, examples or guidelines regarding the architecture you can make. Below are two production ready architecture diagrams, and one development architecture. All which work out of the box:

The Karaf / Camel configuration, the Kagura API is deployed as it's own bundle.

![](https://media-cdn.atlassian.com/file/48d98629-7a8c-41fb-b10c-8578a21d3fbf/image/cdn?allowAnimated=true&client=8efbe0a5-7e37-4200-a92e-6e123097da40&collection=contentId-491540&height=125&max-age=2592000&mode=full-fit&source=mediaCard&token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI4ZWZiZTBhNS03ZTM3LTQyMDAtYTkyZS02ZTEyMzA5N2RhNDAiLCJhY2Nlc3MiOnsidXJuOmZpbGVzdG9yZTpjb2xsZWN0aW9uOmNvbnRlbnRJZC00OTE1NDAiOlsicmVhZCJdfSwiZXhwIjoxNzcyNTg3NTg2LCJuYmYiOjE3NzI1ODQ3MDYsImh0dHBzOi8vaWQuYXRsYXNzaWFuLmNvbS9hcHBBY2NyZWRpdGVkIjpmYWxzZX0.oBLxV6MUI7ofiUf3BlTyFLGD1BbSKTwkIcEaDn18IBQ&width=731)

The War file configuration: (Using rest, you can also use JSF.)

![](https://media-cdn.atlassian.com/file/f5f1affc-f9fe-4bf7-8554-384720ae3306/image/cdn?allowAnimated=true&client=8efbe0a5-7e37-4200-a92e-6e123097da40&collection=contentId-491540&height=125&max-age=2592000&mode=full-fit&source=mediaCard&token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI4ZWZiZTBhNS03ZTM3LTQyMDAtYTkyZS02ZTEyMzA5N2RhNDAiLCJhY2Nlc3MiOnsidXJuOmZpbGVzdG9yZTpjb2xsZWN0aW9uOmNvbnRlbnRJZC00OTE1NDAiOlsicmVhZCJdfSwiZXhwIjoxNzcyNTg3NTg2LCJuYmYiOjE3NzI1ODQ3MDYsImh0dHBzOi8vaWQuYXRsYXNzaWFuLmNvbS9hcHBBY2NyZWRpdGVkIjpmYWxzZX0.oBLxV6MUI7ofiUf3BlTyFLGD1BbSKTwkIcEaDn18IBQ&width=729)

When developing you may want a quick version to start while you edit, during that you can use the maven configuration;

![](https://media-cdn.atlassian.com/file/dcdcdf7c-c331-4b1c-af27-77c595363779/image/cdn?allowAnimated=true&client=8efbe0a5-7e37-4200-a92e-6e123097da40&collection=contentId-491540&height=125&max-age=2592000&mode=full-fit&source=mediaCard&token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI4ZWZiZTBhNS03ZTM3LTQyMDAtYTkyZS02ZTEyMzA5N2RhNDAiLCJhY2Nlc3MiOnsidXJuOmZpbGVzdG9yZTpjb2xsZWN0aW9uOmNvbnRlbnRJZC00OTE1NDAiOlsicmVhZCJdfSwiZXhwIjoxNzcyNTg3NTg2LCJuYmYiOjE3NzI1ODQ3MDYsImh0dHBzOi8vaWQuYXRsYXNzaWFuLmNvbS9hcHBBY2NyZWRpdGVkIjpmYWxzZX0.oBLxV6MUI7ofiUf3BlTyFLGD1BbSKTwkIcEaDn18IBQ&width=777)

If you are not interested in taking advantage of OSGI, I would recommend sticking to the WAR configuration.

# Components

The components that make up the current (as of writing) Kagura source tree:

*

Kagura API / report-core

*

Karaf

*

Camel / CXF routes

*

Assembly

*

Features

*

Example-Authrest

*

Javascript / Jetty

*

Resources

*

Rest-API

More on each component below:

## Kagura API / Report Core

This is the heart of the program where all the major functionality takes place. It has several components to it:

*

Core components

*

Storage Providers

*

Parameter Types

*

Connectors

*

Freemarker

*

Configuration Model

*

Helper components

*

Authentication Providers & Model

*

Export Handler

The Rest-API component technically belongs in Helper components but it is separated to avoid increasing the dependencies of the reporting-core, and it's provided as a guideline only.

### Core components

The core components are components which it would be hard to use Kagura without, and it isn't recommended to do so. They are all components essential in providing the basic functionality of reports to end users. (Which is the [aim of Kagura](https://kagura.atlassian.net/wiki/spaces/KGR/overview#KaguraHome-Aim).)

The components are as follows:

#### Storage Providers

You are required to use one report provider when you use Kagura in the "typical" sense. Storage providers simply put, the component which lists, and provides the report configuration for the rest of the application. As of writing there are 3 main ones:

*

File Report Provider

*

Super File Report Provider

*

Cached Report Provider

Other possible report storage engines which I would like to see:

*

Tar (ziped / gzip / bzip'd?) Report Provider

*

Git Report Provider (I did have one, but I disabled it as it required physical disk space.)

*

S3 Report Provider

*

DB Report Provider

*

NoSQL Report Provider (Design had this in mind.)

*

REST Report Provider

Note, Kagura does not preform any write operations to the disk.

The File Report Provider, expects to see a layout such as this:

*

<report1name directory>

*

reportconf.yaml

*

<report2name directory>

*

reportconf.yaml

And so forth.

Super File Report, is essentially a "one file" version of the above. So it uses a single YAML file for all the reports.

Cached Report Provider, you actually specify a different report provider and it will cache the report configuration.

#### Parameter Types

Parameter types allow for a mapping of Report Parameters to native Java data types. The names of the parameters are also used by Kagura.js to determine what type of value the parameter is expecting. However you can use "extraOptions" instead. Some examples of types are:

Parameter Type
--

Connectors are the components which allow you to specify the data source for a report. In some of the reports Kagura uses Freemarker as a macro driver. There are 4 data sources for reports.:

*

Fake

*

Groovy

*

JDBC & Freemarker

*

JNDI & Freemarker

Like all Kagura components, if you were to crack open the source, you would find that the components are rather lite weight.

Fake, simply allows you to build in the data into the application as a list of hashtables. It has a VERY limited capacity for filtering parameters. This specifically exists for the purpose of demos.

Groovy, allows you to use a Groovy script to produce the desired output, this can be used for a demo, or it can be used to connect to a unique data source, or summarise information.

JDBC / JNDI, these are standard SQL scripts that are executed safely using the prepareStatement provided you use the Freemarker pre-processor correctly. The preprocessor allows for much greater flexibility over the standard query mechanisms, and is required to apply parameters to the system.

#### Configuration Model

This is where the configuration is "stored" in memory. It used to deserialize the YAML and is partially passed up the chain (such as eventually to Kagura.js)

### Helper Components

The "helper" components are there to provide additional functionality.

#### The "Export Handler"

Is a useful API for turning Kagura output in to reports, CVS, PDF or Excel documents. It's simple and easily substitutable.

#### Authentication Providers & Model

Kagura provides model and sample and simple mechanisms for providing user and group information to the middleware. Kagura does not do any user and password validation itself. These are provided as sample only. In a production environment please consider using a different authentication method. Such as JAAS.

## Karaf

[http://karaf.apache.org/](http://karaf.apache.org/)

### Camel / CXF routes Bundle

This component provides the REST front end and the Authentication for the Karaf based middleware. It uses the REST definition provided by Rest-API (documented in a later chapter.) Then uses CXF and Camel, to route the contents to various beans to carry out the business logic and API calls. To allow for configuration via the configuration files in the assembly, we also inherit the Authentication Providers and Storage Providers in such away to allow them to be beans inside the Spring container, and have their properties auto populated by Spring. 

The operations is simple, first check or provide authentication, then route the calls to the correct bean function to get the desired value.

### Assembly

This builds a zip or tar.gz that contains a complete fully functional setup for the REST component. Inside you will find some example reports in etc/reports as well as all the configuration you need in etc. It also builds a development version, which you can run in the target directory and it's configuration files are configured to point to the reports in the source, rather than in the build directory. This can speed up development efforts. 

### Features

This generates and installs the pom and features file into maven. Which is required to correctly import all the resources into an OSGI container.

### Example Authrest

This is an example authentication provider for REST. Essentially there is a RestAuthenticationProvider, which offloads the authentication process to a rest end point. This is an example implementation of the end point.

## Javascript / Jetty

This is where the example HTML, CSS and Javascript live. You can find Kagura.js here which is a javascript file which handles does the jQuery/ajax work for the rest communication. The entire design is to reduce page loads and the amount of data communicated, to allow for a fast and speedy load. It is not framework heavy to allow for people in the future to customise the end page. It is only an example, and not prescribed.

## Resources

This stores the favicon.

## Rest-API

The
 Rest-API component provides a template for the REST results. It
contains a model that responses from REST backends should provide as
well as a javax.rs annotated interface which your project can inherit to
 guarantee Kagura.js compatibility.Architecture
Jan 09, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/www.atlassian.com)

    [Library first](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Library-first)
    [Components](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Components)
        [Kagura API / Report Core](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Kagura-API-%2F-Report-Core)
            [Core components](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Core-components)
                [Storage Providers](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Storage-Providers)
                [Parameter Types](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Parameter-Types)
                [Connectors & Freemarker](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Connectors-%26-Freemarker)
                [Configuration Model](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Configuration-Model)
            [Helper Components](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Helper-Components)
                [The "Export Handler" ](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#The-%22Export-Handler%22)
                [Authentication Providers & Model](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Authentication-Providers-%26-Model)
        [Karaf](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Karaf)
            [Camel / CXF routes Bundle](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Camel-%2F-CXF-routes-Bundle)
            [Assembly](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Assembly)
            [Features](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Features)
            [Example Authrest](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Example-Authrest)
        [Javascript / Jetty](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Javascript-%2F-Jetty)
        [Resources](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Resources)
        [Rest-API](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540/Architecture#Rest-API)

Library first

Kagura is a library first. Which means it was designed to be modular and self contained. It also provides minimal constraints in the way that you can use it, and is intended to be embedded into a larger scale application, such as some of the example components demonstrate. Kagura is designed to be simple in the way you can use it. Many of the components provided inside the core of kagura are optional and provided as helps, examples or guidelines regarding the architecture you can make. Below are two production ready architecture diagrams, and one development architecture. All which work out of the box:

The Karaf / Camel configuration, the Kagura API is deployed as it's own bundle.

The War file configuration: (Using rest, you can also use JSF.)

When developing you may want a quick version to start while you edit, during that you can use the maven configuration;

If you are not interested in taking advantage of OSGI, I would recommend sticking to the WAR configuration.
Components

The components that make up the current (as of writing) Kagura source tree:

    Kagura API / report-core

    Karaf

        Camel / CXF routes

        Assembly

        Features

        Example-Authrest

    Javascript / Jetty

    Resources

    Rest-API

More on each component below:
Kagura API / Report Core

This is the heart of the program where all the major functionality takes place. It has several components to it:

    Core components

        Storage Providers

        Parameter Types

        Connectors

            Freemarker

        Configuration Model

    Helper components

        Authentication Providers & Model

        Export Handler

The Rest-API component technically belongs in Helper components but it is separated to avoid increasing the dependencies of the reporting-core, and it's provided as a guideline only.
Core components

The core components are components which it would be hard to use Kagura without, and it isn't recommended to do so. They are all components essential in providing the basic functionality of reports to end users. (Which is the [aim of Kagura](https://kagura.atlassian.net/wiki/spaces/KGR/overview#KaguraHome-Aim).)

The components are as follows:
Storage Providers

You are required to use one report provider when you use Kagura in the "typical" sense. Storage providers simply put, the component which lists, and provides the report configuration for the rest of the application. As of writing there are 3 main ones:

    File Report Provider

    Super File Report Provider

    Cached Report Provider

Other possible report storage engines which I would like to see:

    Tar (ziped / gzip / bzip'd?) Report Provider

    Git Report Provider (I did have one, but I disabled it as it required physical disk space.)

    S3 Report Provider

    DB Report Provider

    NoSQL Report Provider (Design had this in mind.)

    REST Report Provider

Note, Kagura does not preform any write operations to the disk.

The File Report Provider, expects to see a layout such as this:



        reportconf.yaml



        reportconf.yaml

And so forth.

Super File Report, is essentially a "one file" version of the above. So it uses a single YAML file for all the reports.

Cached Report Provider, you actually specify a different report provider and it will cache the report configuration.
Parameter Types

Parameter types allow for a mapping of Report Parameters to native Java data types. The names of the parameters are also used by Kagura.js to determine what type of value the parameter is expecting. However you can use "extraOptions" instead. Some examples of types are:

Parameter Type

Java Type

BooleanParamConfig


Boolean

DateParamConfig


Date (yyyy/mm/dd only)

DateTimeParamConfig


Date

MultiParamConfig


List

SingleParamConfig


String

Kagura uses PropertyUtils from the Apache Commons Beans Library to map values in to this. Kagura also installs in some circumstances the two converters into "ConvertUtils" dateUtils: "yyyy-MM-dd", "yyyy-MM-dd [hh:mm:ss](http://hhmmss/)" (See [ISO8601](http://xkcd.com/1179/).)

Parameters such as a Combo and Listbox (Single selection and multi selection) allow "Datasources" for the list of options these data sources can be:

    A plain old list

    A Groovy script

    A SQL script (a sub-report.)

Connectors & Freemarker

Connectors are the components which allow you to specify the data source for a report. In some of the reports Kagura uses Freemarker as a macro driver. There are 4 data sources for reports.:

    Fake

    Groovy

    JDBC & Freemarker

    JNDI & Freemarker

Like all Kagura components, if you were to crack open the source, you would find that the components are rather lite weight.

Fake, simply allows you to build in the data into the application as a list of hashtables. It has a VERY limited capacity for filtering parameters. This specifically exists for the purpose of demos.

Groovy, allows you to use a Groovy script to produce the desired output, this can be used for a demo, or it can be used to connect to a unique data source, or summarise information.

JDBC / JNDI, these are standard SQL scripts that are executed safely using the prepareStatement provided you use the Freemarker pre-processor correctly. The preprocessor allows for much greater flexibility over the standard query mechanisms, and is required to apply parameters to the system.
Configuration Model

This is where the configuration is "stored" in memory. It used to deserialize the YAML and is partially passed up the chain (such as eventually to Kagura.js)
Helper Components

The "helper" components are there to provide additional functionality.
The "Export Handler"

Is a useful API for turning Kagura output in to reports, CVS, PDF or Excel documents. It's simple and easily substitutable.
Authentication Providers & Model

Kagura provides model and sample and simple mechanisms for providing user and group information to the middleware. Kagura does not do any user and password validation itself. These are provided as sample only. In a production environment please consider using a different authentication method. Such as JAAS.
Karaf

http://karaf.apache.org/
Camel / CXF routes Bundle

This component provides the REST front end and the Authentication for the Karaf based middleware. It uses the REST definition provided by Rest-API (documented in a later chapter.) Then uses CXF and Camel, to route the contents to various beans to carry out the business logic and API calls. To allow for configuration via the configuration files in the assembly, we also inherit the Authentication Providers and Storage Providers in such away to allow them to be beans inside the Spring container, and have their properties auto populated by Spring.

The operations is simple, first check or provide authentication, then route the calls to the correct bean function to get the desired value.
Assembly

This builds a zip or tar.gz that contains a complete fully functional setup for the REST component. Inside you will find some example reports in etc/reports as well as all the configuration you need in etc. It also builds a development version, which you can run in the target directory and it's configuration files are configured to point to the reports in the source, rather than in the build directory. This can speed up development efforts.
Features

This generates and installs the pom and features file into maven. Which is required to correctly import all the resources into an OSGI container.
Example Authrest

This is an example authentication provider for REST. Essentially there is a RestAuthenticationProvider, which offloads the authentication process to a rest end point. This is an example implementation of the end point.
Javascript / Jetty

This is where the example HTML, CSS and Javascript live. You can find Kagura.js here which is a javascript file which handles does the jQuery/ajax work for the rest communication. The entire design is to reduce page loads and the amount of data communicated, to allow for a fast and speedy load. It is not framework heavy to allow for people in the future to customise the end page. It is only an example, and not prescribed.
Resources

This stores the favicon.
Rest-API
The Rest-API component provides a template for the REST results. It contains a model that responses from REST backends should provide as well as a javax.rs annotated interface which your project can inherit to guarantee Kagura.js compatibility.