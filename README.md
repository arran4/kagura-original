# Kagura

<img align="right" width="200" src="example/javascript/src/main/webapp/fake1.png" alt="Kagura Image">

Kagurabi: a simple Java-based reporting / marshalling framework.

Kagura is an open-source project originally created by base2Services. It is a flexible, simple reporting platform that allows reports to be delivered quickly, without cumbersome designer UIs intended for a less technical market. The aim of Kagura is to produce a simple and fast reporting framework which can be used widely and customised heavily.

## Sample Configuration

Kagura allows configuring reports simply through YAML files. For instance:

```yaml
type: "Fake"
paramConfig:
  - type: String
    name: "An anonymous string"
    help: "Just put anything in"
    placeholder: "asdfsdaef"
columns:
  - name: test1
    extraOptions:
      styleType: text
  - name: test2
    extraOptions:
      styleType: numbers
rows:
  - test2: 899
    test1: "Unique Visitors"
  - test2: 234
    test1: String
extraOptions:
  displayPriority: 1
  reportName: "Fake sample 1"
  description: "Here be a report..."
```

## Project Structure

This repository contains the base POM of the Kagura project along with its modules:

* `shared` - Shared resources and utilities.
* `services` - Core services of the framework.
* `example` - Example usage of the framework.
* `war-rest` - REST API component.

## Building

To build the project and its modules, run the following Maven command from the root directory:

```bash
mvn clean install
```


## Documentation

The project documentation is hosted at: [https://arran4.github.io/kagura-original/](https://arran4.github.io/kagura-original/)

You can find more detailed information there, such as:
- [Architecture](https://arran4.github.io/kagura-original/docs/architecture/)
- [Configurations](https://arran4.github.io/kagura-original/docs/configurations/)
- [Use Cases](https://arran4.github.io/kagura-original/docs/use_cases/)
- [As a Library](https://arran4.github.io/kagura-original/docs/as_a_library/)
- [Introduction](https://arran4.github.io/kagura-original/docs/introduction/)
- [Building a basic JNDI/JDBC report](https://arran4.github.io/kagura-original/docs/building_a_basic_jndi_jdbc_report/)

### Why Kagura?

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
