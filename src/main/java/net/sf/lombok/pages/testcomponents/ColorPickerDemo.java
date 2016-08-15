package net.sf.lombok.pages.testcomponents;

import java.text.Format;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.Persist;

public class ColorPickerDemo {
	  private static final Logger log = Logger.getLogger(ColorPickerDemo.class);
	
	  @Persist
	  private String color;

	  @Persist
	  private String color2;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	

	public void  onSubmitFromMyForm(){
		log.info("omsubmitFromForm : agree =" + getColor());
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	
	public Format getFormater(){
		return new MessageFormat("{0}");
	}
}
