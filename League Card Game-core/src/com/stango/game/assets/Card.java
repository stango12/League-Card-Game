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
	
	public Card(String n, int m, Type t)
	{
		type = t;
		mana = m;
		name = n;
		position = new Vector2();
		card = new Sprite(new Texture(Gdx.files.internal(name + ".png")));
	}

	public void setPosition(float x, float y)
	{
		card.setCenter(x, y);
		position.x = x;
		position.y = y;
	}
	
	public void setName(String s)
	{
		name = s;
		card = new Sprite(new Texture(Gdx.files.internal(name + ".png")));
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
