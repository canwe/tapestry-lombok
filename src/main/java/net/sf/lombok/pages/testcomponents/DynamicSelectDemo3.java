package net.sf.lombok.pages.testcomponents;

import java.util.ArrayList;
import java.util.List;

import net.sf.lombok.util.BMW;
import net.sf.lombok.util.HARLEY;
import net.sf.lombok.util.HONDA;
import net.sf.lombok.util.YAMAHA;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;


@SuppressWarnings("unused")
public class DynamicSelectDemo3 {

	@Inject
	private ComponentResources resources;

	@Property
	@Persist("flash")
	private String make;

	@Property
	@Persist("flash")
	// Note that bike could be a enum BMW, HARLEY,HONDA or YAMAHA.
	private Object bike;

	public List<SelectModel> getChildSelectModels() {
		List<SelectModel> models = new ArrayList<SelectModel>();
		models.add(new EnumSelectModel(BMW.class, resources.getMessages()));
		models.add(new EnumSelectModel(HARLEY.class, resources.getMessages()));
		models.add(new EnumSelectModel(HONDA.class, resources.getMessages()));
		models.add(new EnumSelectModel(YAMAHA.class, resources.getMessages()));
		return models;
	}

}
