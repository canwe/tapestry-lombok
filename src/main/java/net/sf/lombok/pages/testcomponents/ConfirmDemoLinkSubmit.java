package net.sf.lombok.pages.testcomponents;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

public class ConfirmDemoLinkSubmit {
	
	@Component
	private Form form;
	
	@SuppressWarnings("unused")
	@Property @Persist("flash")
	private String name; 
	
	@Persist("flash")
	private boolean showOutput; 
	
	public void  onSubmitFromForm(){
		showOutput = true;
		if (form.getHasErrors()){
			showOutput=false;
		}
	}
	
	public boolean isShowOutput(){
		return showOutput;
	}

}
