package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

/**
 * Created by Luís on 02/06/2017.
 */

public class HealthItem extends Sprite{

    public Body body;
    private float x;
    private float y;
    PlayScreen screen;
    World world;
    float stateTime;
    private boolean setToDestroy, destroyed;

    /**
     * HealthItem constructor
     * @param screen
     * @param x
     * @param y
     */
    public HealthItem(PlayScreen screen, float x, float y){
        super(screen.getHealthAtlas().findRegion("Pixel_heart_icon"));
        this.screen = screen;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        stateTime = 0;
        setBounds(getX(), getY(), 57 / MyGdxGame.PPM, 51/MyGdxGame.PPM);
        setRegion(new TextureRegion(getTexture(), 0, 0, 570, 510));
        defineItemBody();

    }

    /**
     * HealthItem update method
     * @param dt
     */
    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
        else if(!destroyed){
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    /**
     * Defines the health item body
     */
    public void defineItemBody(){
        BodyDef bdef  = new BodyDef();
        bdef.position.set(x/ MyGdxGame.PPM, y/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef1 = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20/MyGdxGame.PPM, 20/MyGdxGame.PPM);
        fdef1.filter.categoryBits = MyGdxGame.ITEM_BIT;
        fdef1.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.WALL_BIT |MyGdxGame.SAMURAI_BIT;

        fdef1.shape = shape;
        body.createFixture(fdef1).setUserData(this);
    }

    /**
     * If the samurai hits the health item, it is set to be destroyed
     */
    public void hit(){
        setToDestroy = true;
    }

    /**
     * Checks if the health item is destroyed
     * @return destroyed boolean
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
