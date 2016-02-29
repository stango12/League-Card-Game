package com.stango.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.stango.game.assets.Card;
import com.stango.game.assets.Champion;
import com.stango.game.assets.MyInputProcessor;
import com.stango.game.cards.*;

public class CardGame extends ApplicationAdapter {
	SpriteBatch batch;
	private OrthographicCamera camera;
	Texture background;
	
	//red side stuff
	private Champion[][] red = new Champion[3][5];
	private Card[][] redGrid = new Card[3][5];
	private Card[] redDeck;
	private int redTower1, redTower2, redTower3;
	private int redNexus;
	
	//blue side stuff
	private Champion[][] blue = new Champion[3][5];
	private Card[][] blueGrid = new Card[3][5];
	private Card[] blueDeck;
	private int blueTower1, blueTower2, blueTower3;
	private int blueNexus;
	
	//variables
	private Vector2 lastRed;
	boolean observe = false;
	boolean redAction = false;
	boolean redMove = true;
	int temp = 0;
	
	//initializing the game
	private void init()
	{
		//empty grid
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				redGrid[i][j] = new Card("empty", 0, null);
				blueGrid[i][j] = new Card("empty", 0, null);
				
				redGrid[i][j].setPosition(1000 + 105 * j + 5, 400 - 148 * i - 5);
				blueGrid[i][j].setPosition(1000 + 105 * j + 5, 600 + 148 * i + 5);
			}
		}
		
		//testing with one card
		redDeck  = new Card[]{new Ashe()};
		redDeck[0].getSprite().setSize(420 / 2, 590 / 2);
		redDeck[0].setPosition(420/4, 590/4);
		lastRed = new Vector2(420/4, 590/4);
		
		//background image
		background = new Texture("SRBackground.png");
		
		//adding a card to the blue field
		blue[0][2] = new Ashe();
		blue[0][2].getSprite().rotate90(true);
		blue[0][2].getSprite().rotate90(true);
		blue[0][2].getSprite().setSize(420 / 4, 590 / 4);
		blue[0][2].setPosition(blueGrid[0][2].getPosition().x, blueGrid[0][2].getPosition().y);


		//red tower stuff
		redTower1 = 30;
		redTower2 = 30;
		redTower3 = 30;
		redNexus = 30;
		
		//blue tower stuff
		blueTower1 = 30;
		blueTower2 = 30;
		blueTower3 = 30;
		blueNexus = 30;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 1000);
		MyInputProcessor inputProcessor = new MyInputProcessor(this);
		Gdx.input.setInputProcessor(inputProcessor);
		init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, 0, 0);
		//drawing the grid
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				redGrid[i][j].render(batch);
				blueGrid[i][j].render(batch);
			}
		}
		//drawing the champ cards on the field
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(red[i][j] != null)
					red[i][j].render(batch);
				if(blue[i][j] != null)
					blue[i][j].render(batch);
			}
		}
		//displaying red's hand
		for(int i = 0; i < redDeck.length; i++)
			redDeck[i].render(batch);
		batch.end();
		
		skillCheck();
		deadCheck();
	}
	
	//checking if player selected a skill
	public void skillCheck()
	{
		if(observe)
		{
			if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
				System.out.println("Pressed Skill 1!");
			else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
				System.out.println("Pressed Skill 2!");
		}
	}
	
	//checks if any champs are dead on the field
	public void deadCheck()
	{

		if(temp == 0)
		{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(red[i][j] != null && red[i][j].isDead())
				{
					System.out.println("Red side " + red[i][j].getName() + " has been defeated!");
					temp++;
				}
				if(blue[i][j] != null && blue[i][j].isDead())
				{
					System.out.println("Blue side " + blue[i][j].getName() + " has been defeated!");
					temp++;
				}
			}
		}
		}
		
	}
	
	public boolean onGrid(Card c)
	{
		return findCardOnGridRed(c).y != -1 || findCardOnGridBlue(c).y != -1;
	}
	
	//returns in (x,y) where a card is on the red grid
	public Vector2 findCardOnGridRed(Card c)
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++)
			{
				if(red[i][j] != null && red[i][j].getName() == c.getName())
					return new Vector2(i, j);
			}
		
		return new Vector2(-1, -1);
	}
	
	//returns in (x,y) where a card is on the blue grid
	public Vector2 findCardOnGridBlue(Card c)
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++)
			{
				if(blue[i][j] != null && blue[i][j].getName() == c.getName())
					return new Vector2(i, j);
			}
		
		return new Vector2(-1, -1);
	}
	
	//right clicking a card zooms in
	public void rightClickCard(int x, int y)
	{
		if(redDeck[0].getSprite().getBoundingRectangle().contains(x,1000 - y))
		{
			if(!observe)
				lastRed = new Vector2(redDeck[0].getPosition().x, redDeck[0].getPosition().y);
			redDeck[0].getSprite().setSize(420, 590);
			redDeck[0].setPosition(800, 500);
			observe = true;
		}
		else if(redMove)
		{		
			redDeck[0].getSprite().setSize(420 / 2, 590 / 2);
			redDeck[0].setPosition(lastRed.x, lastRed.y);
			observe = false;
		}
		else
		{
			redDeck[0].getSprite().setSize(420 / 4, 590 / 4);
			redDeck[0].setPosition(lastRed.x, lastRed.y);
			observe = false;
		}
	}
	
	//holding card to grid moves it to grid, left clicking champ then opponent = attacking
	public void leftClickCard(int x, int y)
	{		
		//attacking
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++)
				if(blue[i][j] != null && redAction && blue[i][j].getSprite().getBoundingRectangle().contains(x,1000 - y))
				{
					//checks if the attack is valid by seeing if both champs are on the same column
					if(findCardOnGridBlue(blue[i][j]).y == findCardOnGridRed(redDeck[0]).y && findCardOnGridBlue(blue[i][j]).y != -1)
					{
						System.out.println("Attack!");
						Champion temp = (Champion)redDeck[0];
						blue[i][j].setHp(blue[i][j].getHp() - temp.getAtk());
						System.out.println("Ashe health: " + blue[i][j].getHp());
					}
				}
		
		//moving from hand to grid
		if(redDeck[0].getSprite().getBoundingRectangle().contains(x,1000 - y))
			redAction = true;
		else
			redAction = false;
	}
	
	//does the actual moving
	//To Do: fix movement/update when you move around the grid
	public void moveCard(int x, int y)
	{
		if(redAction && redMove)
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(redGrid[i][j].getSprite().getBoundingRectangle().contains(x, 1000 - y))
				{
					//redGrid[i][j].setName(redDeck[0].getName());
					Vector2 temp = redGrid[i][j].getPosition();
					redDeck[0].getSprite().setSize(420/4, 590/4);
					redDeck[0].setPosition(temp.x, temp.y);
					red[i][j] = (Champion) redDeck[0];
					lastRed = new Vector2(temp.x, temp.y);
					redMove = false;
				}
			}
		}
	}
}
