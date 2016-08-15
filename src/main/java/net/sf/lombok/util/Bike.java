package net.sf.lombok.util;

public class Bike {
	private String name;

	private String engineCapacity;

	private int maxSpeed;

		
	public Bike(String name, String engineCapacity, int maxSpeed) {		
		this.name = name;
		this.engineCapacity = engineCapacity;
		this.maxSpeed = maxSpeed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(String engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public boolean equals(Object obj){
		
		
		if (obj ==null || !(obj instanceof Bike )){
			return false;
		}
		
		Bike bike = (Bike) obj;
		if (!name.equals(bike.getName())){
			return false;
		}
		
		if (!engineCapacity.equals(bike.getEngineCapacity())){
			return false;
		}
		if (maxSpeed != bike.getMaxSpeed()){
			return false;
		}
		
		return true;
	}

}
