import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
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

public class helper extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONFIG_FILE = "config.txt";

	private static int[][] oxygen;
	private static int[][] nitrogen;
	private static int[][] carbon;
	private static int[][] sugar;
	private static int[][] chlorine;
	private static int[][] phosphorus;
	private static int[][] potassium;
	private static int[][] radium;
	private static int[][] silicon;
	private static int[][] sulfur;
	private static int[][] ethanol;
	private static int[][] iron;
	private static int[][] lithium;
	private static int[][] mercury;
	private static int[][] fluorine;
	private static int[][] hydrogen;
	private static int[][] sodium;
	private static int[][] aluminium;

	private static int[][] dosation_1;
	private static int[][] dosation_5;
	private static int[][] dosation_10;
	private static int[][] dosation_15;
	private static int[][] dosation_20;
	private static int[][] dosation_25;
	private static int[][] dosation_30;
	private static int[][] dosation_50;
	private static int[][] dosation_100;

	private static int[][] dispenserStartPosition;
	private static int[][] dispenserEndPosition;
	private static int[][] ChemMasterStartPosition;
	private static int[][] ChemMasterEndPosition;

	private static int[][] delay_duration;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private TextField message;
	private int initialX, initialY;

	private static Robot bot;

	public helper() {
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

		// Create a panel for the menu options
		JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		menuPanel.setBackground(Color.DARK_GRAY);

		// Create menu buttons with custom style
		JButton button1 = createButton("Medical");
		JButton button2 = createButton("Other");
		JButton button3 = createButton("Test");
		JButton button4 = createButton("Code");
		JButton button5 = createButton("Coordinates");

		// Add action listeners to the buttons
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);

		// Add buttons to the menu panel
		menuPanel.add(button1);
		menuPanel.add(button2);
		menuPanel.add(button3);
		menuPanel.add(button4);
		menuPanel.add(button5);

		// Create panels with different colors
		JPanel medPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		medPanel.setBackground(Color.BLUE);
		JPanel otherPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		otherPanel.setBackground(Color.GREEN);
		JPanel testPanel = new JPanel(new GridLayout(4, 5, 3, 3));
		testPanel.setBackground(Color.RED);

		// Create a card layout and panel to hold the panels
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.add(medPanel, "Medical");
		cardPanel.add(otherPanel, "Other");
		cardPanel.add(testPanel, "Test");

		// Set medPanel as the default visible panel
		cardLayout.show(cardPanel, "First");

		// Create status label with a blue outline and futuristic style
		message = new TextField("Я просто звичайний рядок!");
		message.setFont(new Font("Arial", Font.BOLD, 24));
		message.setBackground(Color.BLACK);
		message.setForeground(Color.CYAN);
		message.setEditable(false);
		message.setBackground(Color.DARK_GRAY);

		// Create a panel to hold the menuPanel and message
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.add(menuPanel, BorderLayout.NORTH);
		headerPanel.add(message, BorderLayout.CENTER);

		// Add headerPanel and cardPanel to the frame
		add(headerPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);

		setUndecorated(true);
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

		JButton closeButton = new JButton();
		closeButton.setBackground(Color.RED);
		closeButton.setPreferredSize(new Dimension(20, 20));
		closeButton.setBorder(null);
		menuPanel.add(closeButton);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Close the program when the button is clicked
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
					message.setText("Доступ до конфігураційного файлу надано");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton MoveDispenser = createButton("Dispenser", medPanel);
		JButton MoveMaster = createButton("ChemMaster", medPanel);
		
		JButton Bicardine = createButton("Bicardine", medPanel);
		JButton Dermaline = createButton("Dermaline", medPanel);
		JButton Dylovene = createButton("Dylovene", medPanel);
		JButton DexalinePlus = createButton("Dexaline+", medPanel);
		JButton Arithatrine = createButton("Arithatrine", medPanel);
		JButton Phalanximine = createButton("Phalanximine", medPanel);
		JButton Spaceacillin = createButton("Cpaceacilin", medPanel);
		JButton TranexamicAcid = createButton("TranexamicAcid", medPanel);
		JButton Tricodrazine = createButton("Tricodrazine", medPanel);
		
		
		JButton ChloralHydrate = createButton("ChloralHydrate", otherPanel);
		JButton EzNutritient = createButton("EzNutritient", otherPanel);
		JButton SpaceDrugs = createButton("SpaceDrugs", otherPanel);
		JButton Cleaner = createButton("Cleaner", otherPanel);
		JButton Lube = createButton("Lube", otherPanel);
		JButton UnstableMutagen = createButton("UnstableMutagen", otherPanel);
		JButton RobustHarvest = createButton("Robust", otherPanel);
		JButton Ethylredoxrazine = createButton("Ethylredoxrazine", otherPanel);
		JButton Ipecac = createButton("Ipecac", otherPanel);
		JButton Fluorosurfactant = createButton("Fluorosurfactant", otherPanel);
		
		
		
		JButton Oxygen = createButton("Oxygen", testPanel);
		JButton Nitrogen = createButton("Nitrogen", testPanel);
		JButton Sugar = createButton("Sugar", testPanel);
		JButton Chlorine = createButton("Chlorine", testPanel);
		JButton Phosphorus = createButton("Phosphorus", testPanel);
		JButton Potassium = createButton("Potassium", testPanel);
		JButton Radium = createButton("Radium", testPanel);
		JButton Silicon = createButton("Silicon", testPanel);
		JButton Sulfur = createButton("Sulfur", testPanel);
		JButton Ethanol = createButton("Ethanol", testPanel);
		JButton Iron = createButton("Iron", testPanel);
		JButton Lithium = createButton("Lithium", testPanel);
		JButton Mercury = createButton("Mercury", testPanel);
		JButton Fluorine = createButton("Fluorine", testPanel);
		JButton Hydrogen = createButton("Hydrogen", testPanel);
		JButton Sodium = createButton("Sodium", testPanel);
		JButton Aluminium = createButton("Aluminium", testPanel);
		
		JButton Dosation_1u = createButton("Dosation_1u", testPanel);
		JButton Dosation_5u = createButton("Dosation_5u", testPanel);
		JButton Dosation_10u = createButton("Dosation_10u", testPanel);
		JButton Dosation_15u = createButton("Dosation_15u", testPanel);
		JButton Dosation_20u = createButton("Dosation_20u", testPanel);
		JButton Dosation_25u = createButton("Dosation_25u", testPanel);
		JButton Dosation_30u = createButton("Dosation_30u", testPanel);
		JButton Dosation_50u = createButton("Dosation_50u", testPanel);
		JButton Dosation_100u = createButton("Dosation_100u", testPanel);
		

		Oxygen.addActionListener((ActionEvent e) -> {make(oxygen);});
		Nitrogen.addActionListener((ActionEvent e) -> {make(nitrogen);});
		Sugar.addActionListener((ActionEvent e) -> {make(sugar);});
		Chlorine.addActionListener((ActionEvent e) -> {make(chlorine);});
		Phosphorus.addActionListener((ActionEvent e) -> {make(phosphorus);});
		Potassium.addActionListener((ActionEvent e) -> {make(potassium);});
		Radium.addActionListener((ActionEvent e) -> {make(radium);});
		Silicon.addActionListener((ActionEvent e) -> {make(silicon);});
		Sulfur.addActionListener((ActionEvent e) -> {make(sulfur);});
		Ethanol.addActionListener((ActionEvent e) -> {make(ethanol);});
		Iron.addActionListener((ActionEvent e) -> {make(iron);});
		Lithium.addActionListener((ActionEvent e) -> {make(lithium);});
		Mercury.addActionListener((ActionEvent e) -> {make(mercury);});
		Fluorine.addActionListener((ActionEvent e) -> {make(fluorine);});
		Hydrogen.addActionListener((ActionEvent e) -> {make(hydrogen);});
		Sodium.addActionListener((ActionEvent e) -> {make(sodium);});
		Aluminium.addActionListener((ActionEvent e) -> {make(aluminium);});
		
		Dosation_1u.addActionListener((ActionEvent e) -> {make(dosation_1);});
		Dosation_5u.addActionListener((ActionEvent e) -> {make(dosation_5);});
		Dosation_10u.addActionListener((ActionEvent e) -> {make(dosation_10);});
		Dosation_15u.addActionListener((ActionEvent e) -> {make(dosation_15);});
		Dosation_20u.addActionListener((ActionEvent e) -> {make(dosation_20);});
		Dosation_25u.addActionListener((ActionEvent e) -> {make(dosation_25);});
		Dosation_30u.addActionListener((ActionEvent e) -> {make(dosation_30);});
		Dosation_50u.addActionListener((ActionEvent e) -> {make(dosation_50);});
		Dosation_100u.addActionListener((ActionEvent e) -> {make(dosation_100);});
		MoveDispenser.addActionListener((ActionEvent e) -> {moveFromTo(dispenserStartPosition, dispenserEndPosition);});
		MoveMaster.addActionListener((ActionEvent e) -> {moveFromTo(ChemMasterStartPosition, ChemMasterEndPosition);});

		Bicardine.addActionListener((ActionEvent e) -> {
			make(dosation_15);
			make(oxygen);
			make(sugar);
			make(carbon);
			make(carbon);
			make(carbon);
			make(carbon);
			message.setText("Бікардин (фіз.травми, * мг)");
		});
		Dermaline.addActionListener((ActionEvent e) -> {
			make(dosation_15);
			make(silicon);
			make(oxygen);
			make(carbon);
			make(oxygen);
			make(phosphorus);
			make(phosphorus);
			message.setText("Бікардин (фіз.травми, * мг)");
		});
		Dylovene.addActionListener((ActionEvent e) -> {
			make(dosation_30);
			make(silicon);
			make(potassium);
			make(nitrogen);
			message.setText("Диловен (отруєння, * мг)");
		});
		Arithatrine.addActionListener((ActionEvent e) -> {
			make(dosation_5);
			make(silicon);
			make(potassium);
			make(nitrogen);
			make(dosation_15);
			make(radium);
			make(hydrogen);
			make(hydrogen);
			message.setText("Диловен (отруєння, * мг)");
		});
		DexalinePlus.addActionListener((ActionEvent e) -> {
			make(dosation_20);
			make(oxygen);
			make(dosation_30);
			make(iron);
			make(carbon);
			message.setText("Декасалін Плюс (асфіксія, * мг) plasma!");
		});

		Phalanximine.addActionListener((ActionEvent e) -> {
			make(dosation_5);
			make(nitrogen);
			make(potassium);
			make(silicon);
			make(dosation_10);
			make(chlorine);
			make(phosphorus);
			make(dosation_25);
			make(radium);
			make(dosation_30);
			make(ethanol);
			message.setText("Фалаксимін (ліки від раку, * мг)");
		});
		ChloralHydrate.addActionListener((ActionEvent e) -> {
			make(dosation_20);
			make(ethanol);
			make(dosation_100);
			make(chlorine);
			message.setText("Хлоралгідрат(снодійне, * мг)water 20!");
		});
		EzNutritient.addActionListener((ActionEvent e) -> {
			make(dosation_30);
			make(nitrogen);
			make(phosphorus);
			make(potassium);
			message.setText("Найпростіші добрива!");
		});
		SpaceDrugs.addActionListener((ActionEvent e) -> {
			make(dosation_30);
			make(lithium);
			make(mercury);
			make(sugar);
			message.setText("Не показувати СБ!");
		});
		Cleaner.addActionListener((ActionEvent e) -> {
			make(dosation_15);
			make(hydrogen);
			make(dosation_5);
			make(nitrogen);
			make(dosation_20);
			make(chlorine);
			make(sodium);
			make(oxygen);
			message.setText("Найкраший засіб від бруду");
		});
		Lube.addActionListener((ActionEvent e) -> {
			make(dosation_30);
			make(oxygen);
			make(silicon);
			message.setText("Космічна змазка!30 water");
		});
		Tricodrazine.addActionListener((ActionEvent e) -> {
			make(dosation_15);
			make(oxygen);
			make(sugar);
			make(carbon);
			make(potassium);
			make(silicon);
			make(nitrogen);
			message.setText("Вітаміни");
		});

		UnstableMutagen.addActionListener((ActionEvent e) -> {
			make(dosation_30);
			make(chlorine);
			make(phosphorus);
			make(radium);
			message.setText("Нестабільний мутаген");
		});
		RobustHarvest.addActionListener((ActionEvent e) -> {
			make(dosation_30);
			make(nitrogen);
			make(phosphorus);
			make(potassium);
			make(dosation_1);
			make(sulfur);
			make(hydrogen);
			make(oxygen);
			make(oxygen);
			message.setText("Найкращі добрива на станції");
		});
		Spaceacillin.addActionListener((ActionEvent e) -> {
			make(dosation_15);
			make(oxygen);
			make(oxygen);
			make(sugar);
			make(sugar);
			make(carbon);
			make(potassium);
			message.setText("Космоліцин (антибіотик * мг)");
		});
		TranexamicAcid.addActionListener((ActionEvent e) -> {
			make(dosation_10);
			make(oxygen);
			make(sugar);
			make(carbon);
			make(dosation_30);
			make(sugar);
			make(dosation_10);
			make(hydrogen);
			make(sulfur);
			make(oxygen);
			make(oxygen);
			message.setText("Кровоспинний засіб(* мг) ");
		});
		Ethylredoxrazine.addActionListener((ActionEvent e) -> {
			make(dosation_10);
			make(silicon);
			make(potassium);
			make(nitrogen);
			make(dosation_30);
			make(oxygen);
			make(carbon);
			message.setText("Від похмілля(* мг) ");
		});
		Ipecac.addActionListener((ActionEvent e) -> {
			make(dosation_10);
			make(hydrogen);
			make(hydrogen);
			make(hydrogen);
			make(nitrogen);
			make(silicon);
			make(potassium);
			make(nitrogen);
			message.setText("Дуже різкий смак ");
		});
		Fluorosurfactant.addActionListener((ActionEvent e) -> {
			make(dosation_5);
			make(hydrogen);
			make(sulfur);
			make(oxygen);
			make(oxygen);
			make(dosation_30);
			make(fluorine);
			make(carbon);
			message.setText("Піниться при контакті з водою");
		});

		Thread configFileThread = new Thread(() -> watchConfigFile(configFile));
		configFileThread.start();

		menuPanel.add(closeButton, BorderLayout.EAST);
		menuPanel.add(openFolderButton, BorderLayout.WEST);

		
		pack();
		setVisible(true);
		setAlwaysOnTop(true);
	}

	private static JButton createButton(String text, JPanel panel) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 12));
		button.setBackground(Color.GRAY);
		button.setForeground(Color.WHITE);
		panel.add(button);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("Coordinates")) {
			createCoordinatesFrame();
		} else if (actionCommand.equals("Code")) {
			message.setText("https://github.com/Exaster/Space-Station-14-Chemistry-Helper");
		} else {
			cardLayout.show(cardPanel, actionCommand);
		}
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return button;
	}

	private void createCoordinatesFrame() {
		JFrame frame = new JFrame("Cursor Coordinates");
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

		// Add dragging functionality
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
				label.setText("Coordinates: X=" + x + ", Y=" + y);
			}
		});

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				timer.stop();
			}
		});

		// Create a small button as a red square
		JButton closeButton = new JButton();
		closeButton.setBackground(Color.RED);
		closeButton.setPreferredSize(new Dimension(10, 10));
		closeButton.setBorder(null);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // Close the frame when the button is clicked
			}
		});

		// Add the button to the bottom-right corner of the frame
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(false);
		buttonPanel.add(closeButton);
		frame.add(buttonPanel, BorderLayout.SOUTH);

		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		timer.start();
	}

	private static void make(int[][]... instructions) {
		for (int[][] instruction : instructions) {
			for (int[] coord : instruction) {
				int x = coord[0];
				int y = coord[1];
				clickOnScreen(x, y);
			}
		}
	}

	private static void moveFromTo(int[][] startCoords, int[][] endCoords) {
		try {
			Robot robot = new Robot();
			int startX = startCoords[0][0];
			int startY = startCoords[0][1];
			int endX = endCoords[0][0];
			int endY = endCoords[0][1];
			robot.mouseMove(startX, startY);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			delay();
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

	private static void loadConfigData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
			String line;
			int oxygenRow = 0;
			int nitrogenRow = 0;
			int carbonRow = 0;
			int sugarRow = 0;
			int chlorineRow = 0;
			int phosphorusRow = 0;
			int potassiumRow = 0;
			int radiumRow = 0;
			int siliconRow = 0;
			int sulfurRow = 0;
			int ethanolRow = 0;
			int ironRow = 0;
			int lithiumRow = 0;
			int mercuryRow = 0;
			int fluorineRow = 0;
			int hydrogenRow = 0;
			int sodiumRow = 0;
			int aluminiumRow = 0;

			int dose_1_Row = 0;
			int dose_5_Row = 0;
			int dose_10_Row = 0;
			int dose_15_Row = 0;
			int dose_20_Row = 0;
			int dose_25_Row = 0;
			int dose_30_Row = 0;
			int dose_50_Row = 0;
			int dose_100_Row = 0;

			int DispenserStartPositionRow = 0;
			int DispenserEndPositionRow = 0;
			int ChemMasterStartPositionRow = 0;
			int ChemMasterEndPositionRow = 0;

			int delayRow = 0;

			// Add variables for other chemicals here...

			while ((line = reader.readLine()) != null) {
				line = line.split("//")[0].trim();
				String[] values = line.split(",");
				if (values.length != 2) {
					continue;
				}
				int x = Integer.parseInt(values[0].trim());
				int y = Integer.parseInt(values[1].trim());
				if (oxygenRow < 1) {
					oxygen = new int[1][2];
					oxygen[0][0] = x;
					oxygen[0][1] = y;
					oxygenRow++;
				} else if (nitrogenRow < 1) {
					nitrogen = new int[1][2];
					nitrogen[0][0] = x;
					nitrogen[0][1] = y;
					nitrogenRow++;
				} else if (carbonRow < 1) {
					carbon = new int[1][2];
					carbon[0][0] = x;
					carbon[0][1] = y;
					carbonRow++;
				} else if (sugarRow < 1) {
					sugar = new int[1][2];
					sugar[0][0] = x;
					sugar[0][1] = y;
					sugarRow++;
				} else if (chlorineRow < 1) {
					chlorine = new int[1][2];
					chlorine[0][0] = x;
					chlorine[0][1] = y;
					chlorineRow++;
				} else if (phosphorusRow < 1) {
					phosphorus = new int[1][2];
					phosphorus[0][0] = x;
					phosphorus[0][1] = y;
					phosphorusRow++;
				} else if (potassiumRow < 1) {
					potassium = new int[1][2];
					potassium[0][0] = x;
					potassium[0][1] = y;
					potassiumRow++;
				} else if (radiumRow < 1) {
					radium = new int[1][2];
					radium[0][0] = x;
					radium[0][1] = y;
					radiumRow++;
				} else if (siliconRow < 1) {
					silicon = new int[1][2];
					silicon[0][0] = x;
					silicon[0][1] = y;
					siliconRow++;
				} else if (sulfurRow < 1) {
					sulfur = new int[1][2];
					sulfur[0][0] = x;
					sulfur[0][1] = y;
					sulfurRow++;
				} else if (ethanolRow < 1) {
					ethanol = new int[1][2];
					ethanol[0][0] = x;
					ethanol[0][1] = y;
					ethanolRow++;
				} else if (ironRow < 1) {
					iron = new int[1][2];
					iron[0][0] = x;
					iron[0][1] = y;
					ironRow++;
				} else if (lithiumRow < 1) {
					lithium = new int[1][2];
					lithium[0][0] = x;
					lithium[0][1] = y;
					lithiumRow++;
				} else if (mercuryRow < 1) {
					mercury = new int[1][2];
					mercury[0][0] = x;
					mercury[0][1] = y;
					mercuryRow++;
				} else if (fluorineRow < 1) {
					fluorine = new int[1][2];
					fluorine[0][0] = x;
					fluorine[0][1] = y;
					fluorineRow++;
				} else if (hydrogenRow < 1) {
					hydrogen = new int[1][2];
					hydrogen[0][0] = x;
					hydrogen[0][1] = y;
					hydrogenRow++;
				} else if (sodiumRow < 1) {
					sodium = new int[1][2];
					sodium[0][0] = x;
					sodium[0][1] = y;
					sodiumRow++;
				} else if (aluminiumRow < 1) {
					aluminium = new int[1][2];
					aluminium[0][0] = x;
					aluminium[0][1] = y;
					aluminiumRow++;
				} else if (dose_1_Row < 1) {
					dosation_1 = new int[1][2];
					dosation_1[0][0] = x;
					dosation_1[0][1] = y;
					dose_1_Row++;
				} else if (dose_5_Row < 1) {
					dosation_5 = new int[1][2];
					dosation_5[0][0] = x;
					dosation_5[0][1] = y;
					dose_5_Row++;
				} else if (dose_10_Row < 1) {
					dosation_10 = new int[1][2];
					dosation_10[0][0] = x;
					dosation_10[0][1] = y;
					dose_10_Row++;
				} else if (dose_15_Row < 1) {
					dosation_15 = new int[1][2];
					dosation_15[0][0] = x;
					dosation_15[0][1] = y;
					dose_15_Row++;
				} else if (dose_20_Row < 1) {
					dosation_20 = new int[1][2];
					dosation_20[0][0] = x;
					dosation_20[0][1] = y;
					dose_20_Row++;
				} else if (dose_25_Row < 1) {
					dosation_25 = new int[1][2];
					dosation_25[0][0] = x;
					dosation_25[0][1] = y;
					dose_25_Row++;
				} else if (dose_30_Row < 1) {
					dosation_30 = new int[1][2];
					dosation_30[0][0] = x;
					dosation_30[0][1] = y;
					dose_30_Row++;
				} else if (dose_50_Row < 1) {
					dosation_50 = new int[1][2];
					dosation_50[0][0] = x;
					dosation_50[0][1] = y;
					dose_50_Row++;
				} else if (dose_100_Row < 1) {
					dosation_100 = new int[1][2];
					dosation_100[0][0] = x;
					dosation_100[0][1] = y;
					dose_100_Row++;
				} else if (DispenserStartPositionRow < 1) {
					dispenserStartPosition = new int[1][2];
					dispenserStartPosition[0][0] = x;
					dispenserStartPosition[0][1] = y;
					DispenserStartPositionRow++;
				} else if (DispenserEndPositionRow < 1) {
					dispenserEndPosition = new int[1][2];
					dispenserEndPosition[0][0] = x;
					dispenserEndPosition[0][1] = y;
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
				} else if (delayRow < 1) {
					delay_duration = new int[1][2];
					delay_duration[0][0] = x;
					delay_duration[0][1] = y;
					delayRow++;
				}

				// Add conditions for other chemicals here...
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void watchConfigFile(File configFile) {
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
							System.out.println("Config file changed" + formattedTimestamp);
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

	private static void delay() {
		int time = delay_duration[0][0];

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void clickOnScreen(int x, int y) {
		delay();
		bot.mouseMove(x, y);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new helper());
	}

}
