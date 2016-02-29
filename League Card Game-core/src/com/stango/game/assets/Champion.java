package com.stango.game.assets;

public class Champion extends Card
{
	private int maxhp, hp, atk, def, rec;
	private int mana1, mana2, cd1, cd2;
	private boolean cc;
	
	public Champion(String n, int h, int a, int d, int r, int m1, int cd1, int m2, int cd2, Type t) {
		super(n, 0, t);
		maxhp = h;
		hp = h;
		atk = a;
		def = d;
		rec = r;
		mana1 = m1;
		this.cd1 = cd1;
		mana2 = m2;
		this.cd2 = cd2;
		cc = false;
		}

	public int getMana1() {
		return mana1;
	}

	public void setMana1(int mana1) {
		this.mana1 = mana1;
	}

	public int getMana2() {
		return mana2;
	}

	public void setMana2(int mana2) {
		this.mana2 = mana2;
	}

	public int getCd1() {
		return cd1;
	}

	public void setCd1(int cd1) {
		this.cd1 = cd1;
	}

	public int getCd2() {
		return cd2;
	}

	public void setCd2(int cd2) {
		this.cd2 = cd2;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getRec() {
		return rec;
	}

	public void setRec(int rec) {
		this.rec = rec;
	}

	public boolean isCc() {
		return cc;
	}

	public void setCc(boolean cc) {
		this.cc = cc;
	}

	public boolean isDead()
	{
		return hp <= 0;
	}
}
