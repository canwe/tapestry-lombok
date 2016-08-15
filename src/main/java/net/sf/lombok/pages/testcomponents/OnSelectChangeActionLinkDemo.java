package net.sf.lombok.pages.testcomponents;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class OnSelectChangeActionLinkDemo {

	@Persist(PersistenceConstants.FLASH)
	@Property
	private int age;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String city;
	
	@Persist(PersistenceConstants.FLASH)
	@Property
	private InputMode mode;
	
	@Inject
	private ComponentResources resources;

	@Inject
	private Block ageBlock;

	@Inject
	private Block cityBlock;

	@SuppressWarnings("unused")
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean showOutput;
	
	public enum InputMode {
		AGE, CITY
	};

	public SelectModel getInputModeModel() {
		return new EnumSelectModel(InputMode.class, resources.getMessages());

	}

	/**
	 * 
	 * This method is called when the select change values.
	 * Note that the form is not submitted when this method is 
	 * triggered. modelSelect is the is of select element.
	 * @param selectecValue choose value in the drop down list
	 */
	void onActionFromModeSelect(InputMode selectedValue) {
		mode = selectedValue;
	}

	void beginRender() {
		if (mode == null) {
			mode=InputMode.AGE;
		}
	}

	public Block getBlockToRender(){

        Block blockToRender = null;
		switch (mode) {
		case AGE:
			blockToRender = ageBlock;
			break;
		case CITY:
			blockToRender = cityBlock;
			break;
		}
		return blockToRender;
		
	}
	
	void onSuccessFromMyForm(){
		
		showOutput = true;
	}
	
	public String getOutput(){
		StringBuilder sb = new StringBuilder();
		switch (mode) {
		case AGE:
			sb.append("age:" + age);
			break;
		case CITY:
			sb.append("city:" + city);
			break;
		}
		return sb.toString();
	}

}
