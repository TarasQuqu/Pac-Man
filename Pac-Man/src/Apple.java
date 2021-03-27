import java.awt.Graphics;
import java.awt.Rectangle;

public class Apple extends Rectangle{
public Apple(int x,int y) {
	setBounds(x+10,y+8,12,12);
}

public void render(Graphics g) {
	g.drawImage(Texture.apple,x,y,width,height,null);
	
	
}

}
