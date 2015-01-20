package se.lagrummet

/**
 * Created by christian on 1/19/15.
 * Encapsulate a section of code you want to benchmark and print to log
 *
 * Code ex: <code>Benchmark.section("RDL latest consolidated search time", log) {</code>
 */
class Benchmark {

    static def section = { logText, log, body ->
        def timestamp = System.currentTimeMillis()
        body.call()
        def elapsedTime = System.currentTimeMillis() - timestamp
        log.info("${logText} ${elapsedTime} ms")
    }
}
