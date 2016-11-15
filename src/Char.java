/**
 * Character class with setters and getters
 * @author jrhinehart
 *
 */

public class Char {

	private String name;
	private int healthTotal;
	private int healthCurrent;
	private int strength;
	
	//Set Name
	public void setName(String Name) { 
		name = Name;
	}
	
	//Set Health randomly
	public void setHealth() {
		Double healthTotalDouble = ( Math.random() * 20 ) + 10 ;
		healthTotal = healthTotalDouble.intValue();
		setHealthCurrent(healthTotal);
	}
	
	//Set Strength randomly
	public void setStrength() {
		Double strengthDouble = ( Math.random() * 7 ) + 1 ;
		strength = strengthDouble.intValue();
	}
	
	//Get Name
	public String getName() {
		//System.out.println(name);
		return name;
	}
	
	//Get Health
	public int getHealth() {
		//System.out.println("Health: " + healthCurrent + "/" + healthTotal);
		return healthTotal;
	}
	
	/**
	 * @return the healthCurrent
	 */
	public int getHealthCurrent() {
		return healthCurrent;
	}

	/**
	 * @param healthCurrent the healthCurrent to set
	 */
	public void setHealthCurrent(int healthCurrent) {
		this.healthCurrent = healthCurrent;
	}

	//Get Strength for testing
	public int getStrength() {
		//System.out.println("Strength: " + strength);
		return strength;
	}
	
	// Returns health if not dead
	public String checkHealth() {
		String result = name + "'s health is " + getHealthCurrent() + " out of " + healthTotal;
		if (getHealthCurrent() <= 0) {
			result = name + " has died!";
			System.out.println(result);
			return result;
		} else {
			System.out.println(result);
			return result;
		}
	}
}
