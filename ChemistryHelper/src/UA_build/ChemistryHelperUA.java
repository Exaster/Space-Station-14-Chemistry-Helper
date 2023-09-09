package UA_build;
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

public class ChemistryHelperUA extends JFrame implements ActionListener {
	
	public static final long serialVersionUID = 1L;
	public static final String CONFIG_FILE = "UA_config.txt";

	public static int[][] Кисень;
	public static int[][] Азот;
	public static int[][] Вуглець;
	public static int[][] Цукор;
	public static int[][] Хлор;
	public static int[][] Фосфор;
	public static int[][] Калій;
	public static int[][] Радій;
	public static int[][] Кремній;
	public static int[][] Сірка;
	public static int[][] Етанол;
	public static int[][] Залізо;
	public static int[][] Літій;
	public static int[][] Ртуть;
	public static int[][] Фтор;
	public static int[][] Водень;
	public static int[][] Натрій;
	public static int[][] Алюміній;
	public static int[][] доза_1;
	public static int[][] доза_5;
	public static int[][] доза_10;
	public static int[][] доза_15;
	public static int[][] доза_20;
	public static int[][] доза_25;
	public static int[][] доза_30;
	public static int[][] доза_50;
	public static int[][] доза_100;
	
	public static int[][] ДозаторStartPosition;
	public static int[][] ДозаторEndPosition;
	public static int[][] ChemMasterStartPosition;
	public static int[][] ChemMasterEndPosition;
	public static int[][] затримка_duration;
	
	public JPanel cardPanel;
	public CardLayout cardLayout;
	public static TextField message;	
	public static int initialX;
	public static int initialY;	
	private static Timer timer;
	public static Robot bot;

	@SuppressWarnings("unused")
	public ChemistryHelperUA() {
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
		JButton button1 = createButton("Медична");
		JButton button2 = createButton("Інше");
		JButton button3 = createButton("Тест");
		JButton button4 = createButton("Код");
		JButton button5 = createButton("Координати");
		JButton button6 = createButton("Налаштування");

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
		

		JPanel МедичнаПанель = new JPanel(new GridLayout(4, 5, 3, 3));
		МедичнаПанель.setBackground(Color.BLUE);
		JPanel ПанельРізне = new JPanel(new GridLayout(4, 5, 3, 3));
		ПанельРізне.setBackground(Color.GREEN);
		JPanel ПанельТестування = new JPanel(new GridLayout(4, 5, 3, 3));
		ПанельТестування.setBackground(Color.RED);		
		JPanel ПанельНалаштувань = new JPanel(new GridLayout(4, 5, 3, 3));
		ПанельНалаштувань.setBackground(Color.YELLOW);	
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.add(МедичнаПанель, "Медична");
		cardPanel.add(ПанельРізне, "Інше");
		cardPanel.add(ПанельТестування, "Тест");
		cardPanel.add(ПанельНалаштувань, "Налаштування");	
		cardLayout.show(cardPanel, "First");

		message = new TextField("Підказки будуть тут");
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
		/********************************Кнопки та їх призначення*************************************************************/
		//_______________________________Кнопки загалного призначення_________________________________________________________//
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
					message.setText("Доступ до файлу налаштувань надано");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});		
		//_______________________________Медична панель________________________________________________________//
		/*Кнопки та сворення рецепту
		 *Щоб добавити нові кпопки потрібно дотримуватись шаблону:
		 *JButton ~назва кнопки~ = createButton(~назва яка висвітлюватиметься на кнопці~, ~Панель розташування кнопки~,(ActionEvent e) -> {make(~рецепт та пропорції через кому, коментар~};})
		 *Назви простих речовин вказані як змінні з великої літери (Приклад: Кисень)
		 *Дозація речовин наявна за щаблном доза_~стандартні дозації наявні в грі Space Station 14~
		 *Задля кращої читабельності коду рекомендується прописувати рецепти сумішей не врядок, а вертрикально*/
		JButton MoveДозатор = createButton("Дозатор", МедичнаПанель,(ActionEvent e) -> {moveFromTo(ДозаторStartPosition, ДозаторEndPosition);});
		JButton MoveMaster = createButton("ChemMaster", МедичнаПанель,(ActionEvent e) -> {moveFromTo(ChemMasterStartPosition, ChemMasterEndPosition);});		
		JButton Бікардин = createButton("Бікардин", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_15,
			        Кисень,
			        Цукор,
			        Вуглець,
			        Вуглець,
			        Вуглець,
			        Вуглець,
			        "Бікардин (фіз.травми, * мг)"
			    );
			});
		JButton Дермалін = createButton("Дермалін", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_15,
			        Кремній,
			        Кисень,
			        Вуглець,
			        Кисень,
			        Фосфор,
			        Фосфор,
			        "Дермалін (опіки, * мг)"
			    );
			});
		JButton Диловен = createButton("Диловен", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_30,
			        Кремній,
			        Калій,
			        Азот,
			        "Диловен (отруєння, * мг)"
			    );
			});
		JButton ДексалінПлюс = createButton("Dexaline+", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_20,
			        Кисень,
			        доза_30,
			        Залізо,
			        Вуглець,
			        "Декасалін Плюс (асфіксія, * мг) plasma!"
			    );
			});
		JButton Аризатрин = createButton("Аризатрин", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_5,
			        Кремній,
			        Калій,
			        Азот,
			        доза_15,
			        Радій,
			        Водень,
			        Водень,
			        "Аризатрин (радіація, * мг)"
			    );
			});
		JButton Фалаксимін = createButton("Фалаксимін", МедичнаПанель, (ActionEvent e) -> {
			    make(
			        доза_5,
			        Азот,
			        Калій,
			        Кремній,
			        доза_10,
			        Хлор,
			        Фосфор,
			        доза_25,
			        Радій,
			        доза_30,
			        Етанол,
			        "Фалаксимін (ліки від раку, * мг)"
			    );
			});
		JButton Спейсацілін = createButton("Спейсаліцин", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_15,
			        Кисень,
			        Кисень,
			        Цукор,
			        Цукор,
			        Вуглець,
			        Калій,
			        "Космоліцин (антибіотик, не актуально)"
			    );
			});
		JButton ТранексамоваКислота = createButton("ТранексамоваКислота", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_10,
			        Кисень,
			        Цукор,
			        Вуглець,
			        доза_30,
			        Цукор,
			        доза_10,
			        Водень,
			        Сірка,
			        Кисень,
			        Кисень,
			        "Кровоспинний засіб(* мг)"
			    );
			});
		JButton Трікордразин = createButton("Трікордразин", МедичнаПанель,(ActionEvent e) -> {
			    make(
			        доза_15,
			        Кисень,
			        Цукор,
			        Вуглець,
			        Калій,
			        Кремній,
			        Азот,
			        "Вітаміни"
			    );
			});		
		JButton Хлоралгідрат = createButton("Хлоралгідрат", ПанельРізне, (ActionEvent e) -> {
			    make(
			        доза_20,
			        Етанол,
			        доза_100,
			        Хлор,
			        "Хлоралгідрат(снодійне, * мг) 20 води!"
			    );
			});
		JButton EZнавоз = createButton("EZнавоз", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_30,
			        Азот,
			        Фосфор,
			        Калій,
			        "Найпростіші добрива!"
			    );
			});
		JButton КосмічніНаркотики = createButton("Наркотики", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_30,
			        Літій,
			        Ртуть,
			        Цукор,
			        "Не показувати СБ!"
			    );
			});
		JButton Відбілювач = createButton("Відбілювач", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_15,
			        Водень,
			        доза_5,
			        Азот,
			        доза_20,
			        Хлор,
			        Натрій,
			        Кисень,
			        "Найкраший засіб від бруду"
			    );
			});
		JButton Змазка = createButton("Змазка", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_30,
			        Кисень,
			        Кремній,
			        "Космічна змазка!30 води"
			    );
			});
		JButton НестабільнийМутаген = createButton("Мутаген", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_30,
			        Хлор,
			        Фосфор,
			        Радій,
			        "Нестабільний мутаген"
			    );
			});
		JButton МіцнийВрожай = createButton("Робуста", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_30,
			        Азот,
			        Фосфор,
			        Калій,
			        доза_1,
			        Сірка,
			        Водень,
			        Кисень,
			        Кисень,
			        "Найкращі добрива на станції!"
			    );
			});
		JButton Етилредоксразин = createButton("Етилредоксразин", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_10,
			        Кремній,
			        Калій,
			        Азот,
			        доза_30,
			        Кисень,
			        Вуглець,
			        "Від похмілля(* мг)"
			    );
			});
		JButton Іпекак  = createButton("Іпекак ", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_10,
			        Водень,
			        Водень,
			        Водень,
			        Азот,
			        Кремній,
			        Калій,
			        Азот,
			        "Дуже різкий смак, викликає нудоту"
			    );
			});
		JButton Fluorosurfactant = createButton("Fluorosurfactant", ПанельРізне,(ActionEvent e) -> {
			    make(
			        доза_5,
			        Водень,
			        Сірка,
			        Кисень,
			        Кисень,
			        доза_30,
			        Фтор,
			        Вуглець,
			        "Піниться при контакті з водою"
			    );
			});		
		//_______________________________Кнопки панелі тестування_________________________________________________________//
		JButton btnКисень = createButton("Кисень", ПанельТестування,(ActionEvent e) -> {make(Кисень,"Кисень");});
		JButton btnАзот = createButton("Азот", ПанельТестування,(ActionEvent e) -> {make(Азот,"Азот");});
		JButton btnВуглець = createButton("Вуглець", ПанельТестування,(ActionEvent e) -> {make(Вуглець,"Вуглець");});
		JButton btnЦукор = createButton("Цукор", ПанельТестування,(ActionEvent e) -> {make(Цукор,"Цукор");});
		JButton btnХлор = createButton("Хлор", ПанельТестування,(ActionEvent e) -> {make(Хлор,"Хлор");});
		JButton btnФосфор = createButton("Фосфор", ПанельТестування,(ActionEvent e) -> {make(Фосфор,"Фосфор");});
		JButton btnКалій = createButton("Калій", ПанельТестування,(ActionEvent e) -> {make(Калій,"Калій");});
		JButton btnРадій = createButton("Радій", ПанельТестування,(ActionEvent e) -> {make(Радій,"Радій");});
		JButton btnКремній = createButton("Кремній", ПанельТестування,(ActionEvent e) -> {make(Вуглець,"Цукор");});
		JButton btnСірка = createButton("Сірка", ПанельТестування,(ActionEvent e) -> {make(Сірка,"Сірка");});
		JButton btnЕтанол = createButton("Етанол", ПанельТестування,(ActionEvent e) -> {make(Етанол,"Етанол");});
		JButton btnЗалізо = createButton("Залізо", ПанельТестування,(ActionEvent e) -> {make(Залізо,"Залізо");});
		JButton btnЛітій = createButton("Літій", ПанельТестування,(ActionEvent e) -> {make(Літій,"Літій");});
		JButton btnРтуть = createButton("Ртуть", ПанельТестування,(ActionEvent e) -> {make(Вуглець,"Цукор");});
		JButton btnФтор = createButton("Фтор", ПанельТестування,(ActionEvent e) -> {make(Фтор,"Фтор");});
		JButton btnВодень = createButton("Водень", ПанельТестування,(ActionEvent e) -> {make(Водень,"Водень");});
		JButton btnНатрій = createButton("Натрій", ПанельТестування,(ActionEvent e) -> {make(Натрій,"Натрій");});
		JButton btnАлюміній = createButton("Алюміній", ПанельТестування,(ActionEvent e) -> {make(Алюміній,"Алюміній");});
		JButton доза_1u = createButton("1u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 1");});
		JButton доза_5u = createButton("5u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 5");});
		JButton доза_10u = createButton("10u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 10");});
		JButton доза_15u = createButton("15u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 15");});
		JButton доза_20u = createButton("20u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 20");});
		JButton доза_25u = createButton("25u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 25");});
		JButton доза_30u = createButton("30u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 30");});
		JButton доза_50u = createButton("50u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 50");});
		JButton доза_100u = createButton("100u", ПанельТестування,(ActionEvent e) -> {make(доза_1,"доза 100");});
		JButton btnДозаторStartPosition = createButton("Дозатор початок", ПанельТестування,(ActionEvent e) -> {moveFromTo(ДозаторStartPosition, ДозаторStartPosition);});
		JButton disДозаторEndPosition = createButton("Дозатор кінець", ПанельТестування,(ActionEvent e) -> {moveFromTo(ДозаторEndPosition, ДозаторEndPosition);});
		JButton btnChemMasterStartPosition = createButton("Chem початок", ПанельТестування,(ActionEvent e) -> {moveFromTo(ChemMasterStartPosition, ChemMasterStartPosition);});
		JButton btnChemMasterEndPosition = createButton("Chem кінець", ПанельТестування,(ActionEvent e) -> {moveFromTo(ChemMasterEndPosition, ChemMasterEndPosition);});
		//_______________________________Кнопки панелі налаштувань_________________________________________________________//
		JButton setКисень = createButton("Кисень", ПанельНалаштувань,(ActionEvent e) -> {setCoords(1,"Кисень");});
		JButton setАзот = createButton("Азот", ПанельНалаштувань,(ActionEvent e) -> {setCoords(2,"Азот");});
		JButton setВуглець = createButton("Цукор", ПанельНалаштувань,(ActionEvent e) -> {setCoords(3,"Вуглець");});
		JButton setЦукор = createButton("Цукор", ПанельНалаштувань,(ActionEvent e) -> {setCoords(4,"Цукор");});
		JButton setХлор = createButton("Хлор", ПанельНалаштувань,(ActionEvent e) -> {setCoords(5,"Хлор");});
		JButton setФосфор = createButton("Фосфор", ПанельНалаштувань,(ActionEvent e) -> {setCoords(6,"Фосфор");});
		JButton setКалій = createButton("Калій", ПанельНалаштувань,(ActionEvent e) -> {setCoords(7,"Калій");});
		JButton setРадій = createButton("Радій", ПанельНалаштувань,(ActionEvent e) -> {setCoords(8,"Радій");});
		JButton setКремній = createButton("Кремній", ПанельНалаштувань,(ActionEvent e) -> {setCoords(9,"Кремній");});
		JButton setСірка = createButton("Сірка", ПанельНалаштувань,(ActionEvent e) -> {setCoords(10,"Сірка");});
		JButton setЕтанол = createButton("Етанол", ПанельНалаштувань,(ActionEvent e) -> {setCoords(11,"Етанол");});
		JButton setЗалізо = createButton("Залізо", ПанельНалаштувань,(ActionEvent e) -> {setCoords(12,"Залізо");});
		JButton setЛітій = createButton("Літій", ПанельНалаштувань,(ActionEvent e) -> {setCoords(13,"Літій");});
		JButton setРтуть = createButton("Ртуть", ПанельНалаштувань,(ActionEvent e) -> {setCoords(14,"Ртуть");});
		JButton setФтор = createButton("Фтор", ПанельНалаштувань,(ActionEvent e) -> {setCoords(15,"Фтор");});
		JButton setВодень = createButton("Водень", ПанельНалаштувань,(ActionEvent e) -> {setCoords(16,"Водень");});
		JButton setНатрій = createButton("Натрій", ПанельНалаштувань,(ActionEvent e) -> {setCoords(17,"Натрій");});
		JButton setАлюміній = createButton("Алюміній", ПанельНалаштувань,(ActionEvent e) -> {setCoords(18,"Алюміній");});
		JButton setДозація_1у = createButton("1u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(19,"1u");});
		JButton setДозація_5у = createButton("5u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(20,"5u");});
		JButton setДозація_10у = createButton("5u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(21,"10u");});
		JButton setДозація_15у = createButton("10u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(22,"15u");});
		JButton setДозація_20у = createButton("15u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(23,"20u");});
		JButton setДозація_25у = createButton("20u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(24,"25u");});
		JButton setДозація_30у = createButton("25u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(25,"30u");});
		JButton setДозація_50у = createButton("50u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(26,"50u");});
		JButton setДозація_100у = createButton("100u", ПанельНалаштувань,(ActionEvent e) -> {setCoords(27,"100u");});
		JButton setПочаткова_позиція_дозатора = createButton("Початкова_позиція_дозатора", ПанельНалаштувань,(ActionEvent e) -> {setCoords(28,"Кисень");});
		JButton setКінцева_позиція_дозатора = createButton("Кінцева_позиція_дозатора", ПанельНалаштувань,(ActionEvent e) -> {setCoords(29,"Кисень");});
		JButton setПочаткова_позиція_ChemMaster = createButton("Початкова_позиція_ChemMaster", ПанельНалаштувань,(ActionEvent e) -> {setCoords(30,"Кисень");});
		JButton setКінцева_позиція_ChemMaster = createButton("Кінцева_позиція_ChemMaster", ПанельНалаштувань,(ActionEvent e) -> {setCoords(31,"Кисень");});
		Thread configFileThread = new Thread(() -> watchConfigFile(configFile));
		configFileThread.start();		
		menuPanel.add(openFolderButton, BorderLayout.WEST);
		setTitle("Помічник хіміка");
		setSize(470,200);
		setVisible(true);
		setAlwaysOnTop(true);		
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
		if (actionCommand.equals("Координати")) {
			createCoordinatesFrame();
		} else if (actionCommand.equals("Код")) {
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
		message.setText("Налаштування кнопок");
		JFrame frame = new JFrame("Координати вказівника");
		frame.setUndecorated(true);
		frame.setSize(250, 100);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Вказівник: X=0, Y=0");
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
				label.setText("<html>Вказівник: X=" + x + ", Y=" + y + "<br>Змінюємо: " + newText
						+ "<br>Enter для зміни " + "</html>");
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
			File configFile = new File("UA_config.txt");
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

				String updatedLine = String.format("%d,%d // змінено `%s`", cursorX, cursorY, newText);
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
		JFrame frame = new JFrame("Координати вказівника");
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(250, 100);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Вказівник: X=0, Y=0");
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
				label.setText("Вказівник: X=" + x + ", Y=" + y);
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
	public static void make(Object... instructions) {
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
			затримка();
			robot.mouseMove(endX, endY);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public static void createDefaultConfigFile() {
	    try {
	        FileWriter writer = new FileWriter(CONFIG_FILE);
	        writer.write("40,150 // Стандартні координати Кисень\n");
	        writer.write("40,95 // Стандартні координати Азот\n");
	        writer.write("290,100 // Стандартні координати Вуглець\n");
	        writer.write("295,205 // Стандартні координати Цукор\n");
	        writer.write("210,215 // Стандартні координати Хлор\n");
	        writer.write("40,215 // Стандартні координати Фосфор\n");
	        writer.write("290,125 // Стандартні координати Калій\n");
	        writer.write("125,180 // Стандартні координати Радій\n");
	        writer.write("135,155 // Стандартні координати Кремній\n");
	        writer.write("295,185 // Стандартні координати Сірка\n");
	        writer.write("40,135 // Стандартні координати Етанол\n");
	        writer.write("130,125 // Стандартні координати Залізо\n");
	        writer.write("215,155 // Стандартні координати Літій\n");
	        writer.write("200,180 // Стандартні координати Ртуть\n");
	        writer.write("125,220 // Стандартні координати Фтор\n");
	        writer.write("220,100 // Стандартні координати Водень\n");
	        writer.write("45,180 // Стандартні координати Натрій\n");
	        writer.write("130,100 // Стандартні координати Алюміній\n");
	        writer.write("95,70 // Стандартні дані для Дозація 1u\n");
	        writer.write("120,70 // Стандартні дані для Дозація 5u\n");
	        writer.write("145,70 // Стандартні дані для Дозація 10u\n");
	        writer.write("175,70 // Стандартні дані для Дозація 15u\n");
	        writer.write("220,70 // Стандартні дані для Дозація 20u\n");
	        writer.write("250,70 // Стандартні дані для Дозація 25u\n");
	        writer.write("275,70 // Стандартні дані для Дозація 30u\n");
	        writer.write("300,70 // Стандартні дані для Дозація 50u\n");
	        writer.write("345,70 // Стандартні дані для Дозація 100u\n");
	        writer.write("420,185 // Стандартні дані для 'Початкова позиція дозатора'\n");
	        writer.write("5,23 // Стандартні дані для 'Кінцева позиція дозатора'\n");
	        writer.write("418,90 // Стандартні дані для 'Початкова позиція ChemMaster'\n");
	        writer.write("10,270 // Стандартні дані для 'Кінцева позиція ChemMaster'\n");
	        writer.write("360,0 // Стандартні дані для часу затримки, використовується лише перше число\n");
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
			int КисеньRow = 0;
			int АзотRow = 0;
			int ВуглецьRow = 0;
			int ЦукорRow = 0;
			int ХлорRow = 0;
			int ФосфорRow = 0;
			int КалійRow = 0;
			int РадійRow = 0;
			int КремнійRow = 0;
			int СіркаRow = 0;
			int ЕтанолRow = 0;
			int ЗалізоRow = 0;
			int ЛітійRow = 0;
			int РтутьRow = 0;
			int ФторRow = 0;
			int ВоденьRow = 0;
			int НатрійRow = 0;
			int АлюмінійRow = 0;
			int доза_1_Row = 0;
			int доза_5_Row = 0;
			int доза_10_Row = 0;
			int доза_15_Row = 0;
			int доза_20_Row = 0;
			int доза_25_Row = 0;
			int доза_30_Row = 0;
			int доза_50_Row = 0;
			int доза_100_Row = 0;
			int ДозатораПочатковаПозиція = 0;
			int ДозатораКінцеваПозиція = 0;
			int ChemMasterПочатковаПозиція = 0;
			int ChemMasterКінцеваПозиція = 0;
			int затримкаRow = 0;			
			while ((line = reader.readLine()) != null) {
				line = line.split("//")[0].trim();
				String[] values = line.split(",");
				if (values.length != 2) {
					continue;
				}
				int x = Integer.parseInt(values[0].trim());
				int y = Integer.parseInt(values[1].trim());
				if (КисеньRow < 1) {
					Кисень = new int[1][2];
					Кисень[0][0] = x;
					Кисень[0][1] = y;
					КисеньRow++;
				} else if (АзотRow < 1) {
					Азот = new int[1][2];
					Азот[0][0] = x;
					Азот[0][1] = y;
					АзотRow++;
				} else if (ВуглецьRow < 1) {
					Вуглець = new int[1][2];
					Вуглець[0][0] = x;
					Вуглець[0][1] = y;
					ВуглецьRow++;
				} else if (ЦукорRow < 1) {
					Цукор = new int[1][2];
					Цукор[0][0] = x;
					Цукор[0][1] = y;
					ЦукорRow++;
				} else if (ХлорRow < 1) {
					Хлор = new int[1][2];
					Хлор[0][0] = x;
					Хлор[0][1] = y;
					ХлорRow++;
				} else if (ФосфорRow < 1) {
					Фосфор = new int[1][2];
					Фосфор[0][0] = x;
					Фосфор[0][1] = y;
					ФосфорRow++;
				} else if (КалійRow < 1) {
					Калій = new int[1][2];
					Калій[0][0] = x;
					Калій[0][1] = y;
					КалійRow++;
				} else if (РадійRow < 1) {
					Радій = new int[1][2];
					Радій[0][0] = x;
					Радій[0][1] = y;
					РадійRow++;
				} else if (КремнійRow < 1) {
					Кремній = new int[1][2];
					Кремній[0][0] = x;
					Кремній[0][1] = y;
					КремнійRow++;
				} else if (СіркаRow < 1) {
					Сірка = new int[1][2];
					Сірка[0][0] = x;
					Сірка[0][1] = y;
					СіркаRow++;
				} else if (ЕтанолRow < 1) {
					Етанол = new int[1][2];
					Етанол[0][0] = x;
					Етанол[0][1] = y;
					ЕтанолRow++;
				} else if (ЗалізоRow < 1) {
					Залізо = new int[1][2];
					Залізо[0][0] = x;
					Залізо[0][1] = y;
					ЗалізоRow++;
				} else if (ЛітійRow < 1) {
					Літій = new int[1][2];
					Літій[0][0] = x;
					Літій[0][1] = y;
					ЛітійRow++;
				} else if (РтутьRow < 1) {
					Ртуть = new int[1][2];
					Ртуть[0][0] = x;
					Ртуть[0][1] = y;
					РтутьRow++;
				} else if (ФторRow < 1) {
					Фтор = new int[1][2];
					Фтор[0][0] = x;
					Фтор[0][1] = y;
					ФторRow++;
				} else if (ВоденьRow < 1) {
					Водень = new int[1][2];
					Водень[0][0] = x;
					Водень[0][1] = y;
					ВоденьRow++;
				} else if (НатрійRow < 1) {
					Натрій = new int[1][2];
					Натрій[0][0] = x;
					Натрій[0][1] = y;
					НатрійRow++;
				} else if (АлюмінійRow < 1) {
					Алюміній = new int[1][2];
					Алюміній[0][0] = x;
					Алюміній[0][1] = y;
					АлюмінійRow++;
				} else if (доза_1_Row < 1) {
					доза_1 = new int[1][2];
					доза_1[0][0] = x;
					доза_1[0][1] = y;
					доза_1_Row++;
				} else if (доза_5_Row < 1) {
					доза_5 = new int[1][2];
					доза_5[0][0] = x;
					доза_5[0][1] = y;
					доза_5_Row++;
				} else if (доза_10_Row < 1) {
					доза_10 = new int[1][2];
					доза_10[0][0] = x;
					доза_10[0][1] = y;
					доза_10_Row++;
				} else if (доза_15_Row < 1) {
					доза_15 = new int[1][2];
					доза_15[0][0] = x;
					доза_15[0][1] = y;
					доза_15_Row++;
				} else if (доза_20_Row < 1) {
					доза_20 = new int[1][2];
					доза_20[0][0] = x;
					доза_20[0][1] = y;
					доза_20_Row++;
				} else if (доза_25_Row < 1) {
					доза_25 = new int[1][2];
					доза_25[0][0] = x;
					доза_25[0][1] = y;
					доза_25_Row++;
				} else if (доза_30_Row < 1) {
					доза_30 = new int[1][2];
					доза_30[0][0] = x;
					доза_30[0][1] = y;
					доза_30_Row++;
				} else if (доза_50_Row < 1) {
					доза_50 = new int[1][2];
					доза_50[0][0] = x;
					доза_50[0][1] = y;
					доза_50_Row++;
				} else if (доза_100_Row < 1) {
					доза_100 = new int[1][2];
					доза_100[0][0] = x;
					доза_100[0][1] = y;
					доза_100_Row++;
				} else if (ДозатораПочатковаПозиція < 1) {
					ДозаторStartPosition = new int[1][2];
					ДозаторStartPosition[0][0] = x;
					ДозаторStartPosition[0][1] = y;
					ДозатораПочатковаПозиція++;
				} else if (ДозатораКінцеваПозиція < 1) {
					ДозаторEndPosition = new int[1][2];
					ДозаторEndPosition[0][0] = x;
					ДозаторEndPosition[0][1] = y;
					ДозатораКінцеваПозиція++;
				} else if (ChemMasterПочатковаПозиція < 1) {
					ChemMasterStartPosition = new int[1][2];
					ChemMasterStartPosition[0][0] = x;
					ChemMasterStartPosition[0][1] = y;
					ChemMasterПочатковаПозиція++;
				} else if (ChemMasterКінцеваПозиція < 1) {
					ChemMasterEndPosition = new int[1][2];
					ChemMasterEndPosition[0][0] = x;
					ChemMasterEndPosition[0][1] = y;
					ChemMasterКінцеваПозиція++;
				} else if (затримкаRow < 1) {
					затримка_duration = new int[1][2];
					затримка_duration[0][0] = x;
					затримка_duration[0][1] = y;
					затримкаRow++;
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
							System.out.println("Файл налаштувань змінений " + formattedTimestamp+"  Дебаг");
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
	public static void затримка() {
		int time = затримка_duration[0][0];
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void clickOnScreen(int x, int y) {
		затримка();
		bot.mouseMove(x, y);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ChemistryHelperUA());
	}
}