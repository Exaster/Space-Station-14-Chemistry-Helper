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
	    Map<Integer, Point> dosePositions = new HashMap<>();
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
	    chemCoords.put("nitrogen", new Point(370, 160));
	    chemCoords.put("ethanol", new Point(50, 130));
	    chemCoords.put("iron", new Point(50, 150));
	    chemCoords.put("lithium", new Point(165, 150));
	    chemCoords.put("mercury", new Point(270, 150));
	    chemCoords.put("eject", new Point(170, 245));
	    chemCoords.put("hydrogen", new Point(275, 125));
	    chemCoords.put("sodium", new Point(165, 215));
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
		JButton btnGetCords = createButton("Get Cords", null, null, null);
		JButton btnBicardine = createButton("Bicardine",new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnDermaline = createButton("Dermaline", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnDylovene = createButton("Dylovene", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnHyronaline = createButton("Hyronalin", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 14));
		JButton btnDexalinePlus = createButton("Dexaline Plus", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));
		JButton btnPhalanximine = createButton("Phalanximine", new Color(0xf19d0e), Color.YELLOW, new Font("Arial", Font.BOLD, 12));
		JButton btnMoveDispenser = createButton("Move Dispenser", new Color(0xe8e3e3), new Color(0x123456), new Font("Arial", Font.BOLD, 12));
		JButton btnMoveChemMaster = createButton("Move ChemMaster", new Color(0xe8e3e3), new Color(0x123456), new Font("Arial", Font.BOLD, 12));
		JButton btnTrunsferChemsFirst = createButton("Transfer first", new Color(0xe8e3e3), new Color(0x123456), new Font("Arial", Font.BOLD, 12));
		JButton btnTrunsferChemsSecond = createButton("Transfer second",  new Color(0xe8e3e3), new Color(0x123456), new Font("Arial", Font.BOLD, 12));
		JButton btnEzNutritient = createButton("EZ Nutritient", Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		JButton btnCleaner = createButton("Cleaner",Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		JButton btnLube = createButton("Lube",Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		JButton btnTrio = createButton("Tricodrazine",Color.WHITE,Color.BLACK, new Font("Arial", Font.BOLD, 12));
		JButton btnCloralHydrate = createButton("Cloral Hydrate", null, null, null);
		JButton btnSpaceDrugs = createButton("SpaceDrugs", null, null, null);
		JButton btnGetCode = createButton("Get code", null, null, null);

		frame.add(btnGetCords);
		frame.add(btnGetCode);

		btnGetCords.setBounds(148,220,90,25);
		btnGetCords.setBackground(Color.BLACK);
		btnGetCords.setForeground(Color.WHITE);
		btnGetCode.setBounds(286,220,90, 25);

		btnGetCode.setBackground(Color.BLACK);
		btnGetCode.setForeground(Color.WHITE);
		btnGetCode.setBounds(286,220,90, 25);
	  
	  /**************************************Labels************************************************/
	  
	  JTextField lbl1=new JTextField("Example of pill`s naming will be here");	  
	  lbl1.setEditable(false);	  
	  lbl1.setFont(new Font("Times New Roman",Font.BOLD,14));
	  lbl1.setBounds(0,0, 420,50); 	  
	  
	  /**************************************Setup panels************************************************/
	  // Create the panels and set their properties
	  JPanel med = new JPanel();
	  med.setBounds(145,50, 245, 100);
	  med.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	  med.setLayout(new GridLayout(3,2,1,1));
	  med.setBackground(Color.GREEN);

	  JPanel control = new JPanel();
	  control.setBounds(0,50, 145, 100);
	  //control.setBorder(BorderFactory.createLineBorder(new Color(0xbcb8e4)));
	  control.setLayout(new GridLayout(4,1,3,1));
	  control.setBackground(new Color(0xbcb8e4));

	  JPanel odds = new JPanel();
	  odds.setBounds(0,150, 145, 100);
	  odds.setLayout(new GridLayout(4,1,1,1));
	 // odds.setBorder(BorderFactory.createLineBorder(new Color(0x9891d5)));
	  //odds.setBackground(new Color(0x9891d5));

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

	  control.add(btnMoveDispenser);
	  control.add(btnMoveChemMaster);
	  control.add(btnTrunsferChemsFirst);
	  control.add(btnTrunsferChemsSecond);

	  odds.add(btnEzNutritient);
	  odds.add(btnCleaner);
	  odds.add(btnLube);
	  odds.add(btnTrio);

	  drg.add(btnCloralHydrate);
	  drg.add(btnSpaceDrugs);

	  frame.add(med);
	  frame.add(control);
	  frame.add(odds);
	  frame.add(drg);
	  frame.add(lbl1); 
	  frame.revalidate();
	  /**************************************Buttons functions************************************************/
		btnGetCords.addActionListener(e -> {
				pause(3000);
				lbl1.setText("\n X:" + (int) MouseInfo.getPointerInfo().getLocation().getX()
						         +"\n Y:" + (int) MouseInfo.getPointerInfo().getLocation().getY());
				
				});		  
		  btnBicardine.addActionListener(e -> {
				createMed("Bicardine");
				lbl1.setText("Бікардин (травми, * мг)");
				});
		  btnDermaline.addActionListener(e -> {
			   	createMed("Dermaline");
				lbl1.setText("Дермалін (опіки, * мг)");
				});
		  btnDylovene.addActionListener(e -> {
				createMed("Dylovene");
				lbl1.setText("Диловен (отруєння, * мг)");
				});
		  btnHyronaline.addActionListener(e -> {
				createMed("Hyronalin");
				lbl1.setText("Гироналін (радіація, * мг)");
				});
		  btnDexalinePlus.addActionListener(e -> {
				createMed("DexalinePlus");
				lbl1.setText("Декасалін Плюс (асфіксія, * мг)");
				});
		  btnPhalanximine.addActionListener(e -> {createMed("Phalanximine");
				lbl1.setText("Фалаксимін (ліки від раку, * мг)");
				});		 
		  btnMoveDispenser.addActionListener(e -> MoveTab("Dispenser"));
		  btnMoveChemMaster.addActionListener(e -> MoveTab("ChemMaster"));
		  btnTrunsferChemsFirst.addActionListener(e -> MoveTab("Transfer first"));
		  btnTrunsferChemsSecond.addActionListener(e -> MoveTab("Transfer second"));
		  btnEzNutritient.addActionListener(e -> {
				createMed("EzNutritient");				
				lbl1.setText("Give your plants some of those EZ nutrients!");
				});
		  btnCleaner.addActionListener(e -> {
				createMed("Cleaner");				
				lbl1.setText("The janitor is likely to appreciate refills.)");
				});
		  btnLube.addActionListener(e -> {
				System.out.println("btnLube button pressed");
				createMed("Lube");				
				lbl1.setText("certainly not used to make people slip");
				});
		  btnTrio.addActionListener(e -> {
				System.out.println("trio button pressed");
				createMed("trio");
				lbl1.setText("Триокодразин (знеболююче широкого типу ** мг)");
				});		  
		  btnCloralHydrate.addActionListener(e -> {
				createMed("CloralHydrate");
				lbl1.setText("Глоралгідрат(снодійне, * мг)");
				});
		  btnSpaceDrugs.addActionListener(e -> {
				createMed("SpaceDrugs");
				lbl1.setText("Space drugs mustn`t be noticed by sc!");
				});  		  
		  btnGetCode.addActionListener(e -> {
				lbl1.setText("https://github.com/Exaster/Space-Station-14-Chemistry-Helper.git");
				});			
	}
}