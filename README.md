Dialog App
==========

Project Dialog App REST service is an exploration attempt to make
million views per hour throughput mark in most inexpensive way on AWS micro.

This instance will be using:
- Dropwizard framework application exposing REST service
- LogRecordDAO using JDBI database connection
- Embedded Derby JavaDB integration
- Quartz SunDial scheduling library.

Install
=====

Initially build the server with maven by running "mvn clean install".
Maven will produce target folder and in it Dialog-app-1.0-SNAPSHOT.jar as 
completely distributable jar.

Start the service with:
"java -jar target/Dialog-app-1.0-SNAPSHOT.jar server dialogapp.yaml"
 
The default server port is 8080, with Dropwizard's administration part 
at port 8081 (http://localhost:8081)

Usage and tests
=====

Available application REST resources are:
    GET     /views/{profileid}/recent (dialog.resources.LogRecordResource)
    POST    /views/{profileid}/by/{viewid} (dialog.resources.LogRecordResource)

Available Administation resources, running on 8081 port, are:
    GET /metrics
    GET /healthcheck (database and application)
    GET /threads
    GET /ping
    POST    /tasks/gc (io.dropwizard.servlets.tasks.GarbageCollectionTask)

Adding profile view can be done with POST call with two parameters, 
id of the profile viewed and id of the viewer, {profileid} and {viewid} respectively.

curl -H "Content-Type:application/json" -X POST http://localhost:8080/views/{profileid}/by/{viewid}
e.g. curl -H "Content-Type:application/json" -X POST http://localhost:8080/views/3541112222/by/3541024323

Getting the list of previous calls can be done with GET and id of the profile

curl -H "Content-Type:application/json" -X GET http://localhost:8080/views/{profileid}/recent
e.g. curl -H "Content-Type:application/json" -X GET http://localhost:8080/views/3541112222/recent

Database 

Design choices
===========

Database schema was left deliberately simple, and efficient in terms of space, 
latency and throughput:
- Longs were used for timestamps type
- Table truncate is performed daily, since the requirements that only last 10 
days are saved it is wise to purge the entries that are older the set requirement.

