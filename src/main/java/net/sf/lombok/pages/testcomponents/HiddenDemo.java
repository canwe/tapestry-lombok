package net.sf.lombok.pages.testcomponents;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.Property;

public class HiddenDemo {
	private static final Logger log = Logger.getLogger(HiddenDemo.class);
	
	@Property
	private String code;
	

	public void beginRender(){
		if (code == null) {
			code ="hello";
		}
	}
	
	public void  onSubmitFromMyForm(){
		log.info("omsubmitFromMyForm : code =" + code);
	}

}
