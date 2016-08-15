package net.sf.lombok.pages.testcomponents;

import org.apache.log4j.Logger;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

public class WatermarkLoopDemo {
	private static Logger log = Logger.getLogger(WatermarkLoopDemo.class);

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String[] words;
	

	@Component(id = "myForm")  
	private Form form;
	
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean showOutput;

	
	private String word;
	
	@Property
	private int ind;
	
	public void beginRender(){
		if (words==null){
			words= new String[3];
		}
	}
	
	public void onPrepareForSubmit() {
		 words = new String[3];
	     	
	}
	public void  onSubmitFromMyForm(){
		showOutput = !form.getHasErrors(); 
		log.info("words=" + words.toString());
	}
	

	public String getMsg(){
		return "Word " + (ind + 1) + ": Please enter a word.";
	}
	
	public String getLabel(){
		return "Word " + (ind + 1) ;
	}
	
	public void setWord(String wordIn){
		words[ind] = wordIn;
	}
	
	public String getWord(){
		return words[ind];
	}
}
