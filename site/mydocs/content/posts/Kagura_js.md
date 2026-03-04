---
title: "Kagura js"
date: 2024-01-01T00:00:00Z
draft: false
---

# Kagura js

Kagura.js[Updated Feb 21, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491524)*
![](https://kagura.atlassian.net/wiki/aa-avatar/950991e0-22e0-4955-ace3-39d47417389b)

# Kagura.js

Feb 21, 2014[Cloud editor]()

Kagura.js is a javascript file which is the driving force of the example javascript web page, which you can use as a basis for your reporting front end. Or you can write something yourself. Or go half way and use the kagura.js javascript file and hire a web designer to make a nicer front end for you your site.

Kagura JS, uses classes-as-tags and hidden elements to control the look and feel of the website. 

# Using

The usage is fairly simple. Include kagura JS. Ensure that all the tags are matched as required, and that you are calling the functions you care about.

# Usage overview

To use kagura you call functions. There are no automatically registered events or anything of that nature. You have full control and are required to build that up yourself. Most calls result in a ajax call, and there will be a delay before the action occurs.

Some of the typical calls: 

Function
--

Kagura.js
[Updated Feb 21, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491524)

Kagura.js
Feb 21, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491524/www.atlassian.com)

Kagura.js is a javascript file which is the driving force of the example javascript web page, which you can use as a basis for your reporting front end. Or you can write something yourself. Or go half way and use the kagura.js javascript file and hire a web designer to make a nicer front end for you your site.

Kagura JS, uses classes-as-tags and hidden elements to control the look and feel of the website.
Using

The usage is fairly simple. Include kagura JS. Ensure that all the tags are matched as required, and that you are calling the functions you care about.
Usage overview

To use kagura you call functions. There are no automatically registered events or anything of that nature. You have full control and are required to build that up yourself. Most calls result in a ajax call, and there will be a delay before the action occurs.

Some of the typical calls:

Function

Action

loginSpin()

loginUnspin()


Starts and stops the spinner.

doLogin()


Attempts a login. Gets username from #loginEmail and gets password from #loginPassword. If successful calls "gotoMain()" Otherwise loginUnspin() and alerts the user to the error

exportReport(fileType, all)


Starts a download of the report int he desired format

runReport()


Runs the report

Some of the typical classes/tags you will be using:

Tag / style class

Description

#reportErrors


The location to insert run errors onto a page

#reportPageNumber


The location to output the report page number

#loginEmail


The input used to get the username / email.

#loginPassword


The input used to get the password.

#reportTableHeader


The table column. Contains one hidden column which is used a template for the other columns

#reportTableBody


The table body. Contains 1 hidden row with a hidden cell for inserting rows & column values

Basically you get your web developer to put these where appropriate on their design, and then you can test the design by calling the appropriate functions and watching the results change.
Function details

Function

Called by

Action

Function

Called by

Action

loginSpin()

loginUnspin()


User


Starts and stops the spinner.

setToken(token)


Kagura.js


Stores the token a a cookie

gotoMain()


User, Kagura.js


Changes the URL to go to "main.jsp"

doLogin()


User


Attempts a login. Gets username from #loginEmail and gets password from #loginPassword. If successful calls "gotoMain()" Otherwise loginUnspin() and alerts the user to the error

loadReportListSimple()


User, onLoad


Simple list of reports. Calls loadReportListData()

loadReportListDetailed()


User, onLoad


Loads the report content list, as a detailed list with parameter values and more. With data when it gets it back calls: loadReportListData

loadReportListData()


Kagura.js


Once you obtain the list of reports, this function populates the UI with the correct data. It looks for #reportDropdownList, removes all non-hidden child nodes from it. Clones the hidden node. And populates the data into it as appropriate.

resetDisplay()


User, Kagura.js


Hides all categories, such as the main view, contact us view, about view and report view.

resetReportConfig()


User, Kagura.js


Resets report table data & columns, and parameter side bar.

resetReportBody()


Kagura.js


Resets report table data

resetReport()


Kagura.js, User


Resets report table data & columns.

addColumns(columns)


Kagura.js


Adds a column

buildReportParameters(msg, inputParamFieldTemplate)


Kagura.js


From the report data structure builds up the parameter input. Mostly pre-written components which are inserted.

processReportExtras


Kagura.js


Handles special extraOptions on the report.

loadReport(reportId)


User


Loads a report by the ID. Calls callKagura()

loadReport(reportId, andRun)


User


Loads a report by the ID, Optionally allows it to run the report in the same call.

displayMain()


User


Display the main section , after resetting the view.

displayContactUs()


User


Displays the contact us section, after resetting the view.

displayAboutUs()


User


Displays the about us section, after resetting the view.

exportReport(fileType, all)


User


Starts a download of the report int he desired format

buildRequestParameters()


Kagura.js


Compiles a JSON string of the report parameters, then URI encodes it.

rerunReport()


Kagura.js


Reruns the report, resetting the page number.

reportErrors(msg)


User, Kagura.js


Writes a bootstrap alert for errors returned in the message.

populateReportRows(msg)


Kagura.js


Populates the table.

fixReportColumns(msg)


Kagura.js


Updates column with new column details if changed.

runReport()


User


Runs the report.

callKagura(reportId, method, url, contentType)


Kagura.js


Ajax call to restful services, then executes the appopriate functions based on the structure of the response.

prevPage()


User


Decrements the page number then runs the report again

nextPage()


User


Increments the page number then runs the report again

logout()


User


Logouts, removes token, and resets page.

ajaxFail(jqXHR, textStatus, errorThrown)


Kagura.js


Reports ajax failures to the user. Used in AJAX calls.
Selector details

Tag / style class

Description

#signinSection


Login section. Used to disable region and enable a spinner

#reportDropdownList


The drop down where the report list is placed.

#kaguraMain
#kaguraContactUs
#kaguraAbout
#reportMain


The main sections of the page to tab between.

#reportTableHeader


The table column. Contains one hidden column which is used a template for the other columns

#reportTableBody


The table body. Contains 1 hidden row with a hidden cell for inserting rows & column values

#paramPanel


The parameter panel. Hidden if there aren't any contents.

#inputParamFieldTemplate


A template input field. For placement mostly.

#reportTitle


The report title location. Writes here when the title changes

#reportDescription


The report description location. Writes the description here if there is any.

#reportImage


The report image tag. Configures it to show an image if there is one

.alert


An alert location.

#reportErrors


The location to insert run errors onto a page

#reportPageNumber


The location to output the report page number

#loginEmail


The input used to get the username / email.

#loginPassword


The input used to get the password.