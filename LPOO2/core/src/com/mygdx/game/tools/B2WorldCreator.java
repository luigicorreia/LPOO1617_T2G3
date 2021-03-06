package com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

public class B2WorldCreator {

	/**
	 * Box 2D world contructor
	 * @param screen
	 */
	public B2WorldCreator(PlayScreen screen){
		World world = screen.getWorld();
		TiledMap map = screen.getMap();

		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//floor body
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
		
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX()+rect.getWidth()/2)/MyGdxGame.PPM, (rect.getY()+rect.getHeight()/2)/MyGdxGame.PPM);
			
			body = world.createBody(bdef);
			
			shape.setAsBox((rect.getWidth()/2)/MyGdxGame.PPM, (rect.getHeight()/2)/MyGdxGame.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		
		//wall body
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX()+rect.getWidth()/2)/MyGdxGame.PPM, (rect.getY()+rect.getHeight()/2)/MyGdxGame.PPM);

			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth()/2)/MyGdxGame.PPM, (rect.getHeight()/2)/MyGdxGame.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = MyGdxGame.WALL_BIT;


			body.createFixture(fdef);
		}
		
	}
	
}
