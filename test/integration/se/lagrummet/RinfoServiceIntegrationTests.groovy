package se.lagrummet

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.mockserver.jetty.integration.ClientAndProxy
import org.mockserver.jetty.integration.ClientAndServer
import org.mockserver.matchers.Times
import org.mockserver.model.Delay
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.slf4j.spi.LocationAwareLogger

import java.util.concurrent.TimeUnit

import static org.mockserver.jetty.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.jetty.integration.ClientAndServer.startClientAndServer
import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response

/**
 * Created with IntelliJ IDEA.
 * User: christian
 * Date: 2/19/14
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
class RinfoServiceIntegrationTests extends GroovyTestCase {

    private final String TEST_RESOURCES_RELATIVE_PATH = "./test/resources/"
    private final String HTTP_RESPONSE_TEST_STRING_CHARSET = "Ära öar Åland Rättsinformationsförordning Ändring"
    private final String XML_CONTENT = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><!DOCTYPE html>" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<body>Rättsinformationsförordning Ändring</body>" +
            "</html>"
    private final String XML_CONTENT_WITH_1552_ENCODING = "<?xml version=\"1.0\" encoding=\"Windows-1252\" standalone=\"no\"?><!DOCTYPE html>" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<body>Rättsinformationsförordning Ändring</body>" +
            "</html>"
    private final String XML_CONTENT_REPLACE_ENCODING = "<?xml version=\"1.0\" encoding=\"encoding-replace\" standalone=\"no\"?><!DOCTYPE html>" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<body>Rättsinformationsförordning Ändring</body>" +
            "</html>"

    private ClientAndProxy proxy;
    private ClientAndServer mockServer;

    @Override
    protected void setUp() throws Exception {
        super.setUp()
        LocationAwareLogger log = null;
        int port = 8090;
        mockServer = startClientAndServer(port)
        proxy = startClientAndProxy(9090)
        ConfigurationHolder.config.lagrummet.rdl.service.baseurl = "http://127.0.0.1:"+port
        ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl = "http://127.0.0.1:"+port
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        proxy.stop()
        mockServer.stop()
    }

    byte[] getResourceFile(String fileName) {
        FileInputStream fileInputStream = new FileInputStream(TEST_RESOURCES_RELATIVE_PATH+fileName)
        byte[] xmlFileAsBytes = new byte[60000];
        int checkSize = fileInputStream.read(xmlFileAsBytes);
        byte[] buffer = new byte[checkSize]
        System.arraycopy(xmlFileAsBytes, 0, buffer, 0, checkSize);
        return buffer
    }

    byte[] getXMLContent(String encoding) {
        return XML_CONTENT_REPLACE_ENCODING.replaceAll("encoding-replace", encoding).getBytes(encoding)
    }

    void makeStarndardRequest(String charset) {
        String httpRequestPath = "/testCharset"+charset;
        RinfoService rinfoService = new RinfoService()
        //byte[] response_content = getXMLContent(charset);
        //byte[] response_content = "A".getBytes(charset)
        byte[] response_content = "AÖA".getBytes(charset)
        println "RinfoServiceIntegrationTests.makeStarndardRequest response_content.length="+response_content.length+" "+response_content
        println "RinfoServiceIntegrationTests.makeStarndardRequest ****************************************************************"
        println ""+new String(response_content,"utf-8")
        println "RinfoServiceIntegrationTests.makeStarndardRequest ****************************************************************"

        HttpRequest request = request()
                .withMethod("GET")
                .withPath(httpRequestPath)

        mockServer
                .when(request, Times.exactly(1))
                .respond(
                response()
                        .withHeaders(
                        new Header("Content-Type", "text/html; charset="+charset),
                )
                        .withBody(response_content)
                        //.withBody(new String(response_content, "utf-8"))
        );

        //String content = rinfoService.getXHtmlContentJavaVersion(httpRequestPath, response_content, charset)
        String content = rinfoService.getXHtmlContentJavaVersion(response_content, charset)
        //String content = rinfoService.getXHtmlContent(httpRequestPath)

        assertTrue "Should contain 'Rättsinformationsförordning'", content.indexOf("Rättsinformationsförordning") > -1
        assertTrue "Should contain 'Ändring'", content.indexOf("Ändring") > -1
        assertEquals "Content differs", new String(response_content,charset), content

        mockServer.verify(request)
    }


    void NotestStarndardRequest() {
        makeStarndardRequest("utf-8");
    }

    void NotestWindows1252Encoding1() {
        makeStarndardRequest("Windows-1252");
    }

    void NOtestWindowsISO88591() {
        makeStarndardRequest("iso-8859-1");
    }

    void NotestWindows1252Encoding2() {
        String httpRequestPath = "/testEncoding";
        String testCharset = "Windows-1252"
        //String testCharset = "utf-8"
        RinfoService rinfoService = new RinfoService()

        HttpRequest request = request()
                                .withMethod("GET")
                                .withPath(httpRequestPath)

/*
        FileInputStream fileInputStream = new FileInputStream(TEST_RESOURCES_RELATIVE_PATH+"sfs.1999-175.konsoliderad.2011-05-02.xhtml")
        byte[] xmlFileAsBytes = new byte[60000];
        int checkSize = fileInputStream.read(xmlFileAsBytes);
        assertEquals "Correct filesize ", checkSize, 27087
*/

        byte[] xmlFileAsBytes = XML_CONTENT_WITH_1552_ENCODING.getBytes(testCharset)

        mockServer
                .when(request, Times.exactly(1))
                .respond(
                    response()
                        .withHeaders(
                            new Header("Content-Type", "text/html; charset=utf-8"),
                            //new Header("Content-Type", "text/html; charset="+testCharset),
                        )
                        .withBody(xmlFileAsBytes)
        );

        println "RinfoServiceIntegrationTests.testEncoding create getXHtmlContent"

        //String content = rinfoService.getXHtmlContent(httpRequestPath)
        String content = rinfoService.getXHtmlContentJavaVersion(httpRequestPath,xmlFileAsBytes, testCharset)

        assertTrue "Should contain 'Rättsinformationsförordning'", content.indexOf("Rättsinformationsförordning") > -1
        assertTrue "Should contain 'Ändring'", content.indexOf("Ändring") > -1
        assertEquals "Content differs", new String(xmlFileAsBytes, testCharset), content

        mockServer.verify(request)
   }

   void NotestRealExternalRequest() {
       //String url = "http://rinfo.test.lagrummet.se/publ/sfs/1999:175/konsolidering/2011-05-02"
       String httpRequestPath = "/publ/sfs/1999:175/konsolidering/2011-05-02"
       ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl = "http://rinfo.test.lagrummet.se"
       RinfoService rinfoService = new RinfoService()


       //String content = rinfoService.getXHtmlContentJavaVersion(httpRequestPath)
       String content = rinfoService.getXHtmlContentHttpBuilderVersion2(httpRequestPath)
       //String content = rinfoService.getXHtmlContent(httpRequestPath)

       println "RinfoServiceIntegrationTests.testRealExternalRequest TESTS"

       assertTrue "Should contain 'Rättsinformationsförordning'", content.indexOf("Rättsinformationsförordning") > -1
       assertTrue "Should contain 'Ändring'", content.indexOf("Ändring") > -1
       assertEquals "Content differs", new String(getResourceFile("sfs.1999-175.konsoliderad.2011-05-02.xhtml"), "Windows-1252"), content

   }

    void testSuccess() {

    }
}

/*        mockServer
                .when(
                request()
                        .withMethod("GET")
                        .withPath("/testEncoding")
                        .withQueryStringParameters(
                        new Parameter("returnUrl", "/account")
                )
                        .withCookies(
                        new Cookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
                )
                        .withBody(exact("{username: 'foo', password: 'bar'}")),
                Times.exactly(1)
        )
                .respond(
                response()
                        .withStatusCode(401)
                        .withHeaders(
                        new Header("Content-Type", "application/json; charset=utf-8"),
                        new Header("Cache-Control", "public, max-age=86400")
                )
                        .withBody("{ message: 'incorrect username and password combination' }")
                        .withDelay(new Delay(TimeUnit.SECONDS, 1))
        );

*/