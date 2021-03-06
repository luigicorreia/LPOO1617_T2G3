package com.mygdx.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

public class Samurai extends Sprite{




	public enum State{WALKING, STANDING, ATTACKING, JUMPING};
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	private TextureRegion standing;
	private Animation samuraiWalk;
	private Animation samuraiAttack;
	private float stateTimer;
	private boolean walkingRight;
	private int hitpoints;
    private  int counter=0;


	/**
	 * Samurai constructor
	 * @param screen
	 */
	public Samurai(PlayScreen screen){
		super(screen.getSamuraiAtlas().findRegion("samurai_walk"));
		this.world = screen.getWorld();
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		hitpoints = 100;
		walkingRight = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 170, 180, 78, 160));
		frames.add(new TextureRegion(getTexture(), 253, 180, 78, 160));
		frames.add(new TextureRegion(getTexture(), 336, 180, 78, 160));
		frames.add(new TextureRegion(getTexture(), 0, 180, 78, 160));
		samuraiWalk = new Animation(0.1f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(), 0, 5, 60, 160));
		frames.add(new TextureRegion(getTexture(), 78, 30, 95, 140));
		frames.add(new TextureRegion(getTexture(), 182, 30, 95, 140));
		frames.add(new TextureRegion(getTexture(), 298, 30, 95, 140));
		samuraiAttack = new Animation(0.4f, frames);
		frames.clear();


		defineSamurai();
		standing = new TextureRegion(getTexture(), 92, 183, 78, 157);
		setBounds(0,0,85/MyGdxGame.PPM, 145/MyGdxGame.PPM);
		setRegion(standing);
	}

	/**
	 * Samurai update method
	 * @param dt
	 */
	public void update(float dt){
		setPosition(b2body.getPosition().x - getWidth()/1.7f, b2body.getPosition().y-getHeight()/2.3f);
		setRegion(getFrame(dt));

	}

	/**
	 * Decreases the samurai's health by h
	 * @param h
	 */
	public void hpLoss(int h){
		if(hitpoints-h <=0){
			hitpoints=0;
		}else {
			hitpoints = hitpoints - h;
		}
	}

	/**
	 * Gets the samurai's health points
	 * @return samurai health points
	 */
	public int getHitpoints() {
		return hitpoints;
	}

	/**
	 * Gets the frame of the animation, depending on the state of the samurai
	 * @param dt
	 * @return the frame of the animation
	 */
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState){
		case WALKING:
			region = (TextureRegion) samuraiWalk.getKeyFrame(stateTimer, true);
			break;
		case ATTACKING:
			region = (TextureRegion) samuraiAttack.getKeyFrame(stateTimer, true);
			break;
			case JUMPING:
				region = standing;
				break;
		case STANDING:
		default:
			region = standing;
			break;
		}
		
		if((b2body.getLinearVelocity().x < 0 || !walkingRight) && !region.isFlipX()){
			region.flip(true, false);
			walkingRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || walkingRight) && region.isFlipX()){
			region.flip(true, false);
			walkingRight = true;
		}
		
		stateTimer = currentState == previousState ? stateTimer +dt : 0;
		previousState = currentState;
		return region;
		
	}

	/**
	 * Gets the state of the samurai
	 * @return state of the samurai
	 */
	public State getState() {
		if(Gdx.input.isKeyPressed(Input.Keys.P))
			return State.ATTACKING;
		else if(b2body.getLinearVelocity().y !=0)
			return State.JUMPING;
		else if(b2body.getLinearVelocity().x != 0)
			return State.WALKING;
		else
			return State.STANDING;
	}

	/**
	 * Defines the body of the samurai
	 */
	public void defineSamurai(){
		BodyDef bdef  = new BodyDef();
		bdef.position.set(300/MyGdxGame.PPM, 150/MyGdxGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef1 = new FixtureDef();
		//CircleShape shape = new CircleShape();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(20/MyGdxGame.PPM, 60/MyGdxGame.PPM);
		fdef1.filter.categoryBits = MyGdxGame.SAMURAI_BIT;
		fdef1.filter.maskBits = MyGdxGame.OBSTACLE_BIT | MyGdxGame.SAMURAI_BIT | MyGdxGame.GROUND_BIT | MyGdxGame.FIREBALL_BIT| MyGdxGame.WALL_BIT | MyGdxGame.FIREBOSS_BIT | MyGdxGame.TRIGGER_BIT | MyGdxGame.FIREBOSS_HEAD_BIT | MyGdxGame.BAT_BIT | MyGdxGame.DARKBALL_BIT | MyGdxGame.ITEM_BIT;

		fdef1.shape = shape;
		b2body.createFixture(fdef1).setUserData(this);

        FixtureDef fdef2 = new FixtureDef();
        EdgeShape katanaLeft = new EdgeShape();

        katanaLeft.set(new Vector2(48/MyGdxGame.PPM, -20/MyGdxGame.PPM), new Vector2(48/MyGdxGame.PPM, 100/MyGdxGame.PPM));
        fdef2.shape = katanaLeft;
       fdef2.isSensor = true;

        b2body.createFixture(fdef2).setUserData("katana");

        FixtureDef fdef3 = new FixtureDef();
        EdgeShape katanaRight = new EdgeShape();

        katanaRight.set(new Vector2(-48/MyGdxGame.PPM, -20/MyGdxGame.PPM), new Vector2(-48/MyGdxGame.PPM, 100/MyGdxGame.PPM));
        fdef3.shape = katanaRight;
        fdef3.isSensor = true;

        b2body.createFixture(fdef3).setUserData("katana");

	}

	/**
	 * Checks if the samurai is walking to his right
	 * @return isWalkingRight boolean
	 */
	public boolean isWalkingRight() {
		return walkingRight;
	}

	/**
	 * Increases the samurai health points by 100
	 */
	public void heal(){
		hitpoints += 100;
	}

	/**
	 * Gets the counter
	 * @return counter number
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Sets the counter
	 * @param counter
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}
}
