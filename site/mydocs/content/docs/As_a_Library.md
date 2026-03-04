---
title: "As a Library"
date: 2026-03-04T00:58:36+00:00
draft: false
---
# As a Library

As a Library[Updated Jan 16, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491541)*

# As a Library

Jan 16, 2014[Cloud editor]()

Kagura has several "library" components you have 2 "compulsory parts":

*

Report Storage Provider

*

Reports Core

And some optional components:

*

Authentication Providers

*

REST API Interfaces

*

Misc Helpers

Reports Core; is the part we are interested in if we want to generate output for a front end. It's the work horse. Report Storage Providers; is required in-order to provide a list of reports, and the report contents. It has a simple interface to inherit if you want to provide your own source.

Authentication providers; are entirely optional, they are provided to help standardise and interface and to provide a simple authentication method for demo sites and quick deploys. The actual core does not use it, it expects the caller application to manage authentication.

REST API Interfaces; kagura provides a REST interface to help standardise communication that components use. If you are implementing your own middleware it's highly suggested that you follow the provided interface if applicable. More information on the REST api can be found on the [REST API](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491544) page.

# Report Storage Provider

First thing you need to provide is what reports there are and their contents. The best way of doing this is via a Report Storage Provider. If the abstract class was an interface it would look as follows:

public abstract class ReportsProvider<InternalType> {
    protected String loadReport(ReportsConfig result, InternalType report) throws Exception;
	protected InternalType[] getReportList();
	protected String reportToName(InternalType report);
	public List<String> getErrors(); // Not actually supposed to be @overriden, use to store errors and clear
    public ReportsConfig getReportsConfig();
    public ReportsConfig getReportsConfig(Collection<String> restrictToNamed);

```
}
```

There is a bit more going on here. However this is what you need to know about to implement a basic storage engine.

The functions are rather straight forward.,

 
--

Once you have initialised your Reports Storage provider, you can get the "ReportsConfig" object and begin using the reports.

Generally when using the Kagura API there are a couple things you want to do:

*

List reports (user can access)

*

Get the report details, parameters, name, description, columns

*

Get the report results

All these are fairly simple. But before we continue let's talk about "extraOptions" aka "extra." extraOptions refer to a couple things, namely a hash table constructed of:

*

Specific values put into the report configuration, for that section

*

Run time parameters that are inserted by the middleware

It has been designed to prevent excessive "feature bloat" from entering the core Kagura reporting code base. Particularly by avoiding growth of the model. If you are using it as a library you should put any values that are important into this component.

## List of reports

First off, to get the list of reports you would do something like:

ReportsProvider<?> reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig();

```
return reportsProvider.getReports().values();
```

This is missing one major component, if you are using authentication, you only want to load the reports that the user has access to, to do that you would run:

ReportsProvider<?> reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(userReports);

```
return reportsProvider.getReports().values();
```

Where userReports is a Set<String> constructed of perhaps a union of all the reports all groups of that user can access.

## Report Details

You might think that just getting a list of reports then would be a simple as calling reportsProvider.getReports().get(); However there is something else you need to do, and that is populate the parameters correctly. For instance if you have a groovy script or SQL script run to produce combobox selection options, unless you prepare them, they won't show the content. Fortunately this is done in one step.

ReportsProvider<?> reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(); // or  reportsProvider.getReportsConfig(userReports);
ReportConfig reportConfig = reportsConfig.getReport(reportId);
reportConfig.prepareParameters(extraOptions);

```
return reportConfig; // Designed to be serialised into JSON
```

extraOptions is of course populated by the caller API entirely. If you have no values that are required simply give it an empty HashMap, however there are somethings you might want to consider passing:

*

DateTime

*

The user running the report

*

The users groups

*

Anything special about that user; ie combo box restrictions - remember to restrict these in the freemarker / sql as well

Some more "interesting" things to pass, at least you could try: (I have not tried it.)

*

FreeMarker "TemplateMethodModel" (gives you a FreeMarker function to run in parameters) or a class if you are using the Groovy report connector.

*

The ReportsConfig object itself, for self reference

The ReportConfig has been designed to be Serialised into a JSON object and returned. However you can manipulate it any which way you would like.

## Running the report

To run the report either create, or reuse the ReportConfig object above, prepareParameters, insertParameters, extract the "Connector" and run the connector:

List<String> errors = new List<String>();
ReportsProvider<?> reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(); // or  reportsProvider.getReportsConfig(userReports);
ReportConfig reportConfig = reportsConfig.getReport(reportId);
reportConfig.prepareParameters(extraOptions1);
ReportConnector reportConnector = reportConfig.getReportConnector();
ParameterUtils.insertParameters(parameters, reportConnector, errors); // Note this is done on the connector.
reportConnector.run(extraOptions2);
// Results
errors.addAll(reportConnector.getErrors());
List<ColumnDef> columns = reportConnector.getColumns();
List<Map<String, Object>> rows = reportConnector.getRows();

```
return {columns, rows, errors, reportConfig};
```

Once again there is an extraOptions, these are applied to the report itself, they can be the same or different as the extra options applied to the parameters.

### Parameters

In this example I am using "ParameterUtils.insertParameters" to insert the parameters, this is only applicable if you are using the REST API component, and using the Parameters class in that library. This isn't the compulsory way of handling parameters, such as if you are using JSF you can populate the parameters (found in ReportConnector.getParameterConfig()) directly, just make sure you set the field: "value" and use "values" for listing alternatives, if applicable. I would recommend the use of Apache's BeanUtils to populate the values like such:

    public static void insertParameters(Parameters parameters, ReportConnector reportConnector, List<String> errors) {
        if (reportConnector.getParameterConfig() != null)
        {
            for (ParamConfig paramConfig : reportConnector.getParameterConfig())
            {
                if (parameters.getParameters().containsKey(paramConfig.getId()))
                {
                    Object o = parameters.getParameters().get(paramConfig.getId());
                    try {
                        if (o != null && StringUtils.isNotBlank(o.toString()))
                            BeanUtils.setProperty(paramConfig, "value", o);
                        else
                            BeanUtils.setProperty(paramConfig, "value", null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (ConversionException e){
                        e.printStackTrace();
                        errors.add("Could not convert parameter: " + paramConfig.getId() + " value " + o);
                    }
                }
            }
        }

```
    }
```

# Errors

I haven't covered errors. It's important to check for errors, if there are any errors you will get a NULL object, and the parent will have a new value in getErrors(). Once you have obtained the errors from getErrors(), it would be a good idea to clear it, one way or another. Here is an example of running the report with errors properly checked.

List<String> errors = new List<String>();
ReportsProvider<?> reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(); // or  reportsProvider.getReportsConfig(userReports);
if (reportsConfig == null)
{
	errors.addAll(reportsProvider.getErrors());
	reportsProvider.getErrors().clear();
	return errors;
}
ReportConfig reportConfig = reportsConfig.getReport(reportId);
if (reportConfig == null)
{
	errors.addAll(reportsConfig.getErrors());
	reportsConfig.getErrors().clear();
	return errors;
}
reportConfig.prepareParameters(extraOptions1);
ReportConnector reportConnector = reportConfig.getReportConnector();
if (reportConnector == null)
{
	errors.addAll(reportConfig.getErrors());
	reportConfig.getErrors().clear();
	return { errors, reportConfig };
}
ParameterUtils.insertParameters(parameters, reportConnector, errors); // Note this is done on the connector.
reportConnector.run(extraOptions2);
// Results
errors.addAll(reportConnector.getErrors()); // Not always fatal.
reportConnector.getErrors().clear();
List<ColumnDef> columns = reportConnector.getColumns();
List<Map<String, Object>> rows = reportConnector.getRows();

```
return {columns, rows, errors, reportConfig};
```

# Notes

I would recommend you use the [REST API](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491544) if you are making middleware. It provides structure and allows you to use your variant of Kagura.js.As a Library
[Updated Jan 16, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491541)

As a Library
Jan 16, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491541/www.atlassian.com)

Kagura has several "library" components you have 2 "compulsory parts":

    Report Storage Provider

    Reports Core

And some optional components:

    Authentication Providers

    REST API Interfaces

    Misc Helpers

Reports Core; is the part we are interested in if we want to generate output for a front end. It's the work horse. Report Storage Providers; is required in-order to provide a list of reports, and the report contents. It has a simple interface to inherit if you want to provide your own source.

Authentication providers; are entirely optional, they are provided to help standardise and interface and to provide a simple authentication method for demo sites and quick deploys. The actual core does not use it, it expects the caller application to manage authentication.

REST API Interfaces; kagura provides a REST interface to help standardise communication that components use. If you are implementing your own middleware it's highly suggested that you follow the provided interface if applicable. More information on the REST api can be found on the [REST API](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491544) page.
Report Storage Provider

First thing you need to provide is what reports there are and their contents. The best way of doing this is via a Report Storage Provider. If the abstract class was an interface it would look as follows:
public abstract class ReportsProvider {
    protected String loadReport(ReportsConfig result, InternalType report) throws Exception;
	protected InternalType[] getReportList();
	protected String reportToName(InternalType report);
	public List getErrors(); // Not actually supposed to be @overriden, use to store errors and clear
    public ReportsConfig getReportsConfig();
    public ReportsConfig getReportsConfig(Collection restrictToNamed);
}

There is a bit more going on here. However this is what you need to know about to implement a basic storage engine.

The functions are rather straight forward.,





loadReport()


Is responsible for parsing the data structure, what ever it is, into the "com.base2.kagura.core.report.configmodel.ReportConfig" format

getReportList()


Gets a list of reports, in the internal format, what ever that may be. This is the first function called and is used to render others

reportToName()


Gets the report name.

getErrors()


Used to report errors, and clear errors. getErrors().add("your error");

getReportsConfig()


Gets all reports in a report config. You shouldn't use this one unless you really do want access to all reports the report provider provides, such as in a system with no authentication

getReportsConfig(Collection restrictToNamed)


Restricted reports, reports which are restricted to just the named ones. Such as a user's specific reports. This prevents manual attacks via REST.
Reports Core

Once you have initialised your Reports Storage provider, you can get the "ReportsConfig" object and begin using the reports.

Generally when using the Kagura API there are a couple things you want to do:

    List reports (user can access)

    Get the report details, parameters, name, description, columns

    Get the report results

All these are fairly simple. But before we continue let's talk about "extraOptions" aka "extra." extraOptions refer to a couple things, namely a hash table constructed of:

    Specific values put into the report configuration, for that section

    Run time parameters that are inserted by the middleware

It has been designed to prevent excessive "feature bloat" from entering the core Kagura reporting code base. Particularly by avoiding growth of the model. If you are using it as a library you should put any values that are important into this component.
List of reports

First off, to get the list of reports you would do something like:
ReportsProvider reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig();
return reportsProvider.getReports().values();

This is missing one major component, if you are using authentication, you only want to load the reports that the user has access to, to do that you would run:
ReportsProvider reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(userReports);
return reportsProvider.getReports().values();

Where userReports is a Set constructed of perhaps a union of all the reports all groups of that user can access.
Report Details

You might think that just getting a list of reports then would be a simple as calling reportsProvider.getReports().get(); However there is something else you need to do, and that is populate the parameters correctly. For instance if you have a groovy script or SQL script run to produce combobox selection options, unless you prepare them, they won't show the content. Fortunately this is done in one step.
ReportsProvider reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(); // or  reportsProvider.getReportsConfig(userReports);
ReportConfig reportConfig = reportsConfig.getReport(reportId);
reportConfig.prepareParameters(extraOptions);
return reportConfig; // Designed to be serialised into JSON

extraOptions is of course populated by the caller API entirely. If you have no values that are required simply give it an empty HashMap, however there are somethings you might want to consider passing:

    DateTime

    The user running the report

    The users groups

    Anything special about that user; ie combo box restrictions - remember to restrict these in the freemarker / sql as well

Some more "interesting" things to pass, at least you could try: (I have not tried it.)

    FreeMarker "TemplateMethodModel" (gives you a FreeMarker function to run in parameters) or a class if you are using the Groovy report connector.

    The ReportsConfig object itself, for self reference

The ReportConfig has been designed to be Serialised into a JSON object and returned. However you can manipulate it any which way you would like.
Running the report

To run the report either create, or reuse the ReportConfig object above, prepareParameters, insertParameters, extract the "Connector" and run the connector:
List errors = new List();
ReportsProvider reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(); // or  reportsProvider.getReportsConfig(userReports);
ReportConfig reportConfig = reportsConfig.getReport(reportId);
reportConfig.prepareParameters(extraOptions1);
ReportConnector reportConnector = reportConfig.getReportConnector();
ParameterUtils.insertParameters(parameters, reportConnector, errors); // Note this is done on the connector.
reportConnector.run(extraOptions2);
// Results
errors.addAll(reportConnector.getErrors());
List columns = reportConnector.getColumns();
List> rows = reportConnector.getRows();
return {columns, rows, errors, reportConfig};

Once again there is an extraOptions, these are applied to the report itself, they can be the same or different as the extra options applied to the parameters.
Parameters

In this example I am using "ParameterUtils.insertParameters" to insert the parameters, this is only applicable if you are using the REST API component, and using the Parameters class in that library. This isn't the compulsory way of handling parameters, such as if you are using JSF you can populate the parameters (found in ReportConnector.getParameterConfig()) directly, just make sure you set the field: "value" and use "values" for listing alternatives, if applicable. I would recommend the use of Apache's BeanUtils to populate the values like such:
    public static void insertParameters(Parameters parameters, ReportConnector reportConnector, List errors) {
        if (reportConnector.getParameterConfig() != null)
        {
            for (ParamConfig paramConfig : reportConnector.getParameterConfig())
            {
                if (parameters.getParameters().containsKey(paramConfig.getId()))
                {
                    Object o = parameters.getParameters().get(paramConfig.getId());
                    try {
                        if (o != null && StringUtils.isNotBlank(o.toString()))
                            BeanUtils.setProperty(paramConfig, "value", o);
                        else
                            BeanUtils.setProperty(paramConfig, "value", null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (ConversionException e){
                        e.printStackTrace();
                        errors.add("Could not convert parameter: " + paramConfig.getId() + " value " + o);
                    }
                }
            }
        }
    }
Errors

I haven't covered errors. It's important to check for errors, if there are any errors you will get a NULL object, and the parent will have a new value in getErrors(). Once you have obtained the errors from getErrors(), it would be a good idea to clear it, one way or another. Here is an example of running the report with errors properly checked.
List errors = new List();
ReportsProvider reportsProvider; // I encourage 1 per user per session, however you are free to use it anyway you like.
reportsProvider = new FileReportsProvider(reportLoc); // If you are using the FileReportsProvider.
ReportsConfig reportsConfig = reportsProvider.getReportsConfig(); // or  reportsProvider.getReportsConfig(userReports);
if (reportsConfig == null)
{
	errors.addAll(reportsProvider.getErrors());
	reportsProvider.getErrors().clear();
	return errors;
}
ReportConfig reportConfig = reportsConfig.getReport(reportId);
if (reportConfig == null)
{
	errors.addAll(reportsConfig.getErrors());
	reportsConfig.getErrors().clear();
	return errors;
}
reportConfig.prepareParameters(extraOptions1);
ReportConnector reportConnector = reportConfig.getReportConnector();
if (reportConnector == null)
{
	errors.addAll(reportConfig.getErrors());
	reportConfig.getErrors().clear();
	return { errors, reportConfig };
}
ParameterUtils.insertParameters(parameters, reportConnector, errors); // Note this is done on the connector.
reportConnector.run(extraOptions2);
// Results
errors.addAll(reportConnector.getErrors()); // Not always fatal.
reportConnector.getErrors().clear();
List columns = reportConnector.getColumns();
List> rows = reportConnector.getRows();
return {columns, rows, errors, reportConfig};
Notes
I would recommend you use the [REST API](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491544) if you are making middleware. It provides structure and allows you to use your variant of Kagura.js.