import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle implements IHelper{
	
	private static final long serialVersionUID = 1L;
	public int speed = 4;
	private int time=0,targetTime = 30;
	public int imageIndex = 0;
	private int lastDir = 1;	
	public int apples = 0;
	
	public Player(int x,int y)
	{
		setBounds(x,y,32,32);
	}
    public boolean right,left,up,down;
    
	public void ForAnimation() {
		time++;
		if(time == targetTime) {
			time =0;
			imageIndex++;
	}
	}
	private boolean canMove(int nextx,int nexty) {
		
		Rectangle bounds = new Rectangle(nextx,nexty,width,height);
		Level level = Game.level;
		
		for(int xx=0;xx<level.tiles.length;++xx) {
			for(int yy=0;yy < level.tiles[0].length;++yy) {
				if(level.tiles[xx][yy] != null) {
					if(bounds.intersects(level.tiles[xx][yy])) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(right && canMove(x+speed,y)) {
			x+=speed;
			lastDir=1;
		}
		if(left && canMove(x-speed,y)) 
		{
			x-=speed;
		    lastDir=-1;
		}
		if(up && canMove(x,y-speed))
		{
			y-=speed;
			lastDir = 2;
		}
		if(down && canMove(x,y+speed))
	        {
			y+=speed;
		    lastDir = -2;
	        }
		
		Level level = Game.level;
		for(int i=0;i<level.apples.size();++i) {
			if(this.intersects(level.apples.get(i))) {
				level.apples.remove(i);
				break;
			}
		}
		if(level.apples.size() == 0) {
			//Game end,we win!
			Game.STATE = Game.WIN;
			return;
		}
		for(int i = 0;i< Game.level.enemies.size();++i)
		{
			if(this.intersects(Game.level.enemies.get(i))) {
				//Menu system
				//DevelopersLogo logo = new DevelopersLogo();
				Game.STATE = Game.PAUSE_SCREEN;
				
				apples=0;
			}
			ForAnimation();
			}
	}
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(lastDir == 1)
		  {
		  g.drawImage(Texture.player[imageIndex%2],x,y,width,height,null);
		  }
	  else if(lastDir == -1){
		  g.drawImage(Texture.player[imageIndex%2],x+32,y,-width,height,null);
		  }
	  else if(lastDir == 2) {
		  g.drawImage(Texture.playerUP[imageIndex%2],x,y,width,height,null);
	  }
	  else {
		  g.drawImage(Texture.playerDOWN[imageIndex % 2], x, y, width, height, null);
	}
	}
}
