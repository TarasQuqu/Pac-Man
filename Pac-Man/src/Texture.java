import java.awt.image.BufferedImage;

public class Texture {

	public static BufferedImage[]  player;
	public static BufferedImage[] ghost;
	public static BufferedImage apple;
	public static BufferedImage[] playerDOWN;
	public static BufferedImage[] playerUP;

	public Texture() {
		player = new BufferedImage[2];
		playerDOWN = new BufferedImage[2];
		playerUP = new BufferedImage[2];
		ghost = new BufferedImage[2];
		
		apple = Game.spritesheet.getSprite(0, 32);
		playerUP[0] = Game.spritesheet.getSprite(64, 0);
		playerUP[1] = Game.spritesheet.getSprite(80, 0);
		playerDOWN[0] = Game.spritesheet.getSprite(48,0);
		playerDOWN[1] = Game.spritesheet.getSprite(32,0);
		player[0] = Game.spritesheet.getSprite(0,0);
		player[1]= Game.spritesheet.getSprite(16,0);
		ghost[0] = Game.spritesheet.getSprite(0, 16);
		ghost[1] = Game.spritesheet.getSprite(16,16);
	}
	
}
