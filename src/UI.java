/**
 *  Creates Frames and re-draws the UI contents
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
//import java.awt.Image;
//import java.text.DecimalFormat;
/**
 * @author jrhinehart
 *  Does all the dirty work of building and updating the UI
 */
public class UI implements Runnable {
	JFrame mainframe;
	private JLabel titleLabel;
	private JPanel imagePanel;
	private JTextArea playerInfoArea;
	private JTextArea npcInfoArea;
	private JPanel charInfoPanel;
	private JLabel statusLabel;
	private ImageIcon playerCharIcon;
	private ImageIcon npcCharIcon;
	private JLabel npcIconLabel;
	private GridBagConstraints c;
	private JTextField nameEntryField;
	private JButton createCharButton;
	private JButton startFightButton;
	private JButton continueButton;
	private JButton endButton;
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
	public class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Create")) {
				String playerName = nameEntryField.getText();
				RpgFight.createChars(playerName);
				drawPreFight();
			} else if (command.equals("Fight")) {
				drawDuringFight();
				RpgFight.runGameLoop();
			} else if (command.equals("End")) {
				System.exit(0);
			} else if (command.equals("Continue")) {
				RpgFight.player.setHealthCurrent(RpgFight.player.getHealth());
				RpgFight.npc.setHealthCurrent(RpgFight.npc.getHealth());
				
				CreateChar characters = new CreateChar();
				RpgFight.npc = characters.createEnemy();
				updateNpcInfo();
				
				imagePanel.remove(npcIconLabel);
				imagePanel.revalidate();
				c.ipadx = 0;
				c.gridx = 2;
				c.gridy = 1;
				imagePanel.add(new JLabel(npcCharIcon),c);
				imagePanel.repaint();
				
				drawPreFight();
				drawDuringFight();
				mainframe.revalidate();
				RpgFight.runGameLoop();
			}
		}
	}
	
	public void run() {
		drawMainframe(); 
		//Thread t = new Thread(new UI());
	}
	
	public void drawMainframe() {
		// TODO Generate Main frame and layout grid
		mainframe = new JFrame("MUD Wrestling");
		mainframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		mainframe.setSize(400,550);
		mainframe.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		JPanel topPane = new JPanel();
		titleLabel = new JLabel("Today's Fighters:",JLabel.CENTER);
		topPane.add(titleLabel);
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 20;
		c.gridheight = 1;
		c.gridwidth = 3;
		mainframe.add(topPane,c);
		
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridBagLayout());
		playerCharIcon = createImageIcon("/images/knight.png","Basic Character Icon");
		JLabel playerIconLabel = new JLabel(playerCharIcon);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.ipady = 0;
		imagePanel.add(playerIconLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 60;
		imagePanel.add(new JLabel("    vs.   "),c);
		npcCharIcon = createImageIcon("/images/orc.png","NPC Icon");
		npcIconLabel = new JLabel(npcCharIcon);
		c.ipadx = 0;
		c.gridx = 2;
		c.gridy = 1;
		imagePanel.add(npcIconLabel,c);
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 3;
		mainframe.add(imagePanel,c);
		
		charInfoPanel = new JPanel();
		charInfoPanel.setLayout(new GridBagLayout());
		playerInfoArea = new JTextArea("??????\nHealth: ?????\nStrength: ??");
		playerInfoArea.setEditable(false);
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 1;
		charInfoPanel.add(playerInfoArea,c);
		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 1;
		c.ipadx = 70;
		charInfoPanel.add(new JLabel("        "),c); //Spacer
		npcInfoArea = new JTextArea("????\nHealth: ?????\nStrength: ??");
		npcInfoArea.setEditable(false);
		c.ipadx = 0;
		c.gridy = 2;
		c.gridx = 2;
		c.gridwidth = 1;
		charInfoPanel.add(npcInfoArea,c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		mainframe.add(charInfoPanel,c);
		
		statusLabel = new JLabel("Enter the name for your Character");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.ipady = 50;
		mainframe.add(statusLabel,c);
		
		nameEntryField = new JTextField("                ");
		nameEntryField.setHorizontalAlignment(JTextField.CENTER);
		nameEntryField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
                // TODO Auto-generated method stub
                nameEntryField.setText("");
            }
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			}
		});
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 4;
		c.ipady = 5;
		mainframe.add(nameEntryField,c);
		
		createCharButton = new JButton("Create");
		createCharButton.setActionCommand("Create");
		createCharButton.addActionListener(new ButtonClickListener());
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 5;
		c.ipady = 10;
		mainframe.add(createCharButton,c);
		mainframe.setVisible(true);
	}
	
	public void drawPreFight() {
		//TODO remove elements, replace with pre-fight layout and contents
		updatePlayerInfo();
		updateNpcInfo();
		//Replace NPC icon with correct icon
		
		switch (RpgFight.npc.getName()) {
			case "Zombie": npcCharIcon = createImageIcon("/images/zombie.png","Zombie");
				break;
			case "Orc": npcCharIcon = createImageIcon("/images/orc.png","Orc");
				break;
			case "Bear": npcCharIcon = createImageIcon("/images/pedo-bear2.png","Bear");
				break;
			case "Large Wolf": npcCharIcon = createImageIcon("/images/wolf2.png","Wolf");
				break;
			case "Wildman": npcCharIcon = createImageIcon("/images/caveman.png","Wildman");
				break;
			case "Giant": npcCharIcon = createImageIcon("/images/giant.png","Giant");
				break;
		}
		imagePanel.remove(npcIconLabel);
		c.ipadx = 0;
		c.gridx = 2;
		c.gridy = 1;
		imagePanel.add(new JLabel(npcCharIcon),c);
		imagePanel.repaint();
		
		mainframe.remove(nameEntryField);
		mainframe.remove(createCharButton);
		statusLabel.setText("Get Ready...");
		
		startFightButton = new JButton("Fight!");
		startFightButton.setActionCommand("Fight");
		startFightButton.addActionListener(new ButtonClickListener());
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 4;
		c.ipady = 5;
		mainframe.add(startFightButton,c);
		mainframe.revalidate();
	}
	

	public void drawDuringFight() {
		mainframe.remove(startFightButton);
		mainframe.repaint();
		mainframe.revalidate();
	}
	
	public void drawPostFight() {
		continueButton = new JButton("Again?");
		continueButton.setActionCommand("Continue");
		continueButton.addActionListener(new ButtonClickListener());
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 4;
		c.ipady = 5;
		c.ipadx = 50;
		mainframe.add(continueButton,c);
		endButton = new JButton("I'm done");
		endButton.setActionCommand("End");
		endButton.addActionListener(new ButtonClickListener());
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 4;
		c.ipady = 5;
		c.ipadx = 50;
		mainframe.add(endButton,c);
		mainframe.revalidate();
	}
	
	public void updatePlayerInfo() {
		playerInfoArea.setText(RpgFight.player.getName() + "\nHealth: " + RpgFight.player.getHealthCurrent() + "/" + RpgFight.player.getHealth() + "\nStrength: " + RpgFight.player.getStrength());
		playerInfoArea.revalidate();
		//System.out.println("updatePlayerInfo- mainframe.revalidate called");
	}
	
	public void updateNpcInfo() {
		npcInfoArea.setText(RpgFight.npc.getName() + "\nHealth: " + RpgFight.npc.getHealthCurrent() + "/" + RpgFight.npc.getHealth() + "\nStrength: " + RpgFight.npc.getStrength());
		npcInfoArea.repaint();
		charInfoPanel.repaint();
		npcInfoArea.revalidate();
	}
	
	public void updateStatusLabel(String status) {
		statusLabel.setText(status);
		statusLabel.repaint();
		mainframe.repaint();
		charInfoPanel.revalidate();
	}

}
