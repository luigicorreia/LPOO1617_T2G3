package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

public class Wall extends InteractiveTileObject{

	/**
	 * Wall constructor
	 * @param world
	 * @param map
	 * @param bounds
	 */
	public Wall(World world, TiledMap map, Rectangle bounds){
		super(world, map, bounds);
		fixture.setUserData(this);

	}

	/**
	 * Checks if the katana hits the wall (not used)
	 */
	@Override
	public void onKatanaHit() {
		Gdx.app.log("Wall", "Collision");
	}
}
