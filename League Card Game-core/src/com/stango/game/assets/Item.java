package com.stango.game.assets;

public class Item extends Card
{
	private int boosthp, boostatk, boostdef, boostrec;
	
	public Item(String n, int m, Type t, int b1, int b2, int b3, int b4) {
		super(n, m, Type.ITEM);
		boosthp = b1;
		boostatk = b2;
		boostdef = b3;
		boostrec = b4;
	}
	
	public int getBoosthp() {
		return boosthp;
	}

	public int getBoostatk() {
		return boostatk;
	}

	public int getBoostdef() {
		return boostdef;
	}

	public int getBoostrec() {
		return boostrec;
	}

}
