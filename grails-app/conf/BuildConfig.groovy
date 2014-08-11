grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

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

        mavenCentral()
        mavenLocal()
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        //compile "org.compass-project:compass:2.2.1"

        runtime 'hsqldb:hsqldb:1.8.0.10'

        build("org.tmatesoft.svnkit:svnkit:1.3.5") {
            excludes "jna", "trilead-ssh2", "sqljet"
        }


    }
    plugins {
        compile ":hibernate:3.6.10.17"
        compile ":tiny-mce:3.4.9"
        compile ":searchable:0.6.8"
        compile ":quartz2:2.1.6.2"
        compile ":jquery:1.11.1"
        compile ":rest:0.8"
        compile ":mail:1.0.6"
        compile ":spring-security-core:1.2.7.4"
        compile ":webxml:1.4.1"
        compile 'org.grails.plugins:gson:1.1.4'

        build ":tomcat:7.0.54"

    }
}
