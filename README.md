#Description

When running ``mvn clean package sonar:sonar``` on Sonarqube LTS (4.5.6) with latest Java plugin, the build failed :

```
[INFO] [13:20:49.905] Configured Java source version: none
[INFO] [13:20:49.905] JavaClasspath initialization...
[WARN] [13:20:49.906] sonar.binaries and sonar.libraries are deprecated since version 2.5 of sonar-java-plugin, please use sonar.java.binaries and sonar.java.libraries instead
[INFO] [13:20:49.906] JavaClasspath initialization done: 1 ms
[INFO] [13:20:49.906] JavaTestClasspath initialization...
[INFO] [13:20:49.906] JavaTestClasspath initialization done: 0 ms
[INFO] [13:20:49.916] Java Main Files AST scan...
[INFO] [13:20:49.916] 2 source files to be analyzed
[ERROR] [13:20:49.932] Class not found: test.sonar.api.MyService
[ERROR]
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO]
[INFO] bug-stackoverflow .................................. FAILURE [  5.146 s]
[INFO] api ................................................ SUCCESS [  1.008 s]
[INFO] impl ............................................... SUCCESS [  0.061 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.671 s
[INFO] Finished at: 2015-12-31T13:20:49+01:00
[INFO] Final Memory: 31M/624M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.codehaus.sonar:sonar-maven-plugin:4.5.6:sonar (default-cli) on project bug-stackoverflow: null: MojoExecutionException: StackOverflowError -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
```

The bug seems to be related to the name of the interface and implementation, that's the same.

To fix, I found two ways :
* rename the interface OR the implementation (see branch fix1)
* using fully qualified name of the interface in the "implements" of the intermediate abstract class (instead of shortname+import package) (see branch fix2)


## Step to reproduce


1. Running sonarqube LTS with Docker :
```docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube:lts```
2. Updating Java Plugin to latest version in the Sonarqube Administration (Tested with 3.8)
3. Stopping the container :
```docker stop sonarqube```
4. Restarting it to load the updated plugin :
```docker start sonarqube```
5. Running the maven build
```mvn clean package sonar:sonar -Dsonar.host.url=http://$DOCKER_IP:9000 -Dsonar.jdbc.url="jdbc:h2:tcp://$DOCKER_IP/sonar"```

