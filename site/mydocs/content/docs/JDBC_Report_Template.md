---
title: "JDBC Report Template"
date: 2026-03-04T00:58:36+00:00
draft: false
---
# JDBC Report Template

JDBC Report Template
reports/TheAnswers/reportconf.yaml
type: "JDBC"
classLoaderPath: "com.mysql.jdbc.Driver"
jdbc: "jdbc:mysql://localhost:3306/test"
username: "test"
password: "test"
sql: "SELECT id, question, answer FROM Answers
	<@where>
		<@clause render=param.questionQuery?has_content> question=${method.value(param.questionQuery)} </@clause> </@where>
	<@limit />"
columns:
  - name: question
    label: Question
  - name: answer
    label: Answer
extraOptions:
  reportName: "The Answers"
  directory: "Folder 1"
  description: "This is a sample report"
  image: "http://jentrata.org/images/jentrata.png"
paramConfig:
  - { type: String, id: questionQuery, name: "Question to search for", help: "Leave blank for any", placeholder: "None" }
