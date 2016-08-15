package net.sf.lombok.pages.testmixins;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

@SuppressWarnings("unused")
public class MaskedInputDemo {

	@Component(id = "myForm")  
	private Form form;
	
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean showOutput;
		
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String phoneNo;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String phoneNoPExt;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String socialSecurityNumber;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String postCode;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String productKey;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String custom;
	
	public void  onSubmitFromMyForm(){
		showOutput = !form.getHasErrors(); 
	}
	
	public Map<String, String> getMyDefinitions(){
		Map<String, String> defns = new HashMap<String,String>();
		defns.put("~", "+-");
		
		return defns;
	}
}
