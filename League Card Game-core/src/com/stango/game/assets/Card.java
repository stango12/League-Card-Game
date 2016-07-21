package com.stango.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Card 
{
	private int mana;
	private Type type;
	private String name;
	private Sprite card;
	private Vector2 position;
	private Vector2 lastPosition;
	private boolean action;
	private boolean move;
	
	public Card(String n, int m, Type t)
	{
		type = t;
		mana = m;
		name = n;
		action = false;
		move = false;
		position = new Vector2();
		lastPosition = new Vector2();
		card = new Sprite(new Texture(Gdx.files.internal(name + ".png")));
	}

	public void setPosition(float x, float y)
	{
		card.setCenter(x, y);
		position.x = x;
		position.y = y;
	}
	
	public void setLastPosition(float x, float y)
	{
		lastPosition.x = x;
		lastPosition.y = y;
	}
	
	public void updateLastPosition()
	{
		lastPosition.x = position.x;
		lastPosition.y = position.y;
	}
	
	public void setName(String s)
	{
		name = s;
		card = new Sprite(new Texture(Gdx.files.internal(name + ".png")));
	}
	
	public void setAction(boolean b)
	{
		action = b;
	}
	
	public void setMove(boolean b)
	{
		move = b;
	}
	
	public void makeEmpty()
	{
		name = "empty";
	}
	
	public int getMana() {
		return mana;
	}
	
	public Type getType() {
		return type;
	}

	public String getName()
	{
		return name;
	}
	
	public Sprite getSprite()
	{
		return card;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public Vector2 getLastPosition()
	{
		return lastPosition;
	}
	
	public boolean getAction()
	{
		return action;
	}
	
	public boolean getMove()
	{
		return move;
	}
	
	public void render(SpriteBatch batch)
	{
		card.draw(batch);
	}
	
	public enum Type
	{
		ATTACK,
		HEAL,
		ITEM,
		CONSUME
	}
}
