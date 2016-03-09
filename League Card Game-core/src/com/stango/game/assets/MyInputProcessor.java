package com.stango.game.assets;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.stango.game.CardGame;

public class MyInputProcessor implements InputProcessor 
{
	CardGame cardgame;
	
	public MyInputProcessor(CardGame c)
	{
		cardgame = c;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(button == Buttons.RIGHT)
			cardgame.rightClickCard(screenX, screenY);
		else
			cardgame.rightClickCard(-1, -1);
		if(button == Buttons.LEFT)
		{
			cardgame.leftClickCard(screenX, screenY);
			//System.out.println(screenX + ", " + screenY);
		}
		else
			cardgame.leftClickCard(-1, -1);
		
		cardgame.endTurn(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT)
		{
			cardgame.moveCard(screenX, screenY);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
