package net.sf.lombok.pages.testcomponents;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.Persist;

public class CheckboxGroupDemo {
    
	private final Logger log = Logger.getLogger(CheckboxGroupDemo.class);
    
	private final String[] colors = { "Blue", "Red", "Green" };

	private String color;

	@Persist
	private Set<String> selectedColors;
	
	public void onSubmitFromMyForm() {
		// Do something with seletedColors
         log.info("In method onSubmitFromMyForm"); 
	}

	public boolean beginRender() {
        if (getSelectedColors() == null){
        	setSelectedColors(new HashSet<String>());
        }
        
        return true;
	}

	public boolean isSelected() {
		return getSelectedColors().contains(getColor());
	}

	/**
	 * Add color to the selected set if selected.
	 */
	public void setSelected(boolean selected) {
		if (selected) {
			getSelectedColors().add(getColor());
		} else {
			getSelectedColors().remove(getColor());
		}
	}

	public String[] getColors() {
		return colors;
	}

	public String getSelectedColorsAsStr() {
		String returnValue = null;
		Iterator<String> it = getSelectedColors().iterator();

		StringBuffer sb = new StringBuffer();
		int size = getSelectedColors().size();
		int count = 0;

		while (it.hasNext()) {
			sb.append(it.next());
			count++;
			if (count < size) {
				sb.append(",");
			}
		}

		if (sb.length() > 0) {
			returnValue = sb.toString();
		}
		return returnValue;
	}

	public Set<String> getSelectedColors() {
		
		return selectedColors;
	}

	public void setSelectedColors(Set<String> selectedColors) {
		this.selectedColors = selectedColors;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
