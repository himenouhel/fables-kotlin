import org.gradle.api.tasks.bundling.War

apply {
    plugin("war")
}

description = "Java Reference Server"

(tasks["war"] as War).archiveName = "java.war"

dependencies {
    compile("com.fasterxml.jackson.core:jackson-annotations:${extra["jackson_version"]}")

    testCompile("com.fasterxml.jackson.core:jackson-core:${extra["jackson_version"]}")
    testCompile("com.fasterxml.jackson.core:jackson-databind:${extra["jackson_version"]}")
    testCompile(group = "com.jayway.restassured", name = "rest-assured", version = extra["rest_assured_version"] as String?)
    testCompile(group = "junit", name = "junit", version = "4.12")
    testCompile(group = "org.hamcrest", name = "hamcrest-all", version = "1.3")
}
