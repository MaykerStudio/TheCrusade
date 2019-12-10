package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Arrow extends MapObject{

	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;

	public Arrow(TileMap tm, boolean right, boolean left) {
		super(tm);
		
		facingRight = right;

		moveSpeed = 4.5;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;

		width = 32;
		height = 32;
		cwidth = 18;
		cheight = 18;

		// load sprites

		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/TileSetsAndSprites/ArrowSprite.png"));
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}

			hitSprites = new BufferedImage[4];
			for (int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i*width, height, width, height);
			}

			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(100);



		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setHit(){
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;	
	}

	public boolean shouldRemove(){return remove;}

	public void update(){
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if(dx == 0 && !hit){
			setHit();
		}

		animation.update();
		if(hit && animation.hasPLayedOnce()){
			remove = true;
		}
	}

	public void draw(Graphics2D g){
		setMapPosition();

		super.draw(g);

	}

}

