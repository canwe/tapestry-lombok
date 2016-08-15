package net.sf.lombok.pages.testcomponents;

import org.apache.tapestry5.annotations.Persist;

public class ColorPickerLoopDemo {

	@Persist
	private String colors[];
	
	private int index;

	
	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	void beginRender(){
		
		if (getColors() == null){
			setColors(new String[3]);
		}
		
	}
	
	public boolean isShowColors(){
    	boolean returnValue = true;
        String [] colors = getColors();
        if (colors[0]==null && colors[1] == null && colors[2]==null){
        	returnValue=false;
        }
        return returnValue;
    }
	
	public void setColor(String color){
		  colors[getIndex()] = color;
	}
	
	public String getColor(){
		return colors[getIndex()];
	}
	
}
