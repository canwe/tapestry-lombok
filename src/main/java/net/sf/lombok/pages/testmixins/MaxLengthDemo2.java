package net.sf.lombok.pages.testmixins;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

public class MaxLengthDemo2 {


	@Property
	@Persist(PersistenceConstants.FLASH)
	private String note;
	
	@Component(id = "myForm")  
	private Form form;
	
	@SuppressWarnings("unused")
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean showOutput;
	
	public void beginRender(){
    	if (note==null){
    		note = "Pls enter at most 30 chars.";
    	}
    }

	public void  onSubmitFromMyForm(){
		showOutput = !form.getHasErrors(); 
	}
}
