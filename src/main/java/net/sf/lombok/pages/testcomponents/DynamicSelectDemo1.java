package net.sf.lombok.pages.testcomponents;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;

@SuppressWarnings("unused")
public class DynamicSelectDemo1 {

	private final String [] BMWS = {"F650", "K110RS", "R100RT", "R80GS"} ;
		
	private final String [] HARLEYS = {"Fat Boy", "FXD Superglide", "R100RT", "R80GS"} ;

	private final String [] HONDAS = {"CBR600F", "FMX650", "Goldwing", "VFR800","VTR1000F"} ;
	
	private final String [] YAMAHAS = {"Fazer 600", "VMax", "YZF-R1", "YZF-R6"} ;
	
	
  
	@Property
    @Persist("flash")
	private String make;	
	
    @Property
    @Persist("flash")
    private String bike;
    
	public List<SelectModel> getChildSelectModels()
	{
		List<SelectModel> models = new ArrayList<SelectModel>();
		models.add(getSelectModle(BMWS));
		models.add(getSelectModle(HARLEYS));
		models.add(getSelectModle(HONDAS));
		models.add(getSelectModle(YAMAHAS));		
		return models;
	}
	
	
	protected SelectModel getSelectModle(String [] values)
	{
		return new SelectModelImpl(null, getOptionModels(values));
	}
	
	protected List<OptionModel> getOptionModels(String [] values){
		
		
		List<OptionModel> optionModels = new ArrayList<OptionModel>();
		for (int i=0; i < values.length; i++)
		{
			optionModels.add(new OptionModelImpl(values[i]));
		}
		
		return optionModels;
	}
	
	public List<OptionModel> getDefaultOptions(){
		List<OptionModel> optionModels = new ArrayList<OptionModel>();
		optionModels.add(new OptionModelImpl(BMWS[0]));
		optionModels.add(new OptionModelImpl(HARLEYS[1]));
		optionModels.add(new OptionModelImpl(HONDAS[2]));
		optionModels.add(new OptionModelImpl(YAMAHAS[3]));
		
		return optionModels;
		
	}
	
}
