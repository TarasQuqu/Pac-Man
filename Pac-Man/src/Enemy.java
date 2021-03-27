import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Rectangle implements IHelper{
	
private static final long serialVersionUID = 1L;

private int random =0,smart =1,find_path = 2;
private int state = smart;
private int right =0,left=1,up=2,down=3;
private int dir = -1;
private int timeForAnim =0;
public Random randomGen;
Player player;
private int time = 0;
private int targetTime = 250;
private int targetTimeForAnim = 200;
private int spd = 4;

private int lastDir = -1;

public int imageIndex = 0;

public Enemy(int x,int y) {
	randomGen = new Random();
	setBounds(x,y,32,32);
	dir = randomGen.nextInt(4);
}
 
public void ForAnimation() {
	timeForAnim++;
	if(timeForAnim == targetTimeForAnim) {
		timeForAnim =0;
		imageIndex++;
		}
}
private boolean canMove(int nextx,int nexty) {
	ForAnimation();
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
	}	return true;
}


@Override
public void tick() {
	// TODO Auto-generated method stub
	if(state == random) {
		if(dir == right) {
			
			if(canMove(x+spd,y)) {
				if(randomGen.nextInt(100) < 50) x+=spd; 
			}else {
				dir = randomGen.nextInt(4);
			}
		
		}else if(dir == left) {
			if(canMove(x-spd,y)) {
				if(randomGen.nextInt(100) < 50) x-=spd;
			}else {
				dir = randomGen.nextInt(4);
			}
			
			
		}else if(dir == up) {
			if(canMove(x,y-spd)) {
				if(randomGen.nextInt(100) < 50) y-=spd;
			}else {
				dir = randomGen.nextInt(4);
			}
		}else if(dir == down)
		{
			if(canMove(x,y+spd)) {
				if(randomGen.nextInt(100) < 50) y+=spd;
			}else {
				dir = randomGen.nextInt(4);
			}
		}
	time++;
	if(time == targetTime) state = smart;
	
	}else if(state == smart) {
		boolean move = false;
		
		if(x < Game.player.x) {
			if(canMove(x+spd,y)) {
				if(randomGen.nextInt(100) < 50) x+=spd;
				move = true;
				lastDir = right;
			}
		}
		if(x > Game.player.x) {
			if(canMove(x-spd,y)) {
				if(randomGen.nextInt(100) < 50) x-=spd;
				move = true;
				lastDir = left;
			}
		}
		if(y < Game.player.y) {
			if(canMove(x,y+spd)) {
				if(randomGen.nextInt(100) < 50) y+=spd;
				move = true;
				lastDir = down;
			}
		}
		if(y > Game.player.y) {
			if(canMove(x,y-spd)) {
				if(randomGen.nextInt(100) < 50) y-=spd;
				move = true;
				lastDir = up;
			}
		}
		
		if(x == Game.player.x && y == Game.player.y) move = true;
		
		if(!move) {
			state = find_path;
		
		}
		 time++;
		 if(time==targetTime) {
	    	state = random;
			time = 0;
		   }	
		
		//Follow the player
		
	}else if(state == find_path) {
		if(lastDir == right) {

			if(y<Game.player.y) {
			if(canMove(x,y+spd)) {
				if(randomGen.nextInt(100) < 50) y+=spd;
			state = smart;
			}
			}
			else {
			if(canMove(x,y-spd)) {
				if(randomGen.nextInt(100) < 50) y-=spd;
			state = smart;
			}
			}
			if(canMove(x+spd,y)) {
				if(randomGen.nextInt(100) < 50) x+=spd;
			}
			}else if(lastDir == left) {
			if(y<Game.player.y) {
			if(canMove(x,y+spd)) {
				if(randomGen.nextInt(100) < 50) y+=spd;
			state = smart;
			}
			}
			else {
			if(canMove(x,y-spd)) {
				if(randomGen.nextInt(100) < 50) y-=spd;
			state = smart;
			}
			}
			if(canMove(x-spd,y)) {
				if(randomGen.nextInt(100) < 50)x-=spd;
			}
			}else if(lastDir == up) {
			if(x<Game.player.y) {
			if(canMove(x+spd,y)) {
				if(randomGen.nextInt(100) < 50) x+=spd;
			state = smart;
			}
			}
			else {
			if(canMove(x-spd,y)) {
				if(randomGen.nextInt(100) < 50) x-=spd;
			state = smart;
			}
			}
			if(canMove(x,y-spd)) {
				if(randomGen.nextInt(100) < 50) y-=spd;
			}
			}else if(lastDir == down) {
			if(x<Game.player.y) {
			if(canMove(x+spd,y)) {
				if(randomGen.nextInt(100) < 50) x+=spd;
			state = smart;
			}
			}
			else {
			if(canMove(x-spd,y)) {
				if(randomGen.nextInt(100) < 50) x-=spd;
			state = smart;
			}
			}
			if(canMove(x,y+spd)) {
				if(randomGen.nextInt(100) < 50) y+=spd;
			}
			}
		time++;
		 if(time==targetTime) {
	    	state = random;
			time = 0;
		   }
		
	}
	
}

@Override
public void render(Graphics g) {
	// TODO Auto-generated method stub
	g.drawImage(Texture.ghost[imageIndex%2],x,y,width,height,null);
}
}
