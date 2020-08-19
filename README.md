# calendar-spring-mongo
Simple personal calendar app.

## Connecting to the MongoDB
Properties used to connect this app to the MongoDB can be found in application.properties files.

## MongoDB and transactions
If mongo database, to which this application
is connected to, is standalone mongo instance,
exception 
```
"Sessions are not supported by the MongoDB cluster to which this client is connected" 
```
would be thrown. On the other hand in the official documentation is written that Mongo supports transactions feature for a replica set (https://docs.mongodb.com/manual/reference/method/Session.startTransaction/).
Fragmet that is important:
```
In version 4.0, MongoDB supports multi-document transactions on replica sets.
In version 4.2, MongoDB introduces distributed transactions, which adds support for multi-document transactions on sharded clusters and incorporates the existing support for multi-document transactions on replica sets.
```
One of solutions is to make a replica set. Process of creating replica set for testing and development purposes is described for example here:
https://docs.mongodb.com/manual/tutorial/deploy-replica-set-for-testing/.

### Creating collections in a transaction
You can come across the exception which says:
```
Cannot create namespace DATABASE.COLLECTION in multi-document transaction.
```
Creating collection in multi-document transactions are not possible (It's important to remember that if some collection doesn't
exist and we try to insert some data into that non-existent collection, MongoDB would try to create it). One of the solutions is to create needed collections manually.

## Future plans
 - adding security with jwt (implementing things like: using authenticated info about logged user to making changes in events and
 enabling logging and registration among other things),
 - (Optional) addding custom cascade annotations,