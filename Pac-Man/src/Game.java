import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.event.*;

import javax.swing.*;



public class Game extends Canvas implements Runnable,KeyListener,IHelper{
	public static JFrame frame = new JFrame();
	public static JPanel jp = new JPanel();
	public static JFrame login = new JFrame();
	public static JFrame registration = new JFrame();
	public static final int WIDTH = 640,HEIGHT = 480;
	public static final String TITLE = "Pac-Man";
	public static final String TITLE1 = "Colors of PAC-MAN";
	private boolean isRunning = false;
	private Thread thread;
	// Модель списка
    private final String[] items = { "Blue" ,"White"  ,"Yellow"};
    private final JList<String> list = new JList<String>(items);
    private final JFrame fram = new JFrame();
	
    
    private IHelper helper;
    
	private  Button[] options;//selection 0,1,2
	private int currentSelection;
	private static  AudioPlayer Audioplayer;	
	
	public static Player player;
	public static Level level;
	public static spriteSheet spritesheet;
	
	public static final int PAUSE_SCREEN = 0,GAME = 1,END = 2,WIN =3;
	public static int STATE = -1;
	
	public boolean isEnter = false;
	
	private int time = 0;
	private int targetFrames = 15;
	private boolean showText = true;
	private ImageIcon background = new ImageIcon("C:/Users/grene/eclipse-workspace/Pac-Man/res/Images/background.jpg");
	
	private static Container containerLogIn; 
    private static JLabel titleLogIn; 
    private static JLabel nameLogIn; 
    private static JLabel passwordLogIn;
    private static JTextField tnameLogIn;   
    private static JButton LogIN; 
    private static JButton registr;  
    public static String UserNameLogIn;
    public static String PasswordLogIn[];
    private static Users UsersLogLogIn;
	
    private static Container containerRegister; 
    private static JLabel titleRegister; 
    private static JLabel nameRegister; 
    private static JLabel passwordRegister;
    private static JTextField tpasswordRegister;
    private static JTextField tnameRegister;
    private static JPasswordField pass;
    private static JButton RegistationBTN;   
    public static String UserNameRegister;
    public static String PasswordRegister;
    public static String email;
    
	public static void main(String[] args)
	{
		Login();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(STATE==GAME) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
	     }else if(STATE == Game.PAUSE_SCREEN) {
	    	 if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	    		 isEnter = true;
	    	 }
	    	 if(e.getKeyCode() == KeyEvent.VK_Q) {
	    		 System.exit(1);
	    	 }
	     }
	     
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
		if(e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public Game()
	{
		Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		
		addKeyListener(this);
		MouseInput mi = new MouseInput();
		addMouseListener(mi);
		addMouseMotionListener(mi);
   	    STATE = PAUSE_SCREEN;
   	    
   	 
	    Thread thread = new Thread(()  ->Audioplayer.play("C:\\Users\\grene\\eclipse-workspace\\Pac-Man\\res\\audio\\CantinaBand60.wav"));//tworzymy watek
        thread.start();
	    ButtonsForMenu();
	    
	    
		
		
	}
   	public void ButtonsForMenu() {
		options = new Button[5];
		options[0] = new Button("Easy level",200 + 0*60,
				new Font("Arial",Font.PLAIN,32),new Font("Arial",Font.BOLD,42),
				Color.WHITE,Color.YELLOW);
		options[1] = new Button("Medium level",200 + 1*60,
				new Font("Arial",Font.PLAIN,32),new Font("Arial",Font.BOLD,42),
				Color.WHITE,Color.YELLOW);
		options[2] = new Button("Hard level",200 + 2*60,
				new Font("Arial",Font.PLAIN,32),new Font("Arial",Font.BOLD,42),
				Color.WHITE,Color.YELLOW);
		options[3] = new Button("Very Hard level",200 + 3*60,
				new Font("Arial",Font.PLAIN,32),new Font("Arial",Font.BOLD,42),
				Color.WHITE,Color.YELLOW);
		options[4] = new Button("Exit",200 + 4*60,
				new Font("Arial",Font.PLAIN,32),new Font("Arial",Font.BOLD,42),
				Color.WHITE,Color.YELLOW);
	}
	public synchronized void Stop()
	{
		if(!isRunning) return;
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
	}
	public synchronized void Start()
	{
		if(isRunning) return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void SkinLVL(String map,String skins) {
		isEnter = false;
		player = new Player(Game.WIDTH/2,Game.HEIGHT/2);
		level = new Level(map);
		spritesheet = new spriteSheet(skins);
		new Texture();
		STATE = GAME;
	}
	public void Blinking() {
		time++;
		if(time == targetFrames) {
			time =0;
			if(showText) {
				showText = false;
			}else {
				showText = true;
			}
	}
	}
	private void rendering()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
	    Graphics g = bs.getDrawGraphics();
		g.drawImage(background.getImage(),0,0,null);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		if(STATE == GAME){
		player.render(g);
		level.render(g);
		}else if(STATE == PAUSE_SCREEN) {
			int boxWidth = 640;
			int boxHeight = 480;
			int xx = Game.WIDTH / 2;
			int yy = Game.HEIGHT /2;
			g.fillRect(xx, yy, boxWidth, boxHeight);
	
			
			render(g);
			g.setFont(new Font(Font.DIALOG,Font.BOLD,18));
			if(showText)g.drawString("Autor:Taras Kuts", xx+150, yy+240);
		}
		g.dispose();
		bs.show();
	}
	@Override
	public void run() {
		requestFocus();
		int fps = 0;
		double timer = System.currentTimeMillis();
		long LastTime = System.nanoTime();
		double targetTick = 60.0;
		double delta = 0;
		double ns = 1000000000/targetTick;
		while(isRunning)
		{
			long now = System.nanoTime();
			delta+=(now - LastTime)/ns;
			LastTime = now;
			
			
			while(delta >= 1)
			{
				ticki();
				tick();
				MouseInput.update();
				rendering();
				fps++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println(fps);
				fps = 0;
				timer+=1000;
			}
		}
		Stop();
		
	}
	public void POM(String case1,String case2,String case3,String caseMap) {
		fram.setPreferredSize(new Dimension(200
				, 200));
	    fram.add(list, BorderLayout.CENTER);
	    fram.setResizable(false);
		fram.pack();
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fram.setLocationRelativeTo(null);
		fram.setTitle(Game.TITLE1);
		fram.add(new JLabel("Choose color of Pac-Man"), BorderLayout.EAST);
	    JButton btn = new JButton("Choose");
	    fram.add(btn, BorderLayout.SOUTH);
	    btn.setPreferredSize(new Dimension(30, 30));
	    btn .setFont(new Font("Arial", Font.PLAIN, 40));
	    btn.setBackground(Color.GREEN);
	    btn.addActionListener(new ActionListener() {
   
	    	
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JOptionPane.showConfirmDialog(fram, "You Selected : " + list.getSelectedValue(), "Display",
	                    JOptionPane.PLAIN_MESSAGE);
	            System.out.println("i" + currentSelection);
	            if(list.getSelectedIndex() == 0 ) {
	            	System.out.println("i" + currentSelection);
	            	fram.setVisible(false);
	            	if(currentSelection == 0) {
	            		SkinLVL("/map/map1.png","s/s2blue1.png");
	            	}
	            	if(currentSelection == 1) {
	            		SkinLVL("/map/map2.png","s/s2blue1.png");
	            	}
	            	if(currentSelection == 2) {
	            		SkinLVL("/map/map3.png","s/s2blue1.png");
	            	}
	            	if(currentSelection == 3) {
	            		SkinLVL("/map/map4.png","s/s2blue1.png");
	            	}
	            	//SkinLVL(caseMap,case1);
	        		STATE = GAME;  	
	            }
	            else if(list.getSelectedIndex()==1) {
	            	System.out.println("i" + currentSelection);
	            	if(currentSelection == 0) {
	            		SkinLVL("/map/map1.png","s/s2white1.png");
	            	}
	            	if(currentSelection == 1) {
	            		SkinLVL("/map/map2.png","s/s2white1.png");
	            	}
	            	if(currentSelection == 2) {
	            		SkinLVL("/map/map3.png","s/s2white1.png");
	            	}
	            	if(currentSelection == 3) {
	            		SkinLVL("/map/map4.png","s/s2white1.png");
	            	}
	            	fram.setVisible(false);
	            	//SkinLVL(caseMap,case2);
	        		STATE = GAME;
	            }
	            else if(list.getSelectedIndex()==2){
	            	System.out.println("i" + currentSelection);
	            	if(currentSelection == 0) {
	            		SkinLVL("/map/map1.png","s/s2.png");
	            	}
	            	if(currentSelection == 1) {
	            		SkinLVL("/map/map2.png","s/s2.png");
	            	}
	            	if(currentSelection == 2) {
	            		SkinLVL("/map/map3.png","s/s2.png");
	            	}
	            	if(currentSelection == 3) {
	            		SkinLVL("/map/map4.png","s/s2.png");
	            	}
	            	fram.setVisible(false);
	            	//SkinLVL(caseMap,case3);
	        		STATE = GAME;
	            }
	            else {
	            	JOptionPane.showConfirmDialog(fram, "Selected one color : " ,"Display",
		                    JOptionPane.PLAIN_MESSAGE);
	            }

	        }
	    });
	    fram.setVisible(true);
	}
	public static void Login() {
		login.setPreferredSize(new Dimension(480
				, 400));
	    login.setResizable(false);
		login.pack();
		login.setLocationRelativeTo(null);
        login.setTitle("Login Form"); 
       
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        login.setResizable(false); 
  
        containerLogIn = login.getContentPane(); 
        containerLogIn.setLayout(null); 
  
        titleLogIn = new JLabel("Login"); 
        titleLogIn.setFont(new Font("Arial", Font.PLAIN, 56)); 
        titleLogIn.setSize(200, 200); 
        titleLogIn.setLocation(175, -75); 
        containerLogIn.add(titleLogIn); 
  
        nameLogIn = new JLabel("Username"); 
        nameLogIn.setFont(new Font("Arial", Font.PLAIN, 24)); 
        nameLogIn.setSize(120, 50); 
        nameLogIn.setLocation(10, 105); 
        containerLogIn.add(nameLogIn); 
  
        tnameLogIn = new JTextField(); 
        tnameLogIn.setFont(new Font("Arial", Font.PLAIN, 24)); 
        tnameLogIn.setSize(275, 30); 
        tnameLogIn.setLocation(140, 115); 
        containerLogIn.add(tnameLogIn); 
        
        passwordLogIn = new JLabel("Passsword"); 
        passwordLogIn.setFont(new Font("Arial", Font.PLAIN, 24)); 
        passwordLogIn.setSize(275, 50); 
        passwordLogIn.setLocation(10, 150); 
        containerLogIn.add(passwordLogIn); 
  
        pass = new JPasswordField();
        pass.setFont(new Font("Arial", Font.PLAIN, 24)); 
        pass.setSize(275, 30); 
        pass.setLocation(140, 160); 
        containerLogIn.add(pass);
        
         
        LogIN = new JButton("Login"); 
        LogIN.setBackground(Color.yellow);
        LogIN.setFont(new Font("Arial", Font.PLAIN, 48)); 
        LogIN.setSize(200, 100); 
        LogIN.setLocation(10, 250); 
        containerLogIn.add(LogIN); 
  
        registr = new JButton("Registr"); 
        registr.setBackground(Color.GREEN);
        registr.setFont(new Font("Arial", Font.PLAIN, 48)); 
        registr.setSize(200, 100); 
        registr.setLocation(270, 250);  
        containerLogIn.add(registr); 
        login.setVisible(true); 
        LogIN.addActionListener(new ActionListener() {
	    	
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	UserNameLogIn = tnameLogIn.getText();
	        	String myPass=String.valueOf(pass.getPassword());
	            UsersDao empDao = new UsersDao();
	            login.setVisible(false);
	            UsersLogLogIn = empDao.get(UserNameLogIn, myPass);
	            empDao.ConnectionClose();
	            if(UsersLogLogIn != null)
	            {
	            	Game game = new Game();
	        		frame.setTitle(Game.TITLE);
	        		frame.add(game);
	        		frame.setResizable(false);
	        		frame.pack();
	        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        		frame.setLocationRelativeTo(null);
	        		frame.setVisible(true);
	        		game.Start();
	            }
	            else{
	                login.setVisible(true);
	            }
	        	
	        }
});
 registr.addActionListener(new ActionListener() {
	   
 	
     @Override
     public void actionPerformed(ActionEvent e) {
     	   Registr();
     	   login.setVisible(false);
     }
});
}
	public static void Registr() { 
		registration.setPreferredSize(new Dimension(480
				, 400));
		registration.setResizable(false);
	    registration.pack();
		registration.setLocationRelativeTo(null);
		registration.setTitle("Registration Form"); 
       
        registration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        registration.setResizable(false); 
  
        containerRegister = registration.getContentPane(); 
        containerRegister.setLayout(null); 
  
        titleRegister = new JLabel("Registration"); 
        titleRegister.setFont(new Font("Arial", Font.PLAIN, 42)); 
        titleRegister.setSize(300, 550); 
        titleRegister.setLocation(130, -250); 
        containerRegister.add(titleRegister); 
  
        nameRegister = new JLabel("Username"); 
        nameRegister.setFont(new Font("Arial", Font.PLAIN, 24)); 
        nameRegister.setSize(120, 50); 
        nameRegister.setLocation(10, 105); 
        containerRegister.add(nameRegister); 
  
        tnameRegister = new JTextField(); 
        tnameRegister.setFont(new Font("Arial", Font.PLAIN, 24)); 
        tnameRegister.setSize(275, 30); 
        tnameRegister.setLocation(140, 115); 
        containerRegister.add(tnameRegister); 
        
        passwordRegister = new JLabel("Passsword"); 
        passwordRegister.setFont(new Font("Arial", Font.PLAIN, 24)); 
        passwordRegister.setSize(275, 50); 
        passwordRegister.setLocation(10, 150); 
        containerRegister.add(passwordRegister); 
  
        tpasswordRegister = new JTextField(); 
        tpasswordRegister.setFont(new Font("Arial", Font.PLAIN, 24)); 
        tpasswordRegister.setSize(275, 30); 
        tpasswordRegister.setLocation(140, 160); 
        containerRegister.add(tpasswordRegister);
        
        RegistationBTN = new JButton("Registration");
        RegistationBTN.setBackground(Color.green);
        RegistationBTN.setFont(new Font("Arial", Font.PLAIN, 48)); 
        RegistationBTN.setSize(460, 100); 
        RegistationBTN.setLocation(5, 250); 
        containerRegister.add(RegistationBTN);  
  
        registration.setVisible(true); 
        RegistationBTN.addActionListener(new ActionListener() {
   
	    	
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            UserNameRegister = tnameRegister.getText();
	            PasswordRegister = tpasswordRegister.getText();
	            if(tnameRegister.getText().isEmpty() || tpasswordRegister.getText().isEmpty()) 
	            	{
	            	JOptionPane.showMessageDialog(null, "username or password is empty");    
	            	}
	            else if(UserNameRegister.length() <6 || PasswordRegister.length() < 6) {
	            	JOptionPane.showMessageDialog(null, "username and password must have minimum 6 chars");
	            }
	            else {
	            pass.setText("");
	            tnameLogIn.setText("");
	            Users user = new Users(UserNameRegister,PasswordRegister);
	            UsersDao dao = new UsersDao();
	            dao.save(user);
	            registration.setVisible(false);
	            Login();
	            }
	        }
});
}	
	
	

	public void ticki() {
		currentSelection = 0;
	    boolean clicked = false;
		for(int i=0;i<options.length;++i) {
		if(options[i].intersects(new Rectangle(MouseInput.getX(),MouseInput.getY(),1,1))){
		currentSelection =i;
		clicked = MouseInput.wasPressed(MouseEvent.BUTTON1);
		    }
		}
		if(clicked ) 
			{
			 select();
			}
}
private void select() {
	switch(currentSelection) {
	case 0:
		POM("/s/s2blue1.png","/s/s2white1.png","/s/s2.png","/map/map1.png");
		//System.out.println("0");
		break;
	case 1:
		POM("/s/s2blue1.png","/s/s2white1.png","/s/s2.png","/map/map2.png");
		//System.out.println("1");
		break;
	case 2:
		POM("/s/s2blue1.png","/s/s2white1.png","/s/s2.png","/map/map3.png");
		//System.out.println("2");
		break;
	case 3:
		POM("/s/s2blue1.png","/s/s2white1.png","/s/s2.png","/map/map4.png");
		//System.out.println("3");
		break;
	case 4:
	    System.exit(1);
		break;
	}
}


@Override
public void tick() {
	// TODO Auto-generated method stub
	if(STATE == GAME) {
		ButtonsForMenu();
		player.tick();
		level.tick();
		}else if(STATE == PAUSE_SCREEN) {
	        Blinking();		
			
			}
				if(STATE == WIN) {
					int op = JOptionPane.showConfirmDialog(this, "You are passed this level!Do you want comeback to main menu?.", "Congratulation!", JOptionPane.OK_OPTION);
			        if (op == JOptionPane.YES_OPTION) {
			            STATE = PAUSE_SCREEN;
			        }else {System.exit(1);}
				}
}
@Override
public void render(Graphics g) {
	// TODO Auto-generated method stub
	g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
	Fonts.drawString(g,new Font("Arial",Font.BOLD,72),Color.ORANGE,Game.TITLE,80);
	for(int i=0;i<options.length;++i) {
		if(i== currentSelection) 
			options[i].setSelected(true);
			else options[i].setSelected(false);
		
		options[i].render(g);
}

}
}
