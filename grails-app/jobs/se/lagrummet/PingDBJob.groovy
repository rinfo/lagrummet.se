package se.lagrummet

/**
 * Created with IntelliJ IDEA.
 * User: christian
 * Date: 6/2/14
 * Time: 8:42 AM
 * To change this template use File | Settings | File Templates.
 */
class PingDBJob {

    def pingDBService

    static triggers = {
        simple startDelay: 60*60*1000, repeatInterval: 60*60*1000  // ping once an houre
        //simple startDelay: 5*1000, repeatInterval: 5*1000 // ping every five seconds
    }

    def execute() {
        log.trace("Ping MySQL database")
        pingDBService.pingDB()
    }
}
