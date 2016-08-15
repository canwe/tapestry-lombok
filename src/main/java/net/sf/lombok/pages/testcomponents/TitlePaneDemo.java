package net.sf.lombok.pages.testcomponents;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;

public class TitlePaneDemo {

	
	@Inject
	@Path("context:/images/switcher_close.gif")
	private Asset closeImg;
	
	@Inject
	@Path("context:/images/switcher_open.gif")
	private Asset openImg;

	public Asset getCloseImg() {
		return closeImg;
	}

	public Asset getOpenImg() {
		return openImg;
	}
	
	
	
}
