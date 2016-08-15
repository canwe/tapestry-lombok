package net.sf.lombok.pages.testcomponents;

import java.text.DecimalFormat;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;

public class N2WTextFieldDemo2 {

	@Component
	private Form form;
	
	@Persist
	private double myNumber;

	
	@Persist("flash")
	private boolean showOutput;
	
	public double getMyNumber() {
		return myNumber;
	}

	public void setMyNumber(double myNumber) {
		this.myNumber = myNumber;
	}

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
	
	String onToClientFromAsset()
	  {
	    DecimalFormat formatter = new DecimalFormat("#.00");
        String str = formatter.format(getMyNumber());
	    return  str;
	  }
}
