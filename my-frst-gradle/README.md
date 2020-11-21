
第一个 Gradle 项目， 参考：

https://blog.csdn.net/achenyuan/article/details/80682288



``

> Task :tasks

------------------------------------------------------------
Tasks runnable from root project
------------------------------------------------------------

Build tasks
-----------
assemble - Assembles the outputs of this project.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the main source code.
scaladoc - Generates Scaladoc for the main source code.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'my-first-gradle'.
components - Displays the components produced by root project 'my-first-gradle'. [incubating]
dependencies - Displays all dependencies declared in root project 'my-first-gradle'.
dependencyInsight - Displays the insight into a specific dependency in root project 'my-first-gradle'.
dependentComponents - Displays the dependent components of components in root project 'my-first-gradle'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project 'my-first-gradle'. [incubating]
outgoingVariants - Displays the outgoing variants of root project 'my-first-gradle'.
projects - Displays the sub-projects of root project 'my-first-gradle'.
properties - Displays the properties of root project 'my-first-gradle'.
tasks - Displays the tasks runnable from root project 'my-first-gradle'.

IDE tasks
---------
cleanIdea - Cleans IDEA project files (IML, IPR)
idea - Generates IDEA project files (IML, IPR, IWS)
openIdea - Opens the IDEA project

Verification tasks
------------------
check - Runs all checks.
test - Runs the unit tests.

Other tasks
-----------
cleanIdeaModule
cleanIdeaProject
cleanIdeaWorkspace
compileJava - Compiles main Java source.
compileScala - Compiles the main Scala source.
compileTestJava - Compiles test Java source.
compileTestScala - Compiles the test Scala source.
createDirs
ideaModule - Generates IDEA module files (IML)
ideaProject - Generates IDEA project file (IPR)
ideaWorkspace - Generates an IDEA workspace file (IWS)
prepareKotlinBuildScriptModel
processResources - Processes main resources.
processTestResources - Processes test resources.
run

Rules
-----
Pattern: clean<TaskName>: Cleans the output files of a task.
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.
Pattern: upload<ConfigurationName>: Assembles and uploads the artifacts belonging to a configuration.

Deprecated Gradle features were used in this build, making it incompatible with Gradle 7.0.
Use '--warning-mode all' to show the individual deprecation warnings.
See https://docs.gradle.org/6.6.1/userguide/command_line_interface.html#sec:command_line_warnings

BUILD SUCCESSFUL in 1s
1 actionable task: 1 executed


```