package net.sf.lombok.pages.testcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.lombok.util.Bike;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;



@SuppressWarnings("unused")
public class DynamicSelectDemo2 {

	
	@Property
	@Persist("flash")
	private String make;

	@Property
	@Persist("flash")
	private Bike bike;

	private List<SelectModel> childSelectModels;

	public List<SelectModel> getChildSelectModels() {
		if (childSelectModels == null) {
			childSelectModels = new ArrayList<SelectModel>();
			childSelectModels.add(getSelectModle(getBMWs()));
			childSelectModels.add(getSelectModle(getHDs()));
			childSelectModels.add(getSelectModle(getHondas()));
			childSelectModels.add(getSelectModle(getYamahas()));
		}
		return childSelectModels;
	}

	protected SelectModel getSelectModle(List<Bike> bikes) {
		return new SelectModelImpl(null, getOptionModels(bikes));
	}

	protected List<OptionModel> getOptionModels(List<Bike> bikes) {

		List<OptionModel> optionModels = new ArrayList<OptionModel>();
		for (Iterator<Bike> it = bikes.iterator(); it.hasNext();) {
			Bike bike = it.next();
			optionModels.add(new OptionModelImpl(bike.getName(), bike));
		}

		return optionModels;
	}

	private List<Bike> getBMWs() {
		List<Bike> list = new ArrayList<Bike>();
		list.add(new Bike("F650", "650cc", 100));
		list.add(new Bike("K1100RS", "1100cc", 220));
		list.add(new Bike("R100RT", "1000cc", 200));
		list.add(new Bike("R100RT", "1000cc", 200));
		list.add(new Bike("R80GS", "800cc", 180));
		return list;
	}

	private List<Bike> getHDs() {
		List<Bike> list = new ArrayList<Bike>();
		list.add(new Bike("Fat Boy", "700cc", 120));
		list.add(new Bike("FXD Superglide", "1100cc", 150));
		list.add(new Bike("Sportster", "850cc", 150));
		return list;
	}

	private List<Bike> getHondas() {
		List<Bike> list = new ArrayList<Bike>();
		list.add(new Bike("CBR600F", "600cc", 250));
		list.add(new Bike("FMX650", "650cc", 150));
		list.add(new Bike("GoldWing", "1500cc", 260));
		list.add(new Bike("VFR800", "800cc", 260));
		list.add(new Bike("VTR1000", "1000cc", 250));
		return list;
	}

	private List<Bike> getYamahas() {
		List<Bike> list = new ArrayList<Bike>();
		list.add(new Bike("Fazer 600", "600cc", 200));
		list.add(new Bike("VMax", "1200cc", 180));
		list.add(new Bike("YZR-R1", "1000cc", 250));
		list.add(new Bike("YZR-R6", "600cc", 240));
		return list;
	}

	
}
