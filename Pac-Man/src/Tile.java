import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends Rectangle implements IHelper{
	public Tile(int x,int y) {
		setBounds(x,y,25,25);
	}

	

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(52, 116, 128));
		g.fillRect(x, y, width, height);
	}
}
