package net.sf.lombok.pages.testcomponents;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;


public class SyntaxHighlighterDemo {

	public String getHtmlCode(){
		String str = 
		
		"<html>\n" +

		"<head>\n" + 
		" <title></title>\n" +
		"  <meta name=\"GENERATOR\" content=\"Quanta Plus\">\n" +
		"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" +
		"</head>\n" +
		"<body>\n"  +

		"   Hello world !\n" +
		"</body>\n" +
		"</html>";

		 
	   return StringEscapeUtils.escapeHtml4(str);
	}
	
	
}
