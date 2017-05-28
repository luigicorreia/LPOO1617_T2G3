package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

/**
 * Created by Luís on 26/05/2017.
 */

public class FireBoss extends Boss{

    PlayScreen screen;
    Body body;
    private Array<TextureRegion> frames;
    private Animation idleAnimation;
    private float stateTime;
    private Vector2 velocity;

    public FireBoss(PlayScreen screen){
        super(screen);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getFirebossAtlas().findRegion("fire_boss_spritesheet"), 3, 1, 40, 53));
        frames.add(new TextureRegion(screen.getFirebossAtlas().findRegion("fire_boss_spritesheet"), 40, 1, 40, 53));

        idleAnimation = new Animation(0.8f, frames);
        stateTime = 0;
        setBounds(0, 0, 252/MyGdxGame.PPM, 300/MyGdxGame.PPM);

        velocity = new Vector2(-4,0);


    }

    public void update(float dt){
        stateTime += dt;
        setPosition(body.getPosition().x - getWidth()/1.7f, body.getPosition().y-getHeight()/2f);
        setRegion((TextureRegion) idleAnimation.getKeyFrame(stateTime, true));
        body.setLinearVelocity(velocity);
    }

    @Override
    protected void defineBoss() {
        BodyDef bdef  = new BodyDef();
        bdef.position.set(2050/ MyGdxGame.PPM, 150/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef1 = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(70/MyGdxGame.PPM, 100/MyGdxGame.PPM);
        fdef1.filter.categoryBits = MyGdxGame.FIREBOSS_BIT;
        fdef1.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.WALL_BIT | MyGdxGame.ENEMY_BIT | MyGdxGame.SAMURAI_BIT | MyGdxGame.FIREBOSS_BIT | MyGdxGame.BULLET_BIT;

        fdef1.shape = shape;
        body.createFixture(fdef1).setUserData(this);
    }

    public void reverseVelocity(){
        velocity.x = -velocity.x;
    }


}
