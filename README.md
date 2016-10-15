#DSE Simple+Kerberos Authentication test 

This is a short sample code to show how Plain Text and Kerberos authentification works on a Cassandra/DSE Cluster.
It enables you to check that authentication is properly setup and works fine.
The cluster should be setup with DSE Unified Authentication, Kerberos as main scheme and internal authentication as alternative schem.

- The principal myapp@MYREALM.COM should be created on the KDC.
- myapp@MYREALM.COM 's keytab should be placed at /tmp/myapp.keytab with the permissions 0600.
- The default user cassandra with its password cassandra should be kept
- Create the role myapp@MYREALM.COM in Cassandra and grant some permissions (Mandatory: GRANT EXECUTE ON KERBEROS SCHEME TO "myapp@MYREALM.COM";) 
- Build the jar :
```
mvn assembly:assembly
```
- Copy /dseclient.jaas file at the location where the jar was built.
- Launch the test :
```
java -Djava.security.auth.login.config=dseclient.jaas auth-test-1.0-SNAPSHOT-jar-with-dependencies.jar
```

