package com.mystarwars;


import com.badlogic.gdx.Game;
import com.mystarwars.screen.MenuScreen;

public class StarWars extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
