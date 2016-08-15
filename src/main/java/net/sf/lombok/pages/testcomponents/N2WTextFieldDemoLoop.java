package net.sf.lombok.pages.testcomponents;

import java.text.DecimalFormat;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;

public class N2WTextFieldDemoLoop {
	
	private static final Double[] myArray = new Double [] {
		new Double(10011), new Double(23451), new Double(50012)  };
	
	private int loopIndex;
	
	@Component
	private Form form;
	
	@Persist
	private Double [] myNumbers;

	
	@Persist("flash")
	private boolean showOutput;

	public void  onSubmitFromForm(){
		setShowOutput(true);
		if (form.getHasErrors()){
			setShowOutput(false);
		}
	}

	public boolean isShowOutput() {
		return showOutput;
	}

	public void setShowOutput(boolean showOutput) {
		this.showOutput = showOutput;
	}
	
	public boolean beginRender() {

	    if (getMyNumbers() ==null){	   
	    	 setMyNumbers(myArray);
	    }
	    return true;
	}

	public Double[] getMyNumbers() {
		return myNumbers;
	}

	public void setMyNumbers(Double[] myNumbers) {
		this.myNumbers = myNumbers;
	}

	public int getLoopIndex() {
		return loopIndex;
	}

	public void setLoopIndex(int loopIndex) {
		this.loopIndex = loopIndex;
	}
	
	public String getErrorMsg(){
		return "Please enter asset " +  getLoopIndex() + " as a number.";
	}

	public Double getLoopValue() {
	    int index = getLoopIndex();		
		return  getMyNumbers()[index];
	}

	public void setLoopValue(Double loopValue) {
		getMyNumbers()[getLoopIndex()]=loopValue;
	}
	
	public String getDisplayName(){
		return "Asset " + getLoopIndex(); 
	}
	
	String onToClientFromAsset()
	  {
	    DecimalFormat formatter = new DecimalFormat("#0.00");
      String str = formatter.format(getLoopValue());
	    return  str;
	  }
	
}
