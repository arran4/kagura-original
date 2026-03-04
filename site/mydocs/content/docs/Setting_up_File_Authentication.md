---
title: "Setting up File Authentication"
date: 2026-03-04T00:58:36+00:00
draft: false
---
# Setting up File Authentication

Setting up File Authentication Provider user and groups

There are a couple of Authentication providers one is the "File Authentication Provider" which is a simple file based authentication. This particular one does not employ encryption. It's designed for demo sites. I would suggest using another authentication provider. The setup is simple, you create 2 files in the reports/ directory,

    users.yaml

    groups.yaml

Users can't have direct access to reports, they have be placed in a group in-order to access the reports. A user can only have 3 fields: username, password, and a list of groups they are in.

A group has only 2 fields, group name and a list of reports it grants access to. Everything is read only inside the container.

Generally the files are located in reports/. Below is a sample configuration of 1 user, 1 group and 1 report:

users.yaml
- { username: "testuser", password: "t3st", groups: ["group1"] }

groups.yaml
- { groupname: "group1", reports: [ "TheAnswers" ] }

This creates a group called "group1" which can access the report "TheAnswers" and creates a user called "testuser." Obviously in a real system you would want this data to at least be encrypted.
