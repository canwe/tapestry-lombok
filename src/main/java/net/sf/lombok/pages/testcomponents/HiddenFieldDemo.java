package net.sf.lombok.pages.testcomponents;

import org.apache.tapestry5.annotations.Persist;

public class HiddenFieldDemo {

	@Persist
	private String message="heelo";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	void beginRender(){
		setMessage("fee");
	}
	
}
