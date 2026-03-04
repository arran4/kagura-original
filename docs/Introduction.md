# Introduction

Kagura is an open source project created by base2Services. It is a flexible simple reporting platform that allows reports to be delivered in a speedy manner, without cumbersome designer UIs intended for a less technical market.

This page contains more information on the project.

    [Screenshots](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491543)

    [Configurations](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491527)

    [Use Cases](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491529)

    [Architecture](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491540)

    [Getting Started](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491542)

    [Documentation](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491534)

    [Quick reference](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491532)

Aim

The aim of Kagura is to produce a simple and fast reporting framework, which can be used widely and customised heavily.
Why

Currently Reporting frameworks are either seriously under developed or proprietary in nature, and all of them are greatly ambitious in functionality and aim to be "usable" by non-technical users. As a result they often require the use of clunky UIs that require additional training and come with additional constraints. With Kagura we decided to go the opposite direction, and to provide a reporting framework which does as little as possible, and is to be used by bolting it on to other components, frameworks or larger projects.
Where to get the code

https://github.com/base2Services/kagura
That's great, but where can I download it?

https://kagurabi.ci.cloudbees.com/job/kagura-release/
Where to get a base template Karaf Kagura project

https://github.com/kagurabi/kagura-custom-assembly
Versions

Current version: 1.9
Maven properties config
<properties>
	<kagura.version>1.9</kagura.version>
</properties>


Maven Details

Do not add them all, add them depending on the component you want. For instance if you want to use the library component, for instance, add the "reporting-core"
Maven Libraries
        <dependency>
	        <groupId>com.kagurabi.shared</groupId>
		    <artifactId>reporting-core</artifactId>
		    <packaging>bundle</packaging>
        </dependency>
        <dependency>
	        <groupId>com.kagurabi.shared</groupId>
		    <artifactId>kagura-resources</artifactId>
		    <packaging>bundle</packaging>
        </dependency>
        <dependency>
		    <groupId>com.kagurabi.shared</groupId>
		    <artifactId>rest-api</artifactId>
		    <packaging>bundle</packaging>
        </dependency>
Example Karaf assembly
        <dependency>
            <groupId>com.kagurabi.services</groupId>
            <artifactId>kagura-assembly</artifactId>
            <version>${kagura.version}</version>
            <type>tar.gz</type>
        </dependency>
Example projects
        <dependency>
		    <groupId>com.kagurabi</groupId>
		    <artifactId>war-rest</artifactId>
		    <packaging>war</packaging>
        </dependency>
        <dependency>
            <groupId>com.kagurabi.example</groupId>
            <artifactId>kagura-example-javascript</artifactId>
            <version>${kagura.version}</version>
            <type>war</type>
        </dependency>
