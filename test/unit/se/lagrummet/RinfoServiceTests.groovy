package se.lagrummet

import grails.test.*

class RinfoServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {

    }

    void testGetXHtmlContentJavaVersionWithUTF8() {
        def final inputString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\"\n" +
                "    \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "    xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\n" +
                "    xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
                "    version=\"XHTML+RDFa 1.0\" xml:lang=\"en\">\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "</html>";
        def final inputStream = new ByteArrayInputStream(inputString.getBytes())

        def service = new RinfoService()
        def xhtml = service.getXHtmlContentJavaVersion(inputStream)

        assertEquals xhtml, inputString
    }
    void testGetXHtmlContentJavaVersionWithISO8859() {
        def final inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\"\n" +
                "    \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "    xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\n" +
                "    xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
                "    version=\"XHTML+RDFa 1.0\" xml:lang=\"en\">\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "</html>";
        def final inputStream = new ByteArrayInputStream(inputString.getBytes())

        def service = new RinfoService()
        def xhtml = service.getXHtmlContentJavaVersion(inputStream)

        assertFalse xhtml == inputString
    }

    void testShouldBeAbleToReadALongDocument() {
        def final inputStringTop = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\"\n" +
                "    \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "    xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\n" +
                "    xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
                "    version=\"XHTML+RDFa 1.0\" xml:lang=\"en\">\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n";
        def final inputStringBottom = "</html>";

        def inputString = inputStringTop.padRight(150000 , 'A')
        inputString = inputString.concat(inputStringBottom)
        def service = new RinfoService()
        def xhtml = service.getXHtmlContentJavaVersion(new ByteArrayInputStream(inputString.getBytes()))

        assertTrue(xhtml.length() >= inputString.length())
    }
}
