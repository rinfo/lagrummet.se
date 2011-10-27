package se.lagrummet;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import org.compass.core.converter.ConversionException;
import org.compass.core.converter.basic.AbstractBasicConverter;
import org.compass.core.mapping.ResourcePropertyMapping;
import org.compass.core.marshall.MarshallingContext;

public class HtmlConverter extends AbstractBasicConverter<String> {

	@Override
	protected String doFromString(String arg0, ResourcePropertyMapping arg1,
			MarshallingContext arg2) throws ConversionException {
		return arg0;
	}

	@Override
	protected String doToString(String o,
			ResourcePropertyMapping resourcePropertyMapping,
			MarshallingContext context) {
		Source source = new Source(o);
		TextExtractor extractor = new TextExtractor(source);
		return extractor.toString();
	}
	
	
	
}