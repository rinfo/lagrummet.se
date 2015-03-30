package se.lagrummet

import groovy.time.TimeCategory

/**
 * Created by christian on 1/19/15.
 * Encapsulate a section of code you want to benchmark and print to log
 *
 * Code ex: <code>Benchmark.section("RDL latest consolidated search time", log) {</code>
 */
class Benchmark {

    static def section = { logText, log, body ->
        def timestamp = new Date()
        body.call()
        log.info(logText+" "+TimeCategory.minus(new Date(), timestamp))
    }

    static def sectionByIntermediate = { logText, log, body ->
        def intermediate = new Intermediate(log)
        body.call(intermediate)
        intermediate.sum(logText)
    }

    static class Intermediate {
        def log
        def timestamp = new Date()
        def list = [:]

        Intermediate(log) {
            this.log = log
        }

        void log(String comment) {
            list.put(new Date(), comment)
        }
        void sum(String text) {
            list.each { start, comment -> log.info(comment+" "+TimeCategory.minus( (Date) start, timestamp)) }
            log.info(text+" "+TimeCategory.minus(new Date(), timestamp))
        }
    }
}
