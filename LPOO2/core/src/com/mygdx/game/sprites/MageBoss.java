package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

/**
 * Created by Luís on 31/05/2017.
 */



public class MageBoss extends Boss {

    public Body body;
    private Array<TextureRegion> frames;
    private Animation idleAnimation;
    private float stateTime;
    private boolean teleporting;
    private boolean crossed;


    public MageBoss(PlayScreen screen){
        super(screen);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getMagebossAtlas().findRegion("Mage Boss"), i * 65, 8, 65, 78));
        }

        flip();

        idleAnimation= new Animation(0.2f, frames);

        stateTime = 0;
        setBounds(0, 0, 65*2.3f/ MyGdxGame.PPM, 78*2.3f/MyGdxGame.PPM);
        teleporting = false;
        crossed = false;
    }


    public void update(float dt){
        stateTime += dt;
        float samuraiX = screen.getSamurai().b2body.getPosition().x;
        float samuraiY = screen.getSamurai().b2body.getPosition().y;
        setPosition(body.getPosition().x - getWidth()/2f, body.getPosition().y-getHeight()/2.1f);
        setRegion((TextureRegion) idleAnimation.getKeyFrame(stateTime, true));
        if(teleporting){
            if(samuraiX < body.getPosition().x){
                body.setTransform(samuraiX - 2f, samuraiY, 0);
                flip();
            }
            else if(samuraiX > body.getPosition().x){
                body.setTransform(samuraiX + 2f, samuraiY, 0);
                flip();
            }
            teleporting = false;
        }

    }

    @Override
    protected void defineBoss() {
        BodyDef bdef  = new BodyDef();
        bdef.position.set(8000/ MyGdxGame.PPM, 150/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef1 = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(45/MyGdxGame.PPM, 80/MyGdxGame.PPM);
        fdef1.filter.categoryBits = MyGdxGame.MAGEBOSS_BIT;
        fdef1.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.WALL_BIT | MyGdxGame.FIREBOSS_BIT | MyGdxGame.BULLET_BIT | MyGdxGame.MAGEBOSS_BIT;

        fdef1.shape = shape;
        body.createFixture(fdef1).setUserData(this);

    }

    public void teleport(){
       teleporting = true;
    }

    public void flip(){
        for(int i = 0; i < frames.size; i++) {
            frames.get(i).flip(true, false);
        }
    }

}