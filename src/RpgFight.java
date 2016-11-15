import javax.swing.SwingUtilities;

/**
 * @author jrhinehart
 * A simple character creation and dice-rolling fight against an NPC
 */

public class RpgFight implements Runnable {

	//private static Scanner scanner = new Scanner(System.in);
	private static boolean fightContinues = false;
	public static boolean gameOn = true;
	private static int roundNum;
	//private static CreateChar characters;
	public static Char player;
	public static Char npc;
	public static UI mainUI = new UI();
	
	public static void main(String[] args) throws InterruptedException {
		(new Thread(new RpgFight())).start();
		//runGameLoop();  called from UI?
	}
	
	public void run() {
    	mainUI.drawMainframe();
    }
	
	/*
    public static void createAndShowJFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            
        });
    }
	 */
	public static void runGameLoop() { // Needed for
																	// Thread.sleep
			// Start the Fight!
			roundNum = 0;
			fightContinues = true;

			// Fight cycle
			while (fightContinues) {
				roundNum++;
				//System.out.println("\n   Round " + roundNum + ":");
				double p1acc = (Math.random() * 2);
				double p2acc = (Math.random() * 2);
				int p1Dam = (int) (p1acc * player.getStrength());
				int p2Dam = (int) (p2acc * npc.getStrength());
				player.setHealthCurrent(player.getHealthCurrent() - p2Dam);
				npc.setHealthCurrent(npc.getHealthCurrent() - p1Dam);
				mainUI.updateStatusLabel("Round " + roundNum + ":\n" + player.getName() + " does " + p1Dam
						+ " damage!\n" + npc.getName() + " does " + p2Dam
						+ " damage!");
				System.out.println("Round " + roundNum + ":\n" + player.getName() + " does " + p1Dam
						+ " damage!\n" + npc.getName() + " does " + p2Dam
						+ " damage!");  //Console output for debug
				mainUI.updatePlayerInfo();
				mainUI.updateNpcInfo();
				mainUI.mainframe.revalidate();
				
				System.out.println(player.getName() + ": "
						+ player.getHealthCurrent() + " ---- " + npc.getName()
						+ ": " + npc.getHealthCurrent());

				//Compartmentalize damage check as own method?
				checkForDead();
				
				mainUI.mainframe.revalidate();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep Interrupted");
					e.printStackTrace();
				}
			}

			// Query for another round
			System.out.println("Would you like to go for another round? [y/n]");
			mainUI.drawPostFight();
		}
	
	public static void checkForDead() {
		if ((player.getHealthCurrent() < 0)
				& (npc.getHealthCurrent() < 0)) {
			System.out.println("It's a draw!");
			mainUI.updateStatusLabel("It's a draw!");
			roundNum = 0;
			fightContinues = false;
		} else if (player.getHealthCurrent() <= 0) {
			System.out.println(npc.getName() + " wins with "
					+ npc.getHealthCurrent() + " health remaining!");
			mainUI.updateStatusLabel(npc.getName() + " wins with "
					+ npc.getHealthCurrent() + " health remaining!");
			roundNum = 0;
			fightContinues = false;
		} else if (npc.getHealthCurrent() <= 0) {
			System.out.println(player.getName() + " wins with "
					+ player.getHealthCurrent()
					+ " health remaining! Woot!!");
			mainUI.updateStatusLabel(player.getName() + " wins with "
					+ player.getHealthCurrent()
					+ " health remaining! Woot!!");
			roundNum = 0;
			fightContinues = false;
		} else if (roundNum > 10) {
			System.out.println("It's a draw, get on with it!");
			mainUI.updateStatusLabel("It's a draw, get on with it!");
			roundNum = 0;
			fightContinues = false;
		}
	}
	
	public static void createChars(String playerName) {
		CreateChar characters = new CreateChar();
		player = characters.createPlayer(playerName);
		npc = characters.createEnemy();
		System.out.println("Our Fighters: " + player.getName() + " vs. "
				+ npc.getName() + "!");
	}
}
