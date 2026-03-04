---
title: "Configurations"
date: 2026-03-04T00:58:36+00:00
draft: false
---
# Configurations

Kagura is flexible enough to be used in various configurations, here are a couple of use cases I can think of:
As a stand alone reports web service

This is primarily what comes to mind when people think of using something like Kagura. Kagura has 2 ways of operating out-of-the-box as a fully functional web service;

    As a Tomee compatible war file

    As a Jetty + Karaf (OSGI) service

These are straight forwards to get started with, see the getting started guide. Both of these solutions provide the entire stack, including library, backend / middleware, javascript client, and web assets. This would be the easiest point to begin customisation.
Tomee War file

This is an OpenEJB + CXF backed project that allows you to quickly get a service running without many overheads. It however does however embed the reports into the resources of the WAR file, which means rapid prototyping of reports is difficult. It is possible to over-come this by modifying the source and pointing it to a hard coded or configurable path. This is a good way to just deploy the service as a demo, or once you have fully configured it to keep the maintenance down. One of the advantages of this is that there is only one service that you need to worry about.

If you were to get started on modifying this you could

    copy the project as an example and extend it

    use it as a guideline for implementing or integrating it into your own projects

This is the best point to start if

    you want to customise the look and feel, and have the reports mostly ready

    want to setup a quick visual orientated demo

Jetty + Karaf

This deployment method requires you to run 2 services, a Jetty based war file and a Karaf assembly (or feature file and bundles if you already have a karaf environment you wish to use.) This is slightly more difficult to get setup, however it is much better for rapid development of reports, it can also be used as a dependency in a project to repackage the assembly, for an easy file for a deployment or CI pipeline.

If you were to get started of modifying this you could:

    Karaf

        Take the assembly file generated and start modifying the provided files (configuration, and reports.)

        Use the features file and import the bundles into an existing environment then start developing reports in etc/reports (If you don't change the configuration)

        Use the assembly as a bundle in a new maven project and repackage the files (recommended as you can use source control.)

    Web

        Copy web assets or war project and modify them (Files do not need to be run in java.)

This is the best point to start if:

    You want to develop reports, and expect lots of modifications

    You will be using a separate method for providing the web assets to running the backend

    You have a CI environment and want a nice tidy deployment process

As a rest provider for reports

Kagura has a standard REST API for communicating with it's components, this is provided by the middleware such as the WAR file or the Karaf component. It also has an example JavaScript library for extending the way the current application. There are several reasons you would want to do this:

    You are using the kagura backend for generating scheduled reports (see roadmap) and don't use Java in your organisation preventing you from integrating it into your product

    You use a web framework which you want to integrate the data with

You would do this using Karaf, or stripping out the web assets from the war example and deploying it as per the instructions above.
Tomee War file

The instructions are the same as in the 'Stand alone' configuration, however you would not be importing the web assets.
Karaf

This is the heart of what the OSGI container does. So if you were to follow the instructions from the 'Stand alone' part minus the Jetty stuff, you would have exactly this.

As a library to implement in your own projects so it can be entirely integrated with your existing applications. This requires the most work, however it provides the highest level of integration. This is best as the final step once you have developed your reports and figured out what you need. This is also the most flexible install.
