package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ai.aiInterface;
import com.mygdx.game.screens.PlayScreen;



/**
 * Created by Luís on 31/05/2017.
 */

public class Bat extends Enemy implements aiInterface {

    private float stateTime;
    private Animation flyingAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    /**
     * Bat constructor
     * @param screen
     * @param x
     * @param y
     * @param distance
     */
    public Bat(PlayScreen screen, float x, float y, float distance){
        super(screen, x, y, distance);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getBatAtlas().findRegion("bat sprite"), i*47, 0, 47, 47));
        }
         for(int i = 0; i < 5; i++){
            frames.get(i).flip(true, false);
        }

        flyingAnimation = new Animation(0.2f, frames);
        this.stateTime = 0;
        setBounds(getX(), getY(), 47*2/ MyGdxGame.PPM, 47*2/MyGdxGame.PPM);

    }

    /**
     * Bat update method
     * @param dt - delta time
     */
    public void update(float dt) {
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed) {
            move(super.screen.getSamurai());
            setRegion((TextureRegion) flyingAnimation.getKeyFrame(stateTime, true));
        }
    }

    /**
     * Defines the body of the this enemy
     */
    @Override
    protected void defineEnemy() {
        BodyDef bdef  = new BodyDef();
        bdef.position.set(distance/MyGdxGame.PPM, randomHeightBetween(50, 500)/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef1 = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25/MyGdxGame.PPM);
        fdef1.filter.categoryBits = MyGdxGame.BAT_BIT;
        fdef1.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.SAMURAI_BIT | MyGdxGame.BULLET_BIT;

        fdef1.shape = shape;
        b2body.createFixture(fdef1).setUserData(this);
    }

    @Override
    public int randomHeightBetween(int min, int max) {
        return super.randomHeightBetween(min, max);
    }

    /**
     * Makes the bat move towards the samurai
     * @param samurai
     */
    @Override
    public void move(Samurai samurai) {
        float x= samurai.b2body.getPosition().x;
        float y= samurai.b2body.getPosition().y;
        if(x != this.getX() ){
            if(b2body.getPosition().x < x && b2body.getPosition().y >= y){
                setPosition((b2body.getPosition().x - getWidth() / 1.7f)+0.05f, (b2body.getPosition().y - getHeight() / 2.3f) - 0.05f);
                b2body.setLinearVelocity(batCounterVelocity);
            }else if(b2body.getPosition().x > x && b2body.getPosition().y >= y){
                setPosition((b2body.getPosition().x - getWidth() / 1.7f)-0.05f, (b2body.getPosition().y - getHeight() / 2.3f)-0.05f);
                b2body.setLinearVelocity(batVelocity);
            } else if(b2body.getPosition().x == x && b2body.getPosition().y == y){
                setPosition(b2body.getPosition().x - getWidth() / 1.7f, b2body.getPosition().y - getHeight() / 2.3f);
                b2body.setLinearVelocity(new Vector2(0,0));
            }
            else if( b2body.getPosition().y < y){
                setPosition(b2body.getPosition().x - getWidth() / 1.7f, (b2body.getPosition().y - getHeight() / 2.3f)+0.05f);
                b2body.setLinearVelocity(new Vector2(0,2));
            } else if(b2body.getPosition().x < x && b2body.getPosition().y < y){
                setPosition((b2body.getPosition().x - getWidth() / 1.7f)+0.05f, (b2body.getPosition().y - getHeight() / 2.3f)+0.05f);
                b2body.setLinearVelocity(new Vector2(2,2));
            }else if(b2body.getPosition().x > x && b2body.getPosition().y < y){
                setPosition((b2body.getPosition().x - getWidth() / 1.7f)-0.05f, (b2body.getPosition().y - getHeight() / 2.3f)+0.05f);
                b2body.setLinearVelocity(new Vector2(-2,2));
            }

        }

    }

    /**
     * If the bats hits the samurai, it gets set to be destroyed
     */
    @Override
    public void hit() {
        setToDestroy = true;
    }

    /**
     * Checks if the bat is destroyed
     * @return destroyed boolean
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
