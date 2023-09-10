package EN_build;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChemistryHelperEN extends JFrame implements ActionListener {
	
	public static final long serialVersionUID = 1L;
	public static final String CONFIG_FILE = "EN_config.txt";

	public static int[][] Oxygen;
	public static int[][] Nitrogen;
	public static int[][] Carbon;
	public static int[][] Sugar;
	public static int[][] Chlorine;
	public static int[][] Phosphorus;
	public static int[][] Potassium;
	public static int[][] Radium;
	public static int[][] Silicon;
	public static int[][] Sulfur;
	public static int[][] Ethanol;
	public static int[][] Iron;
	public static int[][] Lithium;
	public static int[][] Mercury;
	public static int[][] Fluorine;
	public static int[][] Hydrogen;
	public static int[][] Sodium;
	public static int[][] Aluminium;
	public static int[][] dosation_1;
	public static int[][] dosation_5;
	public static int[][] dosation_10;
	public static int[][] dosation_15;
	public static int[][] dosation_20;
	public static int[][] dosation_25;
	public static int[][] dosation_30;
	public static int[][] dosation_50;
	public static int[][] dosation_100;
	
	public static int[][] DispenserStartPosition;
	public static int[][] DispenserEndPosition;
	public static int[][] ChemMasterStartPosition;
	public static int[][] ChemMasterEndPosition;
	public static int[][] Delay_duration;
	
	public JPanel cardPanel;
	public CardLayout cardLayout;
	public static TextField message;	
	public static int initialX;
	public static int initialY;	
	private static Timer timer;
	public static Robot bot;

	@SuppressWarnings("unused")
	public ChemistryHelperEN() {
		File configFile = new File(CONFIG_FILE);
		if (!configFile.exists()) {
			createDefaultConfigFile();
		} else {
			loadConfigData();
		}
		try {
			bot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		menuPanel.setBackground(Color.DARK_GRAY);

		// Create menu buttons with custom style
		JButton button1 = createButton("Medical");
		JButton button2 = createButton("Other");
		JButton button3 = createButton("Test");
		JButton button4 = createButton("Code");
		JButton button5 = createButton("Coords");
		JButton button6 = createButton("Settings");

		// Add action listeners to the buttons
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		
		// Add buttons to the menu panel
		menuPanel.add(button1);
		menuPanel.add(button2);
		menuPanel.add(button3);
		menuPanel.add(button4);
		menuPanel.add(button5);
		menuPanel.add(button6);
		

		// Create panels with different colors
		JPanel MedicalPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		MedicalPanel.setBackground(Color.BLUE);
		JPanel OtherPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		OtherPanel.setBackground(Color.GREEN);
		JPanel TestPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		TestPanel.setBackground(Color.RED);		
		JPanel SettingsPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		SettingsPanel.setBackground(Color.YELLOW);	
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.add(MedicalPanel, "Medical");
		cardPanel.add(OtherPanel, "Other");
		cardPanel.add(TestPanel, "Test");
		cardPanel.add(SettingsPanel, "Settings");	
		cardLayout.show(cardPanel, "First");

		message = new TextField("Just silly text!");
		message.setFont(new Font("Arial", Font.BOLD, 24));
		message.setBackground(Color.BLACK);
		message.setForeground(Color.CYAN);
		message.setEditable(false);
		message.setBackground(Color.DARK_GRAY);
		
		
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.add(menuPanel, BorderLayout.NORTH);
		headerPanel.add(message, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);
		
		setUndecorated(false);
		setLocationRelativeTo(null);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialX = e.getX();
				initialY = e.getY();
			}
		});		
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int currentX = getLocation().x + e.getX() - initialX;
				int currentY = getLocation().y + e.getY() - initialY;
				setLocation(currentX, currentY);
			}
		});		
		
		JButton openFolderButton = new JButton();
		openFolderButton.setBackground(Color.GREEN);
		openFolderButton.setPreferredSize(new Dimension(20, 20));
		openFolderButton.setBorder(null);
		openFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
					String folderPath = new File(path).getParent();
					Desktop.getDesktop().open(new File(folderPath));
					message.setText("Config file");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});		
		
		createButton("Dispencer", MedicalPanel,(ActionEvent e) -> {moveFromTo(DispenserStartPosition, DispenserEndPosition);});
		createButton("ChemMaster", MedicalPanel,(ActionEvent e) -> {moveFromTo(ChemMasterStartPosition, ChemMasterEndPosition);});		
		createButton("Bicardine", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_15,
			        Oxygen,
			        Sugar,
			        Carbon,
			        Carbon,
			        Carbon,
			        Carbon,
			        "Bicardine (brute, * u)"
			    );
			});
		createButton("Dermaline", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_15,
			        Silicon,
			        Oxygen,
			        Carbon,
			        Oxygen,
			        Phosphorus,
			        Phosphorus,
			        "Dermaline (burns, * u)"
			    );
			});
		createButton("Dylovene", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_30,
			        Silicon,
			        Potassium,
			        Nitrogen,
			        "Dylovene (poisoning, * u)"
			    );
			});
		createButton("Dexaline+", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_20,
			        Oxygen,
			        dosation_30,
			        Iron,
			        Carbon,
			        "Dexaline + (airloss, * u) plasma!"
			    );
			});
		createButton("Arizatrine", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_5,
			        Silicon,
			        Potassium,
			        Nitrogen,
			        dosation_15,
			        Radium,
			        Hydrogen,
			        Hydrogen,
			        "Arizatrine (radiation, * u)"
			    );
			});
		createButton("Phalaximine", MedicalPanel, (ActionEvent e) -> {
			    mix(
			        dosation_5,
			        Nitrogen,
			        Potassium,
			        Silicon,
			        dosation_10,
			        Chlorine,
			        Phosphorus,
			        dosation_25,
			        Radium,
			        dosation_30,
			        Ethanol,
			        "Phalaximine (cancer cure, * u)"
			    );
			});
		createButton("Spaceacillin", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_15,
			        Oxygen,
			        Oxygen,
			        Sugar,
			        Sugar,
			        Carbon,
			        Potassium,
			        "Spaceacillin "
			    );
			});
		createButton("TranexAcid", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_10,
			        Oxygen,
			        Sugar,
			        Carbon,
			        dosation_30,
			        Sugar,
			        dosation_10,
			        Hydrogen,
			        Sulfur,
			        Oxygen,
			        Oxygen,
			        "Tranex acid(* u)"
			    );
			});
		createButton("Tricodrazine", MedicalPanel,(ActionEvent e) -> {
			    mix(
			        dosation_15,
			        Oxygen,
			        Sugar,
			        Carbon,
			        Potassium,
			        Silicon,
			        Nitrogen,
			        "Vitamins"
			    );
			});		
		createButton("Chloralhydrate", OtherPanel, (ActionEvent e) -> {
			    mix(
			        dosation_20,
			        Ethanol,
			        dosation_100,
			        Chlorine,
			        "Chloralhydrate, 20 water!"
			    );
			});
		createButton("EZnutritient", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_30,
			        Nitrogen,
			        Phosphorus,
			        Potassium,
			        "Easy fertilizer"
			    );
			});
		createButton("Drugs", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_30,
			        Lithium,
			        Mercury,
			        Sugar,
			        "Don`t share with sec!"
			    );
			});
		createButton("SpaceCleaner", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_15,
			        Hydrogen,
			        dosation_5,
			        Nitrogen,
			        dosation_20,
			        Chlorine,
			        Sodium,
			        Oxygen,
			        "Cleaner, 20u water needed"
			    );
			});
		createButton("Lube", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_30,
			        Oxygen,
			        Silicon,
			        "Space Lube!30 water"
			    );
			});
		createButton("Мутаген", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_30,
			        Chlorine,
			        Phosphorus,
			        Radium,
			        "Unstable mutagen"
			    );
			});
		createButton("Robust", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_30,
			        Nitrogen,
			        Phosphorus,
			        Potassium,
			        dosation_1,
			        Sulfur,
			        Hydrogen,
			        Oxygen,
			        Oxygen,
			        "Robust"
			    );
			});
		createButton("AntiEthanol", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_10,
			        Silicon,
			        Potassium,
			        Nitrogen,
			        dosation_30,
			        Oxygen,
			        Carbon,
			        "If beer sucks"
			    );
			});
		 createButton("Ipecac ", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_10,
			        Hydrogen,
			        Hydrogen,
			        Hydrogen,
			        Potassium,
			        Potassium,
			        Potassium,
			        Potassium,
			        dosation_50,
			        Nitrogen,	
			        Nitrogen,
			        "Cause vommiting"
			    );
			});
		createButton("Fluorosurfactant", OtherPanel,(ActionEvent e) -> {
			    mix(
			        dosation_5,
			        Hydrogen,
			        Sulfur,
			        Oxygen,
			        Oxygen,
			        dosation_30,
			        Fluorine,
			        Carbon,
			        "Foam with water"
			    );
			});		
		createButton("Oxygen", TestPanel,(ActionEvent e) -> {mix(Oxygen,"Oxygen");});
		createButton("Nitrogen", TestPanel,(ActionEvent e) -> {mix(Nitrogen,"Nitrogen");});
		createButton("Carbon", TestPanel,(ActionEvent e) -> {mix(Carbon,"Carbon");});
		createButton("Sugar", TestPanel,(ActionEvent e) -> {mix(Sugar,"Sugar");});
		createButton("Chlorine", TestPanel,(ActionEvent e) -> {mix(Chlorine,"Chlorine");});
		createButton("Phosphorus", TestPanel,(ActionEvent e) -> {mix(Phosphorus,"Phosphorus");});
		createButton("Potassium", TestPanel,(ActionEvent e) -> {mix(Potassium,"Potassium");});
		createButton("Radium", TestPanel,(ActionEvent e) -> {mix(Radium,"Radium");});
		createButton("Silicon", TestPanel,(ActionEvent e) -> {mix(Carbon,"Sugar");});
		createButton("Sulfur", TestPanel,(ActionEvent e) -> {mix(Sulfur,"Sulfur");});
		createButton("Ethanol", TestPanel,(ActionEvent e) -> {mix(Ethanol,"Ethanol");});
		createButton("Iron", TestPanel,(ActionEvent e) -> {mix(Iron,"Iron");});
		createButton("Lithium", TestPanel,(ActionEvent e) -> {mix(Lithium,"Lithium");});
		createButton("Mercury", TestPanel,(ActionEvent e) -> {mix(Carbon,"Sugar");});
		createButton("Fluorine", TestPanel,(ActionEvent e) -> {mix(Fluorine,"Fluorine");});
		createButton("Hydrogen", TestPanel,(ActionEvent e) -> {mix(Hydrogen,"Hydrogen");});
		createButton("Sodium", TestPanel,(ActionEvent e) -> {mix(Sodium,"Sodium");});
		createButton("Aluminium", TestPanel,(ActionEvent e) -> {mix(Aluminium,"Aluminium");});
		createButton("1u", TestPanel,(ActionEvent e) -> {mix(dosation_1,"доза 1");});
		createButton("5u", TestPanel,(ActionEvent e) -> {mix(dosation_5,"доза 5");});
		createButton("10u", TestPanel,(ActionEvent e) -> {mix(dosation_10,"доза 10");});
		createButton("15u", TestPanel,(ActionEvent e) -> {mix(dosation_15,"доза 15");});
		createButton("20u", TestPanel,(ActionEvent e) -> {mix(dosation_20,"доза 20");});
		createButton("25u", TestPanel,(ActionEvent e) -> {mix(dosation_25,"доза 25");});
		createButton("30u", TestPanel,(ActionEvent e) -> {mix(dosation_30,"доза 30");});
		createButton("50u", TestPanel,(ActionEvent e) -> {mix(dosation_50,"доза 50");});
		createButton("100u", TestPanel,(ActionEvent e) -> {mix(dosation_100,"доза 100");});
		createButton("Dispencer start", TestPanel,(ActionEvent e) -> {moveFromTo(DispenserStartPosition, DispenserStartPosition);});
		createButton("Dispencer end", TestPanel,(ActionEvent e) -> {moveFromTo(DispenserEndPosition, DispenserEndPosition);});
		createButton("Chem start", TestPanel,(ActionEvent e) -> {moveFromTo(ChemMasterStartPosition, ChemMasterStartPosition);});
		createButton("Chem end", TestPanel,(ActionEvent e) -> {moveFromTo(ChemMasterEndPosition, ChemMasterEndPosition);});

		createButton("Oxygen", SettingsPanel,(ActionEvent e) -> {setCoords(1,"Oxygen");});
		createButton("Nitrogen", SettingsPanel,(ActionEvent e) -> {setCoords(2,"Nitrogen");});
		createButton("Carbon", SettingsPanel,(ActionEvent e) -> {setCoords(3,"Carbon");});
		createButton("Sugar", SettingsPanel,(ActionEvent e) -> {setCoords(4,"Sugar");});
		createButton("Chlorine", SettingsPanel,(ActionEvent e) -> {setCoords(5,"Chlorine");});
		createButton("Phosphorus", SettingsPanel,(ActionEvent e) -> {setCoords(6,"Phosphorus");});
		createButton("Potassium", SettingsPanel,(ActionEvent e) -> {setCoords(7,"Potassium");});
		createButton("Radium", SettingsPanel,(ActionEvent e) -> {setCoords(8,"Radium");});
		createButton("Silicon", SettingsPanel,(ActionEvent e) -> {setCoords(9,"Silicon");});
		createButton("Sulfur", SettingsPanel,(ActionEvent e) -> {setCoords(10,"Sulfur");});
		createButton("Ethanol", SettingsPanel,(ActionEvent e) -> {setCoords(11,"Ethanol");});
		createButton("Iron", SettingsPanel,(ActionEvent e) -> {setCoords(12,"Iron");});
		createButton("Lithium", SettingsPanel,(ActionEvent e) -> {setCoords(13,"Lithium");});
		createButton("Mercury", SettingsPanel,(ActionEvent e) -> {setCoords(14,"Mercury");});
		createButton("Fluorine", SettingsPanel,(ActionEvent e) -> {setCoords(15,"Fluorine");});
		createButton("Hydrogen", SettingsPanel,(ActionEvent e) -> {setCoords(16,"Hydrogen");});
		createButton("Sodium", SettingsPanel,(ActionEvent e) -> {setCoords(17,"Sodium");});
		createButton("Aluminium", SettingsPanel,(ActionEvent e) -> {setCoords(18,"Aluminium");});
		createButton("1u", SettingsPanel,(ActionEvent e) -> {setCoords(19,"1u");});
		createButton("5u", SettingsPanel,(ActionEvent e) -> {setCoords(20,"5u");});
		createButton("10u", SettingsPanel,(ActionEvent e) -> {setCoords(21,"10u");});
		createButton("15u", SettingsPanel,(ActionEvent e) -> {setCoords(22,"15u");});
		createButton("20u", SettingsPanel,(ActionEvent e) -> {setCoords(23,"20u");});
		createButton("25u", SettingsPanel,(ActionEvent e) -> {setCoords(24,"25u");});
		createButton("30u", SettingsPanel,(ActionEvent e) -> {setCoords(25,"30u");});
		createButton("50u", SettingsPanel,(ActionEvent e) -> {setCoords(26,"50u");});
		createButton("100u", SettingsPanel,(ActionEvent e) -> {setCoords(27,"100u");});
		createButton("DispencerStartPosition", SettingsPanel,(ActionEvent e) -> {setCoords(28,"Dispencer start position");});
		createButton("setChemMasterStartPosition", SettingsPanel,(ActionEvent e) -> {setCoords(29,"Dispencer end position");});
		createButton("setChemMasterStartPosition", SettingsPanel,(ActionEvent e) -> {setCoords(30,"ChemMaster start position");});
		createButton("setChemMasterEndPosition", SettingsPanel,(ActionEvent e) -> {setCoords(31,"ChemMaster end position");});
		Thread configFileThread = new Thread(() -> watchConfigFile(configFile));
		configFileThread.start();		
		menuPanel.add(openFolderButton, BorderLayout.WEST);
		setTitle("EN Chemistry Helper");
		setSize(470,200);
		setVisible(true);
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Erasing Thread to free RAM");
                System.exit(0);
            }
        });
	}
	public static JButton createButton(String text, JPanel panel, ActionListener actionListener) {
	    JButton button = new JButton(text);
	    button.setFont(new Font("Arial", Font.BOLD, 12));
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    panel.add(button);
	    button.addActionListener(actionListener);
	    return button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("Coords")) {
			createCoordinatesFrame();
		} else if (actionCommand.equals("Code")) {
			message.setText("https://github.com/Exaster/Space-Station-14-Chemistry-Helper");
		} else {
			cardLayout.show(cardPanel, actionCommand);
		}
	}
	public JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));		
		return button;
	}		
	public static void setCoords(int lineNumber, String newText) {
		message.setText("Buttons setting");
		JFrame frame = new JFrame("Coordinates");
		frame.setUndecorated(true);
		frame.setSize(250, 100);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Coordinates: X=0, Y=0");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		frame.add(label);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateConfig(lineNumber, newText);
				}
			}
		});
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int x = MouseInfo.getPointerInfo().getLocation().x;
				int y = MouseInfo.getPointerInfo().getLocation().y;
				label.setText("<html>Coordinates: X=" + x + ", Y=" + y + "<br>Changing: " + newText
						+ "<br>Enter to apply " + "</html>");
			}
		});
		JButton closeButton = new JButton();
		closeButton.setBackground(Color.RED);
		closeButton.setPreferredSize(new Dimension(10, 10));
		closeButton.setBorder(null);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(false);
		buttonPanel.add(closeButton);
		frame.add(buttonPanel, BorderLayout.SOUTH);

		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialX = e.getX();
				initialY = e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int currentX = frame.getLocation().x + e.getX() - initialX;
				int currentY = frame.getLocation().y + e.getY() - initialY;
				frame.setLocation(currentX, currentY);
			}
		});
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				timer.stop();
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateConfig(lineNumber, newText);
					frame.dispose();
				}
			}
		});
		timer.start();
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.requestFocus();
	}
	private static void updateConfig(int lineNumber, String newText) {
		try {
			File configFile = new File("EN_config.txt");
			ArrayList<String> lines = new ArrayList<>();

			try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					lines.add(line);
				}
			}
			if (lineNumber >= 1 && lineNumber <= lines.size()) {
				int cursorX = MouseInfo.getPointerInfo().getLocation().x;
				int cursorY = MouseInfo.getPointerInfo().getLocation().y;

				String updatedLine = String.format("%d,%d // changed `%s`", cursorX, cursorY, newText);
				lines.set(lineNumber - 1, updatedLine);
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
					for (String updated : lines) {
						writer.write(updated);
						writer.newLine();
					}
				}
			} else {
				System.out.println("Invalid line number.");
			}
		} catch (IOException ex) {
			System.err.println("An error occurred: " + ex.getMessage());
		}
	}	
	public static void createCoordinatesFrame() {
		JFrame frame = new JFrame("Coordinates");
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(250, 100);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Coordinates: X=0, Y=0");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		frame.add(label);		
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialX = e.getX();
				initialY = e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int currentX = frame.getLocation().x + e.getX() - initialX;
				int currentY = frame.getLocation().y + e.getY() - initialY;
				frame.setLocation(currentX, currentY);
			}
		});
		Timer timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int x = MouseInfo.getPointerInfo().getLocation().x;
				int y = MouseInfo.getPointerInfo().getLocation().y;
				label.setText("Position: X=" + x + ", Y=" + y);
			}
		});
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				timer.stop();				
			}
		});		
		JButton closeButton = new JButton();
		closeButton.setBackground(Color.RED);
		closeButton.setPreferredSize(new Dimension(10, 10));
		closeButton.setBorder(null);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); 
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(false);
		buttonPanel.add(closeButton);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		timer.start();
	}
	public static void mix(Object... instructions) {
	    for (Object instruction : instructions) {
	        if (instruction instanceof int[][]) {
	            int[][] coords = (int[][]) instruction;
	            for (int[] coord : coords) {
	                int x = coord[0];
	                int y = coord[1];
	                clickOnScreen(x, y);
	            }
	        } else if (instruction instanceof String) {
	            String text = (String) instruction;
	            message.setText(text);
	        }
	    }
	}
	public static void moveFromTo(int[][] startCoords, int[][] endCoords) {
		try {
			Robot robot = new Robot();
			int startX = startCoords[0][0];
			int startY = startCoords[0][1];
			int endX = endCoords[0][0];
			int endY = endCoords[0][1];
			robot.mouseMove(startX, startY);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Delay();
			robot.mouseMove(endX, endY);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	private static void createDefaultConfigFile() {
		try {
			FileWriter writer = new FileWriter(CONFIG_FILE);
			writer.write("50,175 // Default data for Oxygen\n");
			writer.write("375,160 // Default data for Nitrogen\n");
			writer.write("165,90 // Default data for Carbon\n");
			writer.write("270,220 // Default data for Sugar\n");
			writer.write("270, 100 // Default data for Chlorine\n");
			writer.write("150, 175 // Default data for Phosphorus\n");
			writer.write("270, 175 // Default data for Potassium\n");
			writer.write("350, 175 // Default data for Radium\n");
			writer.write("50, 220 // Default data for Silicon\n");
			writer.write("370, 220 // Default data for Sulfur\n");
			writer.write("50, 130 // Default data for Ethanol\n");
			writer.write("50, 150 // Default data for Iron\n");
			writer.write("165, 150 // Default data for Lithium\n");
			writer.write("270, 150 // Default data for Mercury\n");
			writer.write("162, 125 // Default data for Fluorine\n");
			writer.write("275, 125 // Default data for Hydrogen\n");
			writer.write("165, 215 // Default data for Sodium\n");
			writer.write("50, 90 // Default data for Aluminium\n");

			writer.write("95, 70 // Default data for Dosation 1u \n");
			writer.write("120, 70 // Default data for Dosation 5u \n");
			writer.write("160, 70 // Default data for Dosation 10u\n");
			writer.write("200, 70 // Default data for Dosation 15u\n");
			writer.write("220, 70 // Default data for Dosation 20u\n");
			writer.write("250, 70 // Default data for Dosation 25u\n");
			writer.write("300, 70 // Default data for Dosation 30u\n");
			writer.write("340, 70 // Default data for Dosation 50u\n");
			writer.write("380, 70 // Default data for Dosation 100u\n");

			writer.write("430, 192 // Default data for 'Start Dispenser position'\n");
			writer.write("2, 23 // Default data for 'End Dispenser position'\n");
			writer.write("418, 90 // Default data for 'Start ChemMaster position'\n");
			writer.write("10, 270 // Default data for 'End ChemMaster position'\n");

			writer.write("300, 0 // Default data for delay time, only first number used cuz Imma lazy ^_^ \n");

			writer.close();
			loadConfigData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void loadConfigData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
			String line;
			int OxygenRow = 0;
			int NitrogenRow = 0;
			int CarbonRow = 0;
			int SugarRow = 0;
			int ChlorineRow = 0;
			int PhosphorusRow = 0;
			int PotassiumRow = 0;
			int RadiumRow = 0;
			int SiliconRow = 0;
			int SulfurRow = 0;
			int EthanolRow = 0;
			int IronRow = 0;
			int LithiumRow = 0;
			int MercuryRow = 0;
			int FluorineRow = 0;
			int HydrogenRow = 0;
			int SodiumRow = 0;
			int AluminiumRow = 0;
			int dosation_1_Row = 0;
			int dosation_5_Row = 0;
			int dosation_10_Row = 0;
			int dosation_15_Row = 0;
			int dosation_20_Row = 0;
			int dosation_25_Row = 0;
			int dosation_30_Row = 0;
			int dosation_50_Row = 0;
			int dosation_100_Row = 0;
			int DispenserStartPositionRow = 0;
			int DispenserEndPositionRow = 0;
			int ChemMasterStartPositionRow = 0;
			int ChemMasterEndPositionRow = 0;
			int DelayRow = 0;			
			while ((line = reader.readLine()) != null) {
				line = line.split("//")[0].trim();
				String[] values = line.split(",");
				if (values.length != 2) {
					continue;
				}
				int x = Integer.parseInt(values[0].trim());
				int y = Integer.parseInt(values[1].trim());
				if (OxygenRow < 1) {
					Oxygen = new int[1][2];
					Oxygen[0][0] = x;
					Oxygen[0][1] = y;
					OxygenRow++;
				} else if (NitrogenRow < 1) {
					Nitrogen = new int[1][2];
					Nitrogen[0][0] = x;
					Nitrogen[0][1] = y;
					NitrogenRow++;
				} else if (CarbonRow < 1) {
					Carbon = new int[1][2];
					Carbon[0][0] = x;
					Carbon[0][1] = y;
					CarbonRow++;
				} else if (SugarRow < 1) {
					Sugar = new int[1][2];
					Sugar[0][0] = x;
					Sugar[0][1] = y;
					SugarRow++;
				} else if (ChlorineRow < 1) {
					Chlorine = new int[1][2];
					Chlorine[0][0] = x;
					Chlorine[0][1] = y;
					ChlorineRow++;
				} else if (PhosphorusRow < 1) {
					Phosphorus = new int[1][2];
					Phosphorus[0][0] = x;
					Phosphorus[0][1] = y;
					PhosphorusRow++;
				} else if (PotassiumRow < 1) {
					Potassium = new int[1][2];
					Potassium[0][0] = x;
					Potassium[0][1] = y;
					PotassiumRow++;
				} else if (RadiumRow < 1) {
					Radium = new int[1][2];
					Radium[0][0] = x;
					Radium[0][1] = y;
					RadiumRow++;
				} else if (SiliconRow < 1) {
					Silicon = new int[1][2];
					Silicon[0][0] = x;
					Silicon[0][1] = y;
					SiliconRow++;
				} else if (SulfurRow < 1) {
					Sulfur = new int[1][2];
					Sulfur[0][0] = x;
					Sulfur[0][1] = y;
					SulfurRow++;
				} else if (EthanolRow < 1) {
					Ethanol = new int[1][2];
					Ethanol[0][0] = x;
					Ethanol[0][1] = y;
					EthanolRow++;
				} else if (IronRow < 1) {
					Iron = new int[1][2];
					Iron[0][0] = x;
					Iron[0][1] = y;
					IronRow++;
				} else if (LithiumRow < 1) {
					Lithium = new int[1][2];
					Lithium[0][0] = x;
					Lithium[0][1] = y;
					LithiumRow++;
				} else if (MercuryRow < 1) {
					Mercury = new int[1][2];
					Mercury[0][0] = x;
					Mercury[0][1] = y;
					MercuryRow++;
				} else if (FluorineRow < 1) {
					Fluorine = new int[1][2];
					Fluorine[0][0] = x;
					Fluorine[0][1] = y;
					FluorineRow++;
				} else if (HydrogenRow < 1) {
					Hydrogen = new int[1][2];
					Hydrogen[0][0] = x;
					Hydrogen[0][1] = y;
					HydrogenRow++;
				} else if (SodiumRow < 1) {
					Sodium = new int[1][2];
					Sodium[0][0] = x;
					Sodium[0][1] = y;
					SodiumRow++;
				} else if (AluminiumRow < 1) {
					Aluminium = new int[1][2];
					Aluminium[0][0] = x;
					Aluminium[0][1] = y;
					AluminiumRow++;
				} else if (dosation_1_Row < 1) {
					dosation_1 = new int[1][2];
					dosation_1[0][0] = x;
					dosation_1[0][1] = y;
					dosation_1_Row++;
				} else if (dosation_5_Row < 1) {
					dosation_5 = new int[1][2];
					dosation_5[0][0] = x;
					dosation_5[0][1] = y;
					dosation_5_Row++;
				} else if (dosation_10_Row < 1) {
					dosation_10 = new int[1][2];
					dosation_10[0][0] = x;
					dosation_10[0][1] = y;
					dosation_10_Row++;
				} else if (dosation_15_Row < 1) {
					dosation_15 = new int[1][2];
					dosation_15[0][0] = x;
					dosation_15[0][1] = y;
					dosation_15_Row++;
				} else if (dosation_20_Row < 1) {
					dosation_20 = new int[1][2];
					dosation_20[0][0] = x;
					dosation_20[0][1] = y;
					dosation_20_Row++;
				} else if (dosation_25_Row < 1) {
					dosation_25 = new int[1][2];
					dosation_25[0][0] = x;
					dosation_25[0][1] = y;
					dosation_25_Row++;
				} else if (dosation_30_Row < 1) {
					dosation_30 = new int[1][2];
					dosation_30[0][0] = x;
					dosation_30[0][1] = y;
					dosation_30_Row++;
				} else if (dosation_50_Row < 1) {
					dosation_50 = new int[1][2];
					dosation_50[0][0] = x;
					dosation_50[0][1] = y;
					dosation_50_Row++;
				} else if (dosation_100_Row < 1) {
					dosation_100 = new int[1][2];
					dosation_100[0][0] = x;
					dosation_100[0][1] = y;
					dosation_100_Row++;
				} else if (DispenserStartPositionRow < 1) {
					DispenserStartPosition = new int[1][2];
					DispenserStartPosition[0][0] = x;
					DispenserStartPosition[0][1] = y;
					DispenserStartPositionRow++;
				} else if (DispenserEndPositionRow < 1) {
					DispenserEndPosition = new int[1][2];
					DispenserEndPosition[0][0] = x;
					DispenserEndPosition[0][1] = y;
					DispenserEndPositionRow++;
				} else if (ChemMasterStartPositionRow < 1) {
					ChemMasterStartPosition = new int[1][2];
					ChemMasterStartPosition[0][0] = x;
					ChemMasterStartPosition[0][1] = y;
					ChemMasterStartPositionRow++;
				} else if (ChemMasterEndPositionRow < 1) {
					ChemMasterEndPosition = new int[1][2];
					ChemMasterEndPosition[0][0] = x;
					ChemMasterEndPosition[0][1] = y;
					ChemMasterEndPositionRow++;
				} else if (DelayRow < 1) {
					Delay_duration = new int[1][2];
					Delay_duration[0][0] = x;
					Delay_duration[0][1] = y;
					DelayRow++;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void watchConfigFile(File configFile) {
		try {
			Path configPath = configFile.toPath().toAbsolutePath().getParent();
			WatchService watchService = configPath.getFileSystem().newWatchService();
			configPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				WatchKey key = watchService.take();
				for (WatchEvent<?> event : key.pollEvents()) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						if (event.context().toString().equals(CONFIG_FILE)) {
							LocalDateTime timestamp = LocalDateTime.now();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
							String formattedTimestamp = timestamp.format(formatter);
							System.out.println("Configuration file changed " + formattedTimestamp+"  Debug");
							loadConfigData();
						}
					}
				}
				key.reset();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void Delay() {
		int time = Delay_duration[0][0];
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void clickOnScreen(int x, int y) {
		Delay();
		bot.mouseMove(x, y);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ChemistryHelperEN());
	}
}