package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bat;
import com.mygdx.game.sprites.BlueBullet;
import com.mygdx.game.sprites.DarkBall;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.FireBall;
import com.mygdx.game.sprites.FireBoss;
import com.mygdx.game.sprites.HealthItem;
import com.mygdx.game.sprites.InteractiveTileObject;
import com.mygdx.game.sprites.MageBoss;
import com.mygdx.game.sprites.Samurai;

public class WorldContactListener implements ContactListener{

	/**
	 * Checks the collisions between entities
	 * @param contact
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		int cDef= fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		if(fixA.getUserData() == "katana" || fixB.getUserData() == "katana"){
			Fixture katana = fixA.getUserData() == "katana" ? fixA : fixB;
			Fixture object = katana == fixA ? fixB : fixA;

			if(object.getUserData() != null && object.getUserData() instanceof InteractiveTileObject) {
				((InteractiveTileObject)object.getUserData()).onKatanaHit();
				System.out.print("hi");
			}
		}


		switch (cDef){
			case MyGdxGame.GROUND_BIT | MyGdxGame.SAMURAI_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT){
					((Samurai)fixA.getUserData()).setCounter(0);
				}
				else if (fixB.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT) {
						((Samurai)fixB.getUserData()).setCounter(0);
				}
				break;
			case MyGdxGame.WALL_BIT | MyGdxGame.SAMURAI_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT){
					((Samurai)fixA.getUserData()).setCounter(0);
				} else {
					if (fixB.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT) {
						((Samurai)fixB.getUserData()).setCounter(0);
					}
				}
				break;
			case MyGdxGame.FIREBALL_BIT | MyGdxGame.SAMURAI_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.FIREBALL_BIT){
					((FireBall)fixA.getUserData()).hit();
					((Samurai)fixA.getUserData()).hpLoss(10);
				} else {
					if (fixB.getFilterData().categoryBits == MyGdxGame.FIREBALL_BIT) {
						((FireBall) fixB.getUserData()).hit();
						((Samurai) fixA.getUserData()).hpLoss(10);
					}
				}
				break;
			case MyGdxGame.WALL_BIT | MyGdxGame.FIREBALL_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.FIREBALL_BIT){
					((FireBall)fixA.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.FIREBALL_BIT) {
					((FireBall) fixB.getUserData()).hit();
				}
				break;
			case MyGdxGame.BULLET_BIT | MyGdxGame.FIREBALL_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.FIREBALL_BIT){
					((FireBall)fixA.getUserData()).hit();
					((BlueBullet)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.FIREBALL_BIT) {
					((BlueBullet)fixA.getUserData()).hit();
					((FireBall) fixB.getUserData()).hit();
				}
				break;
			case MyGdxGame.WALL_BIT | MyGdxGame.FIREBOSS_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.WALL_BIT){
					((FireBoss) fixB.getUserData()).reverseVelocity();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.WALL_BIT) {
					((FireBoss) fixA.getUserData()).reverseVelocity();
				}
				break;
			case MyGdxGame.BULLET_BIT | MyGdxGame.FIREBOSS_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.FIREBOSS_BIT){
					((BlueBullet)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.FIREBOSS_BIT) {
					((BlueBullet)fixA.getUserData()).hit();
				}
				break;
			case MyGdxGame.TRIGGER_BIT | MyGdxGame.SAMURAI_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT){
					((Trigger)fixB.getUserData()).hit();
					((Trigger)fixB.getUserData()).startFireboss();
					((Trigger)fixB.getUserData()).startMageboss();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT) {
					((Trigger)fixA.getUserData()).hit();
					((Trigger)fixA.getUserData()).startFireboss();
					((Trigger)fixB.getUserData()).startMageboss();
				}
				break;
			case MyGdxGame.FIREBOSS_HEAD_BIT | MyGdxGame.BULLET_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.FIREBOSS_HEAD_BIT){
					((FireBoss)fixA.getUserData()).damage(15);
					((BlueBullet)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.FIREBOSS_HEAD_BIT) {
					((FireBoss)fixB.getUserData()).damage(15);
					((BlueBullet)fixA.getUserData()).hit();
				}
				break;
			case MyGdxGame.MAGEBOSS_BIT | MyGdxGame.BULLET_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.MAGEBOSS_BIT){
					((MageBoss)fixA.getUserData()).teleport();
					((MageBoss)fixA.getUserData()).damage(10);
					((BlueBullet)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.MAGEBOSS_BIT) {
					((MageBoss)fixB.getUserData()).teleport();
					((MageBoss)fixB.getUserData()).damage(10);
					((BlueBullet)fixA.getUserData()).hit();
				}
				break;
			case  MyGdxGame.BULLET_BIT | MyGdxGame.WALL_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT){
					((BlueBullet)fixA.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.BULLET_BIT) {
					((BlueBullet) fixB.getUserData()).hit();
				}
				break;
			case  MyGdxGame.BAT_BIT | MyGdxGame.SAMURAI_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.BAT_BIT){
					((Bat)fixA.getUserData()).hit();
					((Samurai)fixB.getUserData()).hpLoss(10);
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.BAT_BIT) {
					((Samurai)fixA.getUserData()).hpLoss(10);
					((Bat) fixB.getUserData()).hit();
				}
				break;
			case  MyGdxGame.BAT_BIT | MyGdxGame.BULLET_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.BAT_BIT){
					((Bat)fixA.getUserData()).hit();
					((BlueBullet)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.BAT_BIT) {
					((BlueBullet)fixA.getUserData()).hit();
					((Bat) fixB.getUserData()).hit();
				}
				break;
			case MyGdxGame.ITEM_BIT | MyGdxGame.SAMURAI_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.ITEM_BIT){
					((HealthItem)fixA.getUserData()).hit();
					((Samurai)fixB.getUserData()).heal();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.ITEM_BIT) {
					((Samurai)fixA.getUserData()).heal();
					((HealthItem) fixB.getUserData()).hit();
				}
				break;
			case MyGdxGame.BULLET_BIT | MyGdxGame.DARKBALL_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT){

					((BlueBullet)fixA.getUserData()).hit();
					((DarkBall)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.BULLET_BIT) {
					((DarkBall)fixA.getUserData()).hit();
					((BlueBullet) fixB.getUserData()).hit();
				}
				break;
			case MyGdxGame.SAMURAI_BIT | MyGdxGame.DARKBALL_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT){
					((Samurai)fixA.getUserData()).hpLoss(10);
					((DarkBall)fixB.getUserData()).hit();
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT) {
					((DarkBall)fixA.getUserData()).hit();
					((Samurai) fixB.getUserData()).hpLoss(10);
				}
				break;
			case MyGdxGame.SAMURAI_BIT | MyGdxGame.FIREBOSS_BIT:
				if(fixA.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT){
					((Samurai)fixA.getUserData()).hpLoss(5);
				} else if(fixB.getFilterData().categoryBits == MyGdxGame.SAMURAI_BIT) {
					((Samurai) fixB.getUserData()).hpLoss(5);
				}
				break;





		}
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	
}
