---
title: "REST API"
date: 2024-01-01T00:00:00Z
draft: false
---

# REST API

Report Providers[Published Feb 14, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491526)*
![](https://kagura.atlassian.net/wiki/aa-avatar/a2ec09a3-a6d3-42b3-8c8a-78a5f60355e9)

# Report Providers

Feb 14, 2014[Cloud editor]()

Report Providers are a tool used to return the report data, they are fairly straightforwards.

Report providers:

Report Provider
--

Currently (1.4) the only supported mechanism for storing a file. It provides a report from a configuration as follows:

<reportName>/reportconf.yaml or <reportName>/reportconf.json

It depends on the com.base2.kagura.reportloc configuration option for the location. More information can be found here: [Building a basic JNDI/JDBC report](https://open.base2services.com/pages/viewpage.action?pageId=4098417)

## Cached Report Storage Provider

Cached Report Storage Provider uses the decorator pattern to add a caching system to any other report system. In some cases it isn't necessary, it depends on how the client library uses the API.

# Super File Report Storage Provider

Identical
 to File Report Storage Provider, however instead of using directories,
it uses a HashMap<String, ReportConfig> configuration in a single
YAML file. Report Providers
[Published Feb 14, 2014](https://kagura.atlassian.net/wiki/spaces/KGR/history/491526)

Report Providers
Feb 14, 2014
[Cloud editor](https://kagura.atlassian.net/wiki/spaces/KGR/pages/491526/www.atlassian.com)

Report Providers are a tool used to return the report data, they are fairly straightforwards.

Report providers:

Report Provider

Since

Description

File Report Storage Provider


0


File backed reports, stored in report loc

Cached Report Storage Provider


1.1


Uses the decorator pattern on an existing report provider to cache the values.

Super File Report Storage Provider


1.1


All reports in one YAML file, for simplicity

GIT Report Storage Provider


Removed, Todo


A report provider that uses GIT.

S3 Report Storage Provider


Todo


A report provider that uses s3 as the backing

Compressed File Report Storage Provider


Todo


Where reports are in a compressed file
File Report Storage Provider

Currently (1.4) the only supported mechanism for storing a file. It provides a report from a configuration as follows:

/reportconf.yaml or /reportconf.json

It depends on the com.base2.kagura.reportloc configuration option for the location. More information can be found here: [Building a basic JNDI/JDBC report](https://open.base2services.com/pages/viewpage.action?pageId=4098417)
Cached Report Storage Provider

Cached Report Storage Provider uses the decorator pattern to add a caching system to any other report system. In some cases it isn't necessary, it depends on how the client library uses the API.
Super File Report Storage Provider
Identical to File Report Storage Provider, however instead of using directories, it uses a HashMap configuration in a single YAML file.