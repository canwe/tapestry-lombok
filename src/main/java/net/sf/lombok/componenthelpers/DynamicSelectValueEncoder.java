package net.sf.lombok.componenthelpers;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;

@SuppressWarnings("rawtypes")
public class DynamicSelectValueEncoder implements ValueEncoder {

	private SelectModel selectModel; 
	
		
	public DynamicSelectValueEncoder(SelectModel selectModel) {
		this.selectModel = selectModel;
	}

	public String toClient(Object value) {
		Integer index = selectModel.getOptions().indexOf(value);
		return index.toString();
	}

	public Object  toValue(String clientValue) {
		
		Integer index = Integer.parseInt(clientValue);
		OptionModel optionModel=selectModel.getOptions().get(index);
			
		
		return optionModel;
	}

}
