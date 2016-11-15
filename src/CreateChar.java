/*
 * Constructor for creating characters with random stats
 * in the case of player characters, a provided name is used
 *  author: @jrhinehart
 */
public class CreateChar {
	
	String playerName;
	String[] enemyNames = {"Orc","Large Wolf","Bear","Wildman","Giant","Zombie"};
	String enemyName = "DefaultEnemy"; //for debugging
	
	
	//Creates Player character
	public Char createPlayer(String name) {
		Char player = new Char();
		player.setName(name);
		player.setHealth();
		player.setStrength();
		//Announce Stats  -- This could move to main
		System.out.println(player.getName());
		System.out.println("Health: " + player.getHealthCurrent());
		System.out.println("Strength: " + player.getStrength());
		return player;
	}
	
	//Randomly chooses Enemy Name
	public String RandName(String[] array) {
		int rnd = (int)(Math.random() * array.length);
		String en_name = array[rnd];
		return en_name;
	}
	
	//Creates Enemy character
	public Char createEnemy() {
		Char npc = new Char();
		String enemyName = RandName(enemyNames);
		npc.setName(enemyName);
		npc.setHealth();
		npc.setStrength();
		//Announce Stats   -- This could move to main
		System.out.println(npc.getName());
		System.out.println("Health: " + npc.getHealth());
		System.out.println("Strength: " + npc.getStrength());
		return npc;
	}
}
