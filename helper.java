import java.awt.*;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
public class helper {
	public static void click() { //simulate Left Button Click with delay from "pause"			
		Robot bot = null;
		  try {
		   bot = new Robot();
		  } catch (Exception failed) {
		   System.err.println("Failed instantiating Robot: " + failed);
		  }
		  int mask = InputEvent.BUTTON1_DOWN_MASK;
		 
		  bot.mousePress(mask);
		  bot.mouseRelease(mask);
		  pause(150);
	}
	public static void dosation(int dose) {
		HashMap<Integer, Point> dosePositions = new HashMap<>();
	    dosePositions.put(1, new Point(95, 62));
	    dosePositions.put(5, new Point(120, 70));
	    dosePositions.put(10, new Point(160, 70));
	    dosePositions.put(15, new Point(200, 70));
	    dosePositions.put(20, new Point(220, 70));
	    dosePositions.put(25, new Point(250, 70));
	    dosePositions.put(30, new Point(280, 70));
	    dosePositions.put(100, new Point(380, 70));	    
	    Robot bot = null;
	    try {
	        bot = new Robot();
	    } catch (Exception failed) {
	        System.err.println("Failed instantiating Robot: " + failed);
	    }
	    
	    Point dosePoint = dosePositions.get(dose);
	    if (dosePoint != null) {
	        bot.mouseMove(dosePoint.x, dosePoint.y);
	        click();
	    }
	}
	public static void addChem(String name) {
	    Map<String, Point> chemCoords = new HashMap<>();
	    chemCoords.put("carbon", new Point(165, 90));
	    chemCoords.put("oxygen", new Point(50, 175));
	    chemCoords.put("sugar", new Point(270, 220));
	    chemCoords.put("chlorine", new Point(270, 100));
	    chemCoords.put("phosphorus", new Point(150, 175));
	    chemCoords.put("potassium", new Point(270, 175));
	    chemCoords.put("radium", new Point(350, 175));
	    chemCoords.put("silicon", new Point(50, 220));
	    chemCoords.put("sulfur", new Point(370, 220));
	    chemCoords.put("nitrogen", new Point(370, 160));
	    chemCoords.put("ethanol", new Point(50, 130));
	    chemCoords.put("iron", new Point(50, 150));
	    chemCoords.put("lithium", new Point(165, 150));
	    chemCoords.put("mercury", new Point(270, 150));
	    chemCoords.put("fluorine", new Point(162, 125));
	    chemCoords.put("hydrogen", new Point(275, 125));
	    chemCoords.put("sodium", new Point(165, 215));
	    chemCoords.put("eject", new Point(170, 245));
	    Robot bot = null;
	    try {
	        bot = new Robot();
	    } catch (Exception failed) {
	        System.err.println("Failed instantiating Robot: " + failed);
	    }

	    Point coords = chemCoords.get(name);
	    if (coords != null) {
	        bot.mouseMove(coords.x, coords.y);
	        click();
	    }
	}
	public static void createMed(String name) {// make any drugs using receipts
		    switch(name) {
			case "Bicardine":
				  dosation(15);
				  addChem("oxygen");
				  addChem("sugar");
				  addChem("carbon");
				  addChem("carbon");
				  addChem("carbon");
				  addChem("carbon");
				  addChem("eject");
				break;
			case "Dermaline":
				  dosation(15);
				  addChem("silicon");
				  addChem("carbon");
				  addChem("oxygen");
				  addChem("oxygen");
				  addChem("phosphorus");
				  addChem("phosphorus");
				  addChem("eject");
				  break;
			case "Dylovene":
				  dosation(30);
				  addChem("silicon");
				  addChem("potassium");
				  addChem("nitrogen");
				  addChem("eject");
				  break;
			case "Hyronalin":
				  dosation(15);
				  addChem("silicon");
				  addChem("potassium");
				  addChem("nitrogen");		  
				  addChem("radium");
				  addChem("radium");
				  addChem("radium");
				  addChem("eject");
				  break;
			case "DexalinePlus":
				  dosation(20);
				  addChem("oxygen");
				  dosation(30);
				  addChem("iron");
				  addChem("carbon");
				  addChem("eject");
				  break;
			case "Phalanximine":
				  dosation(5);				  
				  addChem("nitrogen");
				  addChem("potassium");
				  addChem("silicon");				  
				  dosation(10);
				  addChem("chlorine");	
				  addChem("phosphorus");	
				  dosation(25);
				  addChem("radium");
				  dosation(30);
				  addChem("ethanol");
				  addChem("eject");
				  break;
			case "CloralHydrate":
				  dosation(20);
				  addChem("ethanol");
				  dosation(100);
				  addChem("chlorine");
				  addChem("eject");
				  break;
			case "EzNutritient":
				  dosation(30);
				  addChem("nitrogen");
				  addChem("phosphorus");
				  addChem("potassium");
				  addChem("eject");
				  break;
			case "SpaceDrugs":
				  dosation(30);
				  addChem("lithium");
				  addChem("mercury");
				  addChem("sugar");
				  addChem("eject");
				  break;
			case "Cleaner":
				 dosation(15);
				 addChem("hydrogen");
				 dosation(5);
				 addChem("nitrogen");
				 dosation(20);
				 addChem("chlorine");
				 addChem("sodium");
				 addChem("oxygen");
				 addChem("eject");
				 break;
			case "Lube":
				 dosation(30);
				 addChem("oxygen");
				 addChem("silicon");
				 break;
			case "trio":
				 dosation(15);
				 addChem("oxygen");
				 addChem("sugar");
				 addChem("carbon");
				 addChem("potassium");
				 addChem("silicon");
				 addChem("nitrogen");
				 addChem("eject");
				 break;		
			case "Mutagen":
				  dosation(30);
				  addChem("chlorine");
				  addChem("phosphorus");
				  addChem("radium");
				  addChem("eject");
				  break;
			case "Robust":
				  dosation(30);
				  addChem("nitrogen");
				  addChem("phosphorus");
				  addChem("potassium");
				  dosation(1);
				  addChem("sulfur");
				  addChem("hydrogen");
				  addChem("oxygen");
				  addChem("oxygen");
				  addChem("eject");
				  break;
			case "Spaceacillin":
				  dosation(15);
				  addChem("oxygen");
				  addChem("oxygen");
				  addChem("sugar");
				  addChem("sugar");
				  addChem("carbon");
				  addChem("potassium");				  
				  addChem("eject");
				  break;
			case "Tranexamic acid":
				  dosation(10);
				  addChem("oxygen");
				  addChem("sugar");
				  addChem("carbon");
				  dosation(30);
				  addChem("sugar");
				  dosation(10);
				  addChem("hydrogen");
				  addChem("sulfur");
				  addChem("oxygen");
				  addChem("oxygen");	
				  addChem("eject");
				  break;
			case "Fluorosurfactant":
				  dosation(5);
				  addChem("hydrogen");
				  addChem("sulfur");
				  addChem("oxygen");
				  addChem("oxygen");
				  dosation(30);
				  addChem("fluorine");
				  addChem("carbon");
				  addChem("eject");
				  break;
			case "ipecac":
				  dosation(10);
				  addChem("silicon");
				  addChem("potassium");
				  addChem("nitrogen");
				  addChem("hydrogen");
				  addChem("hydrogen");
				  addChem("hydrogen");
				  addChem("nitrogen");
				  addChem("eject");
				  break;
				  
		  }		
	}
	public static void MoveTab(String tab) {
		int mask = InputEvent.BUTTON1_DOWN_MASK;
		Robot bot = null;
		  try {
		   bot = new Robot();
		  } catch (Exception failed) {
		   System.err.println("Failed instantiating Robot: " + failed);
		  }
		switch(tab) {
		case "Dispenser":
			bot.mouseMove(430, 192);//Left corner of "chemical dispenser
			pause(100);
			bot.mousePress(mask);
			pause(300);
			bot.mouseMove(2, 23);//Left corner of window
			bot.mouseRelease(mask);
			break;
		case "ChemMaster":
			bot.mouseMove(418, 90);//Chem Master  start position
			pause(100);
			bot.mousePress(mask);
			pause(300);
			bot.mouseMove(10, 270);//Chem Master left corner position
			bot.mouseRelease(mask);
			break;
		case "Transfer first":
			bot.mouseMove(25, 300);click();// Chem Master "Input button"
			bot.mouseMove(500, 390);click();// Chem Master transfer all (first)
			bot.mouseMove(500, 325);click();// Chem Master "Eject button"
			break;
		case "Transfer second":
			bot.mouseMove(25, 300);click();// Chem Master "Input button"
			bot.mouseMove(500, 410);click();// Chem Master transfer all (second)
			bot.mouseMove(500, 325);click();// Chem Master "Eject button"
			break;			
		}
	}
	private static JButton createButton(String text, Color background, Color foreground, Font font) {
	    JButton button = new JButton(text);
	    button.setBackground(background);
	    button.setForeground(foreground);
	    button.setFont(font);
	    return button;
	}
	

 public static void pause(int ms) { // function which provides delay
	    try {
	        Thread.sleep(ms);
	    } catch (InterruptedException e) {
	        System.err.format("IOException: %s%n", e);
	    }
	}
 

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setLayout(null);
		frame.setTitle("Chemistry Helper");
		frame.setSize(406,290);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar =new JMenuBar();
		JMenu setMenu = new JMenu("Set");
		JMenu etcMenu = new JMenu("Etc");
		JMenu settingsMenu = new JMenu("settings");
		
		menuBar.add(setMenu);
		menuBar.add(etcMenu);
		menuBar.add(settingsMenu);		
		
		JMenuItem moveDispenser= new JMenuItem("Dispenser");
		JMenuItem moveChemMaster= new JMenuItem("Chem Master");
		JMenuItem moveFirst= new JMenuItem("move first");
		JMenuItem moveSecond= new JMenuItem("move second");
		
		JMenuItem MakeIpecac= new JMenuItem("Ipecac");
		JMenuItem MakeFluorosurfactant= new JMenuItem("Fluorosurfactant");
		JMenuItem MakeDrugs= new JMenuItem("Space drugs");

		JMenuItem getCode= new JMenuItem("Get code");
		JMenuItem getCords= new JMenuItem("Get cords");

		//**************************************************************
		
		JButton btnBicardine = createButton("Bicardine",new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnDermaline = createButton("Dermaline", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnDylovene = createButton("Dylovene", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnHyronaline = createButton("Hyronalin", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnDexalinePlus = createButton("Dexaline Plus", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));
		JButton btnPhalanximine = createButton("Phalanximine", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));
		JButton btnTrio = createButton("Tricodrazine",new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));
		JButton btnSpacealicine = createButton("Spacealicine",new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));
		JButton btnTranexamicAcid = createButton("Tranex. acid",new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));

		JButton btnEzNutritient = createButton("EZ Nutritient", Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		JButton btnRobustHarvest = createButton("Robust",Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		JButton btnUnstableMutagen = createButton("Unstable mutagen",Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		
		JButton btnCloralHydrate = createButton("Cloral Hydrate", null, null, null);
		JButton btnCleaner = createButton("Cleaner", null, null, null);
		JButton btnLube = createButton("Lube", null, null, null);

		



	  
	  /**************************************Labels************************************************/
	  
	  JTextField lbl1=new JTextField("Example of pill`s naming will be here");	  
	  lbl1.setEditable(false);	  
	  lbl1.setFont(new Font("Times New Roman",Font.BOLD,14));
	  lbl1.setBounds(0,0, 420,50); 	  
	  
	  /**************************************Setup panels************************************************/
	  // Create the panels and set their properties
	  JPanel med = new JPanel();
	  med.setBounds(0,50, 390, 100);
	  med.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	  med.setLayout(new GridLayout(3,3,1,1));
	  med.setBackground(Color.GREEN);

	 

	  JPanel odds = new JPanel();
	  odds.setBounds(0,150, 145, 100);
	  odds.setLayout(new GridLayout(4,1,1,1));
	
	  JPanel drg = new JPanel();
	  drg.setBounds(145,150, 245, 100);
	  drg.setBorder(BorderFactory.createLineBorder(new Color(0xbcb8e4)));
	  drg.setLayout(new GridLayout(4,1,1,1));
	  drg.setBackground(new Color(0xbcb8e4));
	  
	  /**************************************Grid panels************************************************/
	  med.add(btnBicardine);
	  med.add(btnDermaline);
	  med.add(btnDylovene);
	  med.add(btnHyronaline);
	  med.add(btnDexalinePlus);
	  med.add(btnPhalanximine);
	  med.add(btnTrio);
	  med.add(btnSpacealicine);
	  med.add(btnTranexamicAcid);


	  odds.add(btnEzNutritient);
	  odds.add(btnUnstableMutagen);
	  odds.add(btnRobustHarvest);
	  
	  drg.add(btnCleaner);
	  drg.add(btnLube); 
	  drg.add(btnCloralHydrate);	  
		
	  etcMenu.add(MakeIpecac);
	  etcMenu.add(MakeFluorosurfactant);
	  etcMenu.add(MakeDrugs);
		
	  settingsMenu.add(getCode);
	  settingsMenu.add(getCords);
		
	  setMenu.add(moveDispenser);
	  setMenu.add(moveChemMaster);
	  setMenu.add(moveFirst);		
	  setMenu.add(moveSecond);	
	  
	  frame.setJMenuBar(menuBar); 
	  frame.add(med);	
	  frame.add(odds);
	  frame.add(drg);
	  frame.add(lbl1);
	  
	  frame.revalidate();
	  /**************************************Buttons functions************************************************/
	  getCords.addActionListener(e -> {
				pause(3000);
				lbl1.setText("\n X:" + (int) MouseInfo.getPointerInfo().getLocation().getX()
						    +"\n Y:" + (int) MouseInfo.getPointerInfo().getLocation().getY());				
				});		  
		  btnBicardine.addActionListener(e -> {
				createMed("Bicardine");
				lbl1.setText("???????????????? (????????????, * ????)");
				});
		  btnDermaline.addActionListener(e -> {
			   	createMed("Dermaline");
				lbl1.setText("???????????????? (??????????, * ????)");
				});
		  btnDylovene.addActionListener(e -> {
				createMed("Dylovene");
				lbl1.setText("?????????????? (????????????????, * ????)");
				});
		  btnHyronaline.addActionListener(e -> {
				createMed("Hyronalin");
				lbl1.setText("?????????????????? (????????????????, * ????)");
				});
		  btnDexalinePlus.addActionListener(e -> {
				createMed("DexalinePlus");
				lbl1.setText("?????????????????? ???????? (????????????????, * ????) plasma needed!");
				});
		  btnPhalanximine.addActionListener(e -> {createMed("Phalanximine");
				lbl1.setText("???????????????????? (???????? ?????? ????????, * ????)");
				});				 
		  btnEzNutritient.addActionListener(e -> {
				createMed("EzNutritient");				
				lbl1.setText("Give your plants some of those EZ nutrients!");
				});
		  btnCleaner.addActionListener(e -> {
				createMed("Cleaner");				
				lbl1.setText("The janitor is likely to appreciate refills.) 20u of water needed");
				});
		  btnLube.addActionListener(e -> {
				System.out.println("btnLube button pressed");
				createMed("Lube");				
				lbl1.setText("certainly not used to make people slip, 20u of water needed");
				});
		  btnTrio.addActionListener(e -> {
				System.out.println("trio button pressed");
				createMed("trio");
				lbl1.setText("???????????????????????? (???????????????????? ???????????????? ???????? * ????)");
				});	
		  btnSpacealicine.addActionListener(e -> {
				System.out.println("trio button pressed");
				createMed("Spaceacillin");
				lbl1.setText("???????????????????? (???????????????????? * ????)");
				});
		  btnTranexamicAcid.addActionListener(e -> {
				System.out.println("trio button pressed");
				createMed("Tranexamic acid");
				lbl1.setText("???????????????????????????? ??????????(* ????) ");
				});		  
		  btnCloralHydrate.addActionListener(e -> {
				createMed("CloralHydrate");
				lbl1.setText("????????????????????????(????????????????, * ????)20u of water needed");
				});
		  btnUnstableMutagen.addActionListener(e -> {
				createMed("Mutagen");
				lbl1.setText("Mutagen can be harmful for crew");
				}); 
		  getCode.addActionListener(e -> {
				lbl1.setText("https://github.com/Exaster/Space-Station-14-Chemistry-Helper.git");
				});
		  btnRobustHarvest.addActionListener(e -> {
			    createMed("Robust");
			    lbl1.setText("Plant-enhancing hormones, good for increasing potency.");
				});		  
		  moveDispenser.addActionListener(e->{
				MoveTab("Dispenser");
			});
		  moveChemMaster.addActionListener(e->{
				MoveTab("ChemMaster");
			});
		  moveFirst.addActionListener(e->{
				MoveTab("Transfer first");
			});
		  moveSecond.addActionListener(e->{
				MoveTab("Transfer second");
			});
	      MakeFluorosurfactant.addActionListener(e->{
				createMed("Fluorosurfactant");
			    lbl1.setText("?????????????????? ?? ?????????? ?????????????????? ????????");
			});
		  MakeIpecac.addActionListener(e->{
				createMed("ipecac");
			    lbl1.setText("Cause vomiting");
			});
		MakeDrugs.addActionListener(e->{
				createMed("SpaceDrugs");
				lbl1.setText("Space drugs mustn`t be noticed by sc!");
			});
	}
}