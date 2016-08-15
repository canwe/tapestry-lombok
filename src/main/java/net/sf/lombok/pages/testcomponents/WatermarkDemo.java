package net.sf.lombok.pages.testcomponents;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

public class WatermarkDemo {

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String word;
	

	@Property
	@Persist(PersistenceConstants.FLASH)
	private Double quantity;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String note;
	
	@Component(id = "myForm")  
	private Form form;
	
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean showOutput;
	

	public void  onSubmitFromMyForm(){
		showOutput = !form.getHasErrors(); 
	}
}
