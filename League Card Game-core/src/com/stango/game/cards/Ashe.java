package com.stango.game.cards;

import com.stango.game.assets.*;

public class Ashe extends Champion implements Attacker
{
	public Ashe()
	{
		super("Ashe", 25, 5, 1, 2, 2, 1, 5, 5, Type.ATTACK);
	}
	
	public boolean getSkillOneCC()
	{
		return false;
	}
	
	public int getSkillOneDmg()
	{
		return 3;
	}
	
	public int getSkillSideDmg()
	{
		return 1;
	}
	
	public boolean getSkillTwoCC()
	{
		return true;
	}
	
	public int getSkillTwoDmg()
	{
		return 7;
	}

	public int getSkillLaneDmg() 
	{
		return 0;
	}

	public int getSkillAllDmg() 
	{
		return 0;
	}
	
}
