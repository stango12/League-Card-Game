package com.stango.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private Sprite endButton;
	
	//cards in hand positions
	private Vector2[] handPositions = new Vector2[5];
	
	//red side stuff
	private Champion[][] red = new Champion[3][5];
	private Card[][] redGrid = new Card[3][5];
	private ArrayList<Card> redDeck = new ArrayList<Card>();
	private ArrayList<Card> redHand = new ArrayList<Card>();
	private int redTower1, redTower2, redTower3;
	private int redNexus;

	
	//blue side stuff
	private Champion[][] blue = new Champion[3][5];
	private Card[][] blueGrid = new Card[3][5];
	private ArrayList<Card> blueDeck = new ArrayList<Card>();
	private Card[] blueHand = new Card[5];
	private int blueTower1, blueTower2, blueTower3;
	private int blueNexus;
	private int blueHandSize;
	
	//variables
	boolean observe = false;
	boolean redAction = false;
	boolean redMove = true;
	
	boolean redTurn = true;
	
	//initializing the game
	private void init()
	{	
		blueHandSize = 0;
		
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
		
		//putting hand positions into array
		for(int i = 0; i < handPositions.length; i++)
		{
			handPositions[i] = new Vector2(420/4 + (420 / 2) * i, 590/4);
		}
		
		//testing with one card
		redDeck.add(new Ashe());
		
		//adding card to hand
		redHand.add(new Ashe());
		redHand.get(0).getSprite().setSize(420 / 2, 590 / 2);
		redHand.get(0).setPosition(handPositions[0].x, handPositions[0].y);
		redHand.get(0).updateLastPosition();
	
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
		
		endButton = new Sprite(new Texture(Gdx.files.internal("endButton.png")));
		endButton.setSize(180 / 2, 177 / 2);
		endButton.setCenter(180 / 4 + 5, 500);
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
		endButton.draw(batch);
		
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
		for(int i = 0; i < redHand.size(); i++)
			redHand.get(i).render(batch);
		
		batch.end();
		
		skillCheck();
		deadCheck();
	}
	
	//end turn logic
	public void endTurn(int x, int y)
	{
		if(endButton.getBoundingRectangle().contains(x, y))
		{
			redTurn = !redTurn;
			System.out.println("Ended turn!");
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					if(red[i][j] != null)
						red[i][j].setMove(false);
					
					if(blue[i][j] != null)
						blue[i][j].setMove(false);
				}
			}
			drawRedCard();
		}
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

		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(red[i][j] != null && red[i][j].isDead())
				{
					System.out.println("Red side " + red[i][j].getName() + " has been defeated!");
					red[i][j] = null;
				}
				if(blue[i][j] != null && blue[i][j].isDead())
				{
					System.out.println("Blue side " + blue[i][j].getName() + " has been defeated!");
					blue[i][j] = null;
				}
			}
		}
		
	}
	
	//drawing a card for red
	public void drawRedCard()
	{
		if(!redDeck.isEmpty())
		{
			redHand.add(redDeck.get(0));
			redHand.get(redHand.size() - 1).getSprite().setSize(420 / 2, 590 / 2);
			redHand.get(redHand.size() - 1).setPosition(handPositions[redHand.size()].x, handPositions[redHand.size()].y);
			redHand.get(redHand.size() - 1).updateLastPosition();
			redDeck.remove(0);
		}
	}
	
	//checking if card is on any grid
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
		for(int i = 0; i < redHand.size(); i++)
		{
			if(redHand.get(i).getSprite().getBoundingRectangle().contains(x,1000 - y))
			{
				if(!observe)
					redHand.get(i).updateLastPosition();
				redHand.get(i).getSprite().setSize(420, 590);
				redHand.get(i).setPosition(800, 500);
				observe = true;
			}
			else
			{
				redHand.get(0).getSprite().setSize(420 / 2, 590 / 2);
				Vector2 temp = redHand.get(0).getLastPosition();
				redHand.get(0).setPosition(temp.x, temp.y);
				observe = false;
			}
		}
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++)
			{
				if(red[i][j] != null)
				{
					if(red[i][j].getSprite().getBoundingRectangle().contains(x,1000 - y))
					{		
						if(!observe)
							red[i][j].updateLastPosition();
						red[i][j].getSprite().setSize(420, 590);
						red[i][j].setPosition(800, 500);
						observe = true;
					}
					else
					{
						red[i][j].getSprite().setSize(420 / 4, 590 / 4);
						Vector2 temp = red[i][j].getLastPosition();
						red[i][j].setPosition(temp.x, temp.y);
						observe = false;
					}
				}
			}
	}
	
	//holding card to grid moves it to grid, left clicking champ then opponent = attacking
	public void leftClickCard(int x, int y)
	{		
		//attacking
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 5; j++)
				for(int k = 0; k < redHand.size(); k++)
					if(blue[i][j] != null && redHand.get(k).getAction() && blue[i][j].getSprite().getBoundingRectangle().contains(x,1000 - y))
					{
						//checks if the attack is valid by seeing if both champs are on the same column
						if(findCardOnGridBlue(blue[i][j]).y == findCardOnGridRed(redHand.get(0)).y && findCardOnGridBlue(blue[i][j]).y != -1)
						{
							System.out.println("Attack!");
							Champion temp = (Champion)redHand.get(0);
							blue[i][j].setHp(blue[i][j].getHp() - temp.getAtk());
							System.out.println("Ashe health: " + blue[i][j].getHp());
						}
					}
		
		//moving from hand to grid
		for(int i = 0; i < redHand.size(); i++)
		{
			if(redHand.get(i).getSprite().getBoundingRectangle().contains(x,1000 - y))
				redHand.get(i).setAction(true);
			else
				redHand.get(i).setAction(false);
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(redGrid[i][j].getSprite().getBoundingRectangle().contains(x,1000 - y) && red[i][j] != null)
					red[i][j].setAction(true);
				else if(red[i][j] != null)
					red[i][j].setAction(false);
			}
		}
	}
	
	//does the actual moving
	//To Do: fix movement/update when you move around the grid
	public void moveCard(int x, int y)
	{
		for(int k = 0; k < redHand.size(); k++)
		{
			if(redHand.get(k).getAction() && !redHand.get(k).getMove())
			{
				for(int i = 0; i < 3; i++)
				{
					for(int j = 0; j < 5; j++)
					{
						if(redGrid[i][j].getSprite().getBoundingRectangle().contains(x, 1000 - y))
						{
							//redGrid[i][j].setName(redHand.get(0).getName());
							Vector2 temp = redGrid[i][j].getPosition();
							redHand.get(k).getSprite().setSize(420/4, 590/4);
							redHand.get(k).setPosition(temp.x, temp.y);
							red[i][j] = (Champion) redHand.get(k);
							redHand.get(k).setLastPosition(temp.x, temp.y);
							redHand.get(k).setMove(true);
							return;
						}
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(redGrid[i][j].getSprite().getBoundingRectangle().contains(x, 1000 - y))
				{
					for(int a = 0; a < 3; a++)
					{
						for(int b = 0; b < 5; b++)
						{
							if(red[a][b] != null && !red[a][b].getMove())
							{
								Vector2 temp = redGrid[i][j].getPosition();
								red[a][b].getSprite().setSize(420/4, 590/4);
								red[a][a].setPosition(temp.x, temp.y);
								red[a][b].setLastPosition(temp.x, temp.y);
								red[a][b].setMove(true);
								red[i][j] = red[a][b];
								red[a][b] = null;
								return;
							}
						}
					}
				}
			}
		}
	}
	
}
