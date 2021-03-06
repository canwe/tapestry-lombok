package net.sf.lombok.pages.testcomponents;

import net.sf.lombok.components.Tooltip;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;


public class TooltipDemo {

	@Inject
	@Path("context:images/PoweredByTapestry.gif")
	private Asset tapestryLogo;

	
    @Inject
	 private ComponentResources resources;

	@Component(parameters =
    { "tooltipContent=tooltipOverLogo", "xoffset=30", "yoffset=-30" })
    private Tooltip logoTT;

	@Component(parameters =
    { "tooltipContent=tooltipOverBike", "xoffset=10", "yoffset=-100","staticFlag=true" })
    private Tooltip bikeTT;

	@Component(parameters =
    { "tooltipContent=tooltipOverButton", "xoffset=10", "yoffset=10", "staticFlag=true",
    		"timeout=3000", "disabled=tooltipDisabled"})
    private Tooltip buttonTT;

	private  List<String> tooltipList;
	    
	private String message;
	
	private String inputMessage;
	
	@Persist
	private boolean tooltipDisabled;
	    
	
	public TooltipDemo(){
		tooltipList = new ArrayList<String>();
		tooltipList.add("One");
		tooltipList.add("Two");
		tooltipList.add("Three");
	}

	
	public String getTooltipOverLogo(){
		String msg = resources.getMessages().get("tooltipOverLogo");
		return msg;
		
	}
	
	public String getTooltipOverBike(){
		String msg = resources.getMessages().get("tooltipOverBike");
		return msg;
		
	}
	
	public String getTooltipOverButton(){
		String msg = resources.getMessages().get("tooltipOverButton");
		return msg;
		
	}
	
	
	public String getTooltipOverLink() {
		return "<span id='tooltip1'>A tooltip over link</span>";

	}

	public Asset getTapestryLogo() {
		return tapestryLogo;
	}

	public void setTapestryLogo(Asset tapestryLogo) {
		this.tapestryLogo = tapestryLogo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
	public  List<String> getTooltipList() {
		return tooltipList;
	}

	public void setTooltipList(List<String> tooltipList) {
          this.tooltipList = tooltipList;
	}

	public String getInputMessage() {
		return inputMessage;
	}

	public void setInputMessage(String inputMessage) {
		this.inputMessage = inputMessage;
	}

	 public String getTooltip(){
	        return  "<div id='tooltip1'>" + getMessage() + "</div>" ;
	    }

	
	@OnEvent(component = "buttonForm", value = "submit")	
	public void submit() {
		
		setTooltipDisabled(!isTooltipDisabled());
	}


	public boolean isTooltipDisabled() {
		return tooltipDisabled;
	}


	public void setTooltipDisabled(boolean tooltipDisabled) {
		this.tooltipDisabled = tooltipDisabled;
	}
	
}
