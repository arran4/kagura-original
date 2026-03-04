---
title: "Report Providers"
date: 2026-03-04T00:58:36+00:00
draft: false
---
# Report Providers

Authentication Providers[Updated Feb 12, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491528)*

# Authentication Providers

Feb 12, 2014[Cloud editor]()

Authentications providers are an optional component or base component provided by the reporting-core. They are designed for 2 purposes:

*

Mapping Reports to Users (via groups)

*

Restricting the users of the system to users who are allowed to access the product

There are a couple of existing Authentication providers, and planned Authentication providers: (As of 1.4)

Authentication Provider
--

The file Authentication provider expects to see 2 files in the reports directory: users.yaml and groups.yaml. These are 2 very simply formatted files:

*

users.yaml contains a list of: username, password, and an array of group names

*

groups.yaml contains a list of: group names, with an array of report ids

Format:

##### users.yaml

- { username: "testuser", password: "testuserpass", groups: ["test reports"] }

```
- { username: "tu2", password: "tup2", groups: ["test reports2"] }
```

##### groups.yaml

- { groupname: "test reports", reports: [ "fake1" ] }

```
- { groupname: "test reports2", reports: [ "fake2", "groovytest" ] }
```

## Rest Authentication Provider

The rest authentication provider requires a URL to be provided. The REST Authentication provider will be superseded by a more secure version. This version is designed to be protected. Your REST end point that you point it at must export the following methods:

GET /echo
Parameters: 
- Query Param: message
Echos the message passed to it. Used to test the server.
 
GET /users
Parameters: None
Returns a list of users, with the username and the groups the user is in.
 
GET /groups
Parameters: None
Returns a list of groups and their reports
 
GET /login
Parameters:
- Post Data: JSON Map of "username" and "password"

```
Returns ok if the username and password are correct.
```

##### Java

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;
public interface MyAuth {
    @GET()
    @Path("/echo")
    public String echo(@QueryParam("message") @DefaultValue("No message= found.") String message);

    @GET()
    @Path("/users")
    public Object users();

    @GET()
    @Path("/groups")
    public Object groups();

    @POST()
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(Map<String,String> input);

```
}
```

# Hybrid Authentication Provider

Allows you to mix two authentication providers. You specify one for the groups and one for the user. Authentication Providers
[Updated Feb 12, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491528)

Authentication Providers
Feb 12, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491528/www.atlassian.com)

Authentications providers are an optional component or base component provided by the reporting-core. They are designed for 2 purposes:

    Mapping Reports to Users (via groups)

    Restricting the users of the system to users who are allowed to access the product

There are a couple of existing Authentication providers, and planned Authentication providers: (As of 1.4)

Authentication Provider

As of release

Description

Authentication Provider

As of release

Description

File Authentication Provider


0


Uses plain old YAML files to determine who has access to what reports. See below for details

Rest Authentication Provider


1.1


Allows for an external REST endpoint for Kagura to use for authentication. Basically outsourcing the authentication.

Hybrid Authentication Provider


1.1


Allows for you to mix and match authentication methods. Such as if you want the users to be specified from a Rest end point, however the actual group to reports mappings to be done by the File Authentication Provider you would use this.

Encrypted File Authentication Provider


Todo


Like File Authentication Provider, but uses encrypted passwords



JDBC/JNDI Authentication Provider


Todo


Authentication via database

JaaS Authentication Provider


Todo


Uses JaaS to provide authentication

Groovy Authentication Provider


Todo


An authentication provider that uses code to determine if the user is authenticated, probably for arbitrary integrations
File Authentication Provider

The file Authentication provider expects to see 2 files in the reports directory: users.yaml and groups.yaml. These are 2 very simply formatted files:

    users.yaml contains a list of: username, password, and an array of group names

    groups.yaml contains a list of: group names, with an array of report ids

Format:
users.yaml
- { username: "testuser", password: "testuserpass", groups: ["test reports"] }
- { username: "tu2", password: "tup2", groups: ["test reports2"] }
groups.yaml
- { groupname: "test reports", reports: [ "fake1" ] }
- { groupname: "test reports2", reports: [ "fake2", "groovytest" ] }
Rest Authentication Provider

The rest authentication provider requires a URL to be provided. The REST Authentication provider will be superseded by a more secure version. This version is designed to be protected. Your REST end point that you point it at must export the following methods:
GET /echo
Parameters:
- Query Param: message
Echos the message passed to it. Used to test the server.

GET /users
Parameters: None
Returns a list of users, with the username and the groups the user is in.

GET /groups
Parameters: None
Returns a list of groups and their reports

GET /login
Parameters:
- Post Data: JSON Map of "username" and "password"
Returns ok if the username and password are correct.
Java
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;
public interface MyAuth {
    @GET()
    @Path("/echo")
    public String echo(@QueryParam("message") @DefaultValue("No message= found.") String message);

    @GET()
    @Path("/users")
    public Object users();

    @GET()
    @Path("/groups")
    public Object groups();

    @POST()
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(Map input);
}
Hybrid Authentication Provider
Allows you to mix two authentication providers. You specify one for the groups and one for the user.