# JNDI JDBC Freemarker Report

JNDI/JDBC Freemarker Report[Updated Jan 21, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491537)*
![](https://kagura.atlassian.net/wiki/aa-avatar/f408f89e-1d4e-4e04-a729-50f48ef2625b)

# JNDI/JDBC Freemarker Report

Jan 21, 2014[Cloud editor]()

This is the most likely type of report you will require. It's a report which takes it's data from a data source such as MySQL and returns the output. (Not all reports do this.) 

# Prerequisites

For this to work you need several things:

*

What parameters you are going to use, and how to query them

*

A Query

*

Column configuration - if required

*

Database connection

*

Jndi

*

JNDI name

*

JDBC

*

URI

*

Username

*

Password

# Intro

These need to be placed in a yaml file placed in the following location:

```
reports/<reportid>/reportconf.yaml
```

Please note the reportId is different to the report name.

A standard report configuration has a couple of required elements regardless of type:

*

paramConfig

*

columns (optional, defaults to everything)

*

extraOptions (optional, no default)

*

pageLimit (optional, default changes depending on client)

Additionally JDBC and JNDI both require:

*

sql

*

presqlsql (Optional)

*

postsqlsql (Optional)

Also they have their own options on top of this:

JNDI:

*

jndi - the JNDI name.

JDBC

*

jdbc - The JDBC URI

*

username

*

password

*

classLoaderPath - For when you need to insure that a JDBC Driver has been registered

# Example

A basic configuration can look like any of the following:

JNDI which lists the questions for a date range:

##### answersDB/reportconf.yaml

type: "JNDI"
jndi: "dataSource"
sql: "SELECT question, answer FROM Answers <@where><@clause render=param.dateOfQuestion?has_content>(Date specific query here)</@clause></@where>"
paramConfig:
  - type: DateTime
    name: "Date of Question"
    help: "The date the question was asked."
    placeholder: "Any"
columns:
  - name: Question
    extraOptions:
      styleType: text
  - name: Answer
    extraOptions:
      styleType: numbers
extraOptions:
  image: image1.png

```
  reportName: "Answers DB"
```

A JDBC example report which queries the answer to a specific question:

##### answerDB/reportconf.yaml

type: "JDBC"
classLoaderPath: "com.mysql.jdbc.Driver"
jdbc: "jdbc:mysql://localhost:3306/test"
username: "test"
password: "test" 
sql: "SELECT question, answer FROM Answers <@where><@clause render=param.question?has_content>question=${method.value(param.question)}</@clause></@where>"
paramConfig:
  - type: String
    name: "Question"
    help: "Enter the exact question used."
    placeholder: "All"
columns:
  - name: Question
    extraOptions:
      styleType: text
  - name: Answer
    extraOptions:
      styleType: numbers
extraOptions:
  image: image1.png

```
  reportName: "Answer DB"
```

As you notice in the examples above there is a markup language. This is freemarker with an additional configuration. 

# Components

The JDBC and JNDI report types each have their own set of fields; with one compulsory and a couple optional. 

JNDI requires a JNDI string and that's all.

JDBC can take:

Field
--

The where tag can be used as follows: (These are from the unit tests.)

```
SELECT * FROM table WHERE columnB=${method.value(param.test)} <@limit />
```

Renders:

```
SELECT * FROM table WHERE columnB=?  LIMIT 20 OFFSET 0
```

<@where> <@clause>

Unlike <@limit /> this isn't require and provided for your benefit only.

#### Simple:

The where tag can be used as follows: (These are from the unit tests.)

```
SELECT * FROM table <@where><@clause render=true>columnB=${method.value(param.test)}</@clause></@where> <@limit />
```

Renders:

```
SELECT * FROM table  WHERE columnB=?  LIMIT 20 OFFSET 0
```

#### Multiple

SELECT * FROM table " +
 <@where>
  <@clause render=true>columnB=${method.value(param.test)}</@clause>
  <@clause render=false>columnC=${method.value(param.test)}</@clause>
  <@clause render=param.test='ParameterOutput'>columnD=${method.value(param.test)}</@clause>
 </@where>

```
<@limit />
```

Renders:

```
SELECT * FROM table  WHERE columnB=? AND columnD=?  LIMIT 20 OFFSET 0
```

#### Nested

SELECT * FROM table
<@where>
 <@where type='or'>
  <@clause render=true>columnB=${method.value(param.test)}</@clause>
  <@clause render=false>columnC=${method.value(param.test)}</@clause>
 </@where>
 <@where>
  <@clause render=false>columnE=${method.value(param.test)}</@clause>
  <@clause render=false>columnF=${method.value(param.test)}</@clause>
 </@where>
 <@where type='and'>
  <@clause render=true>columnG=${method.value(param.test)}</@clause>
  <@clause render=true>columnH=${method.value(param.test)}</@clause>
 </@where>
 <@where type='or'>
  <@clause render=true>columnJ=${method.value(param.test)}</@clause>
  <@clause render=true>columnK=${method.value(param.test)}</@clause>
 </@where>
 <@clause render=param.test='ParameterOutput'>columnD=${method.value(param.test)}</@clause>
</@where>

```
<@limit />
```

Returns:

```
SELECT * FROM table  WHERE columnB=? AND columnG=? AND columnH=? AND (columnJ=? OR columnK=?) AND columnD=? LIMIT 20 OFFSET 0
```

Notice how:

*

it removes brackets from queries without the need

*

that where clauses can be nested

*

The were clauses denote usage

*

<@where> tag adds the WHERE token

#### Render=

You can use any Boolean expression you like in the render tag, however I recommend sticking to simple expressions particularly: param.name?has_content. Here are some examples:

<@clause render=param.startDate?has_content>InspectionDate >= ${method.value(param.startDate?date)} </@clause>
<@clause render=param.test='ParameterOutput'>columnD=${method.value(param.test)}</@clause>

```
<@clause render=param.Employee?has_content>username=${method.value(param.Employee)}</@clause>
```

#### Notes

If can always have multiple clauses doing similar or the same thing, try keep your expressions themselves simple, freemarker can quickly become unreadable if you try do too much.

### method.value()

method.value() is highly recommended on any external variable and some internal ones (extra.*). It properly handles escape characters and other issues you may encounter taking dynamic strings and using them inside a SQL statement. Some examples of ways you can use method.value():

```
SELECT * FROM table WHERE columnB=${method.value(param.test)} <@limit />
```

When the code runs, the ${method.value(param.test)} is replaced with a ?, and the value inside the function is put as part of the prepareStatement query. You can put anything as a query, including a constant string:

```
SELECT * FROM table WHERE columnB=${method.value("That's")} <@limit />
```

#### Notes

Values will be converted to strings inside freemarker if you are using dates or some other type, you will have to convert it as you use it, an example of that is:

      <@clause render=param.startDate?has_content>date >= ${method.value(param.startDate?date)} </@clause>

```
      <@clause render=param.endDate?has_content>date < ${method.value(param.endDate?date)} </@clause>
```

### method.values()

method.values() has been with kagura since version 1.3. It supports an array / freemarker list as an input, and it allows you to use "IN". It's designed primarily for use with ManyCombo parameter types. The usage is as below:

```
SELECT * FROM table WHERE columnB IN ${method.values(param.test)} <@limit />
```

### param.* and extra.*

These act like standard Freemarker variables, and you can use them in expressions. You can insert them into the SQL script by wrapping them in ${ .. } however, I would strongly recommend the use of method.value(...) when outputting the values into the SQL. 

# Finally

Remember,
 the user needs to be setup with the Authentication Provider, or with
the middleware you are using in order to see the reports.JNDI/JDBC Freemarker Report
[Updated Jan 21, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491537)

JNDI/JDBC Freemarker Report
Jan 21, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491537/www.atlassian.com)

This is the most likely type of report you will require. It's a report which takes it's data from a data source such as MySQL and returns the output. (Not all reports do this.)
Prerequisites

For this to work you need several things:

    What parameters you are going to use, and how to query them

    A Query

    Column configuration - if required

    Database connection

        Jndi

            JNDI name

        JDBC

            URI

            Username

            Password

Intro

These need to be placed in a yaml file placed in the following location:
reports//reportconf.yaml

Please note the reportId is different to the report name.

A standard report configuration has a couple of required elements regardless of type:

    paramConfig

    columns (optional, defaults to everything)

    extraOptions (optional, no default)

    pageLimit (optional, default changes depending on client)

Additionally JDBC and JNDI both require:

    sql

    presqlsql (Optional)

    postsqlsql (Optional)

Also they have their own options on top of this:

JNDI:

    jndi - the JNDI name.

JDBC

    jdbc - The JDBC URI

    username

    password

    classLoaderPath - For when you need to insure that a JDBC Driver has been registered

Example

A basic configuration can look like any of the following:

JNDI which lists the questions for a date range:
answersDB/reportconf.yaml
type: "JNDI"
jndi: "dataSource"
sql: "SELECT question, answer FROM Answers <@where><@clause render=param.dateOfQuestion?has_content>(Date specific query here)"
paramConfig:
  - type: DateTime
    name: "Date of Question"
    help: "The date the question was asked."
    placeholder: "Any"
columns:
  - name: Question
    extraOptions:
      styleType: text
  - name: Answer
    extraOptions:
      styleType: numbers
extraOptions:
  image: image1.png
  reportName: "Answers DB"

A JDBC example report which queries the answer to a specific question:
answerDB/reportconf.yaml
type: "JDBC"
classLoaderPath: "com.mysql.jdbc.Driver"
jdbc: "jdbc:mysql://localhost:3306/test"
username: "test"
password: "test"
sql: "SELECT question, answer FROM Answers <@where><@clause render=param.question?has_content>question=${method.value(param.question)}"
paramConfig:
  - type: String
    name: "Question"
    help: "Enter the exact question used."
    placeholder: "All"
columns:
  - name: Question
    extraOptions:
      styleType: text
  - name: Answer
    extraOptions:
      styleType: numbers
extraOptions:
  image: image1.png
  reportName: "Answer DB"

As you notice in the examples above there is a markup language. This is freemarker with an additional configuration.
Components

The JDBC and JNDI report types each have their own set of fields; with one compulsory and a couple optional.

JNDI requires a JNDI string and that's all.

JDBC can take:

Field

Optional

Meaning

Example

jdbc


no


The JDBC Connector URL


jdbc:mysql://localhost/test

classLoaderPath


yes


The class path of the driver loader.

This is required to load the driver into the system to run the report.


Mysql:

com.mysql.jdbc.Driver

username


no - recommended


The username to connect to the database with




password


no


The password to connect to the database with




In addition to the specific components there are a couple of shared fields, both the JDBC and the JNDI connectors provide:

Field

Optional

Meaning

Example

presqlsql


Yes


This is a batch SQL query that allows you to setup the database if required before running the report. Should be avoided. Freemarker markup is supported.


"UPDATE counts SET count=(SELECT COUNT( * ) FROM source WHERE counts.type=source.type)"

postsqlsql


Yes


A batch query you can run after the SQL. Useful for cleaning up after a query has been run. Should be avoided. Freemarker markup is supported.


"DELETE FROM cache"

sql


No


The SQL query to run, this must be a single query (or return a result) and it's results will be used to generate the report. It's run in-between the presqlsql and postsqlsql. Freemarker markup is supported.


"SELECT * FROM counts"
Freemarker

Kagura uses Freemarker with the JDBC and JNDI reports as a sort of pre-processor to be run with reports, it is responsible for inserting values into the query, making the report responsive to environment and allowing a greater degree of flexibility. Kagura as of writing uses Freemarker 2.3.19.
Some examples of Freemarker usage

It can be used to make conditional parameters: (However see below we have a better way of using it.)
SELECT id, active, username FROM User <#if param.User?has_content>WHERE username=${method.value(param.User)}<@limit />;

This would select all users, unless a user has been specified as a parameter, if it has it makes it a where clause.

Doing evaluation of equations:
SELECT result FROM Lookup WHERE percent>=${param.percent / 100} and percent<${(param.percent+10) / 100}<@limit />;

Type conversions:
SELECT * FROM log WHERE entryDate >= ${method.value(param.startDate?date)} <@limit />

Generate SQL from lists: (Contrived, but an example.)
SELECT date AS 'Date'
<#list ["uniqueProperty1", "uniqueProperty2", "uniqueProperty3", "uniqueProperty4"] as x>
`${x}` AS '${x}'

FROM dynamicTable WHERE category IS NULL
<#list param.selectedValues as x>
OR category=`${x}`

<@limit />

Switching:
SELECT invoiceNumber, invoicePrice FROM invoice WHERE
<#switch param.mode>
  <#case "Test">
    category="TE"
  <#case "Customer 1">
    category="ON"
  <#default>
    category IS NOT NULL

<@limit />

Converting user input:
${param.fullname?upper_case}
${param.fullname?capitalize}
${param.firstname?cap_first}
${param.firstname?trim}
${param.fullname?split[0]}
${param.fullname?split(", ")?reverse?join(" "}

Formatting and pattern  based conversions:
${param.fullname?date("MM/dd/yyyy")} - Convert a string date to a freemarker date.
${param.lastUpdated?string("yyyy-MM-dd HH:mm:ss zzzz"} - Date to string.
${param.typeA?string("yes", "no")} - Boolean to string

Ideas:

    Using macros to avoid repetition: http://freemarker.org/docs/ref_directive_macro.html

    Using functions to generate values: http://freemarker.org/docs/ref_directive_function.html

    Throwing an exception: http://freemarker.org/docs/ref_directive_stop.html - Such as overly generic parameters selection to save loading times.

Customisations of Freemarker

As well as the impressive array of inbuilt functions Freemarker provides, Kagura provides some additional custom tags on top:

Tag

Required, limits

Description

<@limit />


yes, exactly once.


This is the only compulsory field, every query inside the "sql" parameter must have this value.

This then is substituted for a LIMIT and OFFSET statement. It currently accepts 1 parameter:

    sql

This parameter accepts 2 values as of writing:

    mysql

    postgres

More to come. It defaults to "mysql."

Every query must have this, even if you do not expect the values to reach that value.

If for some reason you want a report to return everythingm, you can always comment it out using –. But that isn't recommended.

<@where>

<@where type=AND>

<@where type=OR>



no, unlimited use


This is a helper function, it's similar to a JOIN however it only accepts the children: @where or @clause.

By default it acts as an AND operation, however you can overwrite that behaviour by specifying the parameter: "type" with the value "OR".

It includes the " WHERE " component of the SQL query if required.

It can be nested. See below.

<@clause render=...><@clause>

<@caluse>


no, unlimited use


This allows you to specify a clause in a WHERE statement.

There is an optional boolean expression in the "render=" statement, if the expression returns false the clause does not get added.

Inside the clause you should put a single SQL where expression.

If you want to create complicated logic you can nest where clauses to build up a tree.

method.value(var1)


no, unlimited use


This is a function, usually called inside ${ ... } that inserts a parameter into the query using the injection safe connection.prepareStatement() method.

I recommend that you insert ALL values this way which might be susceptible to being modified, you can even run it on your own values.

It returns a single ?, this should be used directly in the query as you would usually use it.

See below for usage.

method.values(var1)


no, unlimited use


Like above, however inserts an array safely. Since version 1.3.

extra.*


no, unlimited use


These are constants passed from the front-end, middleware or Kagura itself. These help make the application aware. You use use them as you would parameters; ie:

${extra.time}

These should be wrapped in method.value() if being used in the query.

These are called "extraOptions" elsewhere in Kagura.

param.*


no, unlimited use


These are values passed from the end user, usually via the website.

These values should always be wrapped in method.value(), otherwise your report server / application is subject to injection attack.
<@limit />

The where tag can be used as follows: (These are from the unit tests.)
SELECT * FROM table WHERE columnB=${method.value(param.test)} <@limit />

Renders:
SELECT * FROM table WHERE columnB=?  LIMIT 20 OFFSET 0

<@where> <@clause>

Unlike <@limit /> this isn't require and provided for your benefit only.
Simple:

The where tag can be used as follows: (These are from the unit tests.)
SELECT * FROM table <@where><@clause render=true>columnB=${method.value(param.test)} <@limit />

Renders:
SELECT * FROM table  WHERE columnB=?  LIMIT 20 OFFSET 0
Multiple
SELECT * FROM table " +
 <@where>
  <@clause render=true>columnB=${method.value(param.test)}
  <@clause render=false>columnC=${method.value(param.test)}
  <@clause render=param.test='ParameterOutput'>columnD=${method.value(param.test)}

<@limit />

Renders:
SELECT * FROM table  WHERE columnB=? AND columnD=?  LIMIT 20 OFFSET 0
Nested
SELECT * FROM table
<@where>
 <@where type='or'>
  <@clause render=true>columnB=${method.value(param.test)}
  <@clause render=false>columnC=${method.value(param.test)}

 <@where>
  <@clause render=false>columnE=${method.value(param.test)}
  <@clause render=false>columnF=${method.value(param.test)}

 <@where type='and'>
  <@clause render=true>columnG=${method.value(param.test)}
  <@clause render=true>columnH=${method.value(param.test)}

 <@where type='or'>
  <@clause render=true>columnJ=${method.value(param.test)}
  <@clause render=true>columnK=${method.value(param.test)}

 <@clause render=param.test='ParameterOutput'>columnD=${method.value(param.test)}

<@limit />

Returns:
SELECT * FROM table  WHERE columnB=? AND columnG=? AND columnH=? AND (columnJ=? OR columnK=?) AND columnD=? LIMIT 20 OFFSET 0

Notice how:

    it removes brackets from queries without the need

    that where clauses can be nested

    The were clauses denote usage

    <@where> tag adds the WHERE token

Render=

You can use any Boolean expression you like in the render tag, however I recommend sticking to simple expressions particularly: param.name?has_content. Here are some examples:
<@clause render=param.startDate?has_content>InspectionDate >= ${method.value(param.startDate?date)}
<@clause render=param.test='ParameterOutput'>columnD=${method.value(param.test)}
<@clause render=param.Employee?has_content>username=${method.value(param.Employee)}
Notes

If can always have multiple clauses doing similar or the same thing, try keep your expressions themselves simple, freemarker can quickly become unreadable if you try do too much.
method.value()

method.value() is highly recommended on any external variable and some internal ones (extra.*). It properly handles escape characters and other issues you may encounter taking dynamic strings and using them inside a SQL statement. Some examples of ways you can use method.value():
SELECT * FROM table WHERE columnB=${method.value(param.test)} <@limit />

When the code runs, the ${method.value(param.test)} is replaced with a ?, and the value inside the function is put as part of the prepareStatement query. You can put anything as a query, including a constant string:
SELECT * FROM table WHERE columnB=${method.value("That's")} <@limit />
Notes

Values will be converted to strings inside freemarker if you are using dates or some other type, you will have to convert it as you use it, an example of that is:
      <@clause render=param.startDate?has_content>date >= ${method.value(param.startDate?date)}
      <@clause render=param.endDate?has_content>date < ${method.value(param.endDate?date)}
method.values()

method.values() has been with kagura since version 1.3. It supports an array / freemarker list as an input, and it allows you to use "IN". It's designed primarily for use with ManyCombo parameter types. The usage is as below:
SELECT * FROM table WHERE columnB IN ${method.values(param.test)} <@limit />
param.* and extra.*

These act like standard Freemarker variables, and you can use them in expressions. You can insert them into the SQL script by wrapping them in ${ .. } however, I would strongly recommend the use of method.value(...) when outputting the values into the SQL.
Finally
Remember, the user needs to be setup with the Authentication Provider, or with the middleware you are using in order to see the reports.