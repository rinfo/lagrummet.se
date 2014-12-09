grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"

        mavenCentral()
        mavenLocal()
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        //compile "org.compass-project:compass:2.2.1"
        runtime 'hsqldb:hsqldb:1.8.0.10'

        // We had runtime production problems as reported in: https://github.com/robfletcher/grails-gson/issues/38
        // This fix was suggested:  compile 'org.springframework:spring-test:2.5'
        // However, installing spring-test from Grails Central didn't work.
        // Instead, manually downloaded jar file is currently in project's lib folder.

        build 'org.springframework:spring-test:3.2.5.RELEASE'
        //runtime 'org.springframework:spring-test:3.2.5.RELEASE'
        //test 'org.springframework:spring-test:3.2.5.RELEASE'



        build("org.tmatesoft.svnkit:svnkit:1.3.5") {
            excludes "jna", "trilead-ssh2", "sqljet"
        }


    }
    plugins {
        compile ":hibernate:3.6.10.17"
        compile ":tiny-mce:3.4.9"
        compile ":searchable:0.6.9"
        compile ":quartz2:2.1.6.2"
        compile ":jquery:1.11.1"
        compile ":rest:0.8"
        compile ":mail:1.0.7"
        compile ":spring-security-core:1.2.7.4"
        compile ":webxml:1.4.1"
        compile 'org.grails.plugins:gson:1.1.4'
        compile ':cache-headers:1.1.7'
        build ":tomcat:7.0.55"


    }
}
