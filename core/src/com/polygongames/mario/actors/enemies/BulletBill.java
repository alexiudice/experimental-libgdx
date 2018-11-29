package com.polygongames.mario.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.polygongames.mario.actors.Collider;
import com.polygongames.mario.actors.Mario;
import com.polygongames.mario.gamesys.GameManager;
import com.polygongames.mario.screens.PlayScreen;

public class BulletBill extends Enemy
{

    private TextureRegion bulletBill;
    private Animation flying;

    public BulletBill( PlayScreen playScreen, float x, float y )
    {
        super( playScreen, x, y );
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        keyFrames.add(  new TextureRegion(textureAtlas.findRegion("bulletBill")));

        flying = new Animation( 0.2f, keyFrames );

        setRegion(keyFrames.get(0));
        setSize(  16.0f / GameManager.PPM, 32.0f / GameManager.PPM);
    }

    @Override
    public void getDamage( int damage )
    {

    }

    @Override
    public void interact( Mario mario )
    {
        super.interact( mario );
    }

    @Override
    protected void defBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

        //top
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(
                new Vector2(-4.0f, 7.0f).scl(1 / GameManager.PPM),
                new Vector2(8.0f, 7.0f).scl(1 / GameManager.PPM)
        );

        fixtureDef.shape = edgeShape;
        fixtureDef.filter.categoryBits = GameManager.ENEMY_WEAKNESS_BIT;
        fixtureDef.filter.maskBits = GameManager.MARIO_BIT;
        body.createFixture(fixtureDef).setUserData(this);


        // lethal
        Vector2[] vertices = {
                new Vector2(-3f, 7.0f).scl(1 / GameManager.PPM),
                new Vector2(-7f, 2.0f).scl(1 / GameManager.PPM),
                new Vector2(-7f, -2.0f).scl(1 / GameManager.PPM),
                new Vector2(-3f, -7.0f).scl(1 / GameManager.PPM),
                new Vector2(7f, -7.0f).scl(1 / GameManager.PPM),
                new Vector2(7f, 7.0f).scl(1 / GameManager.PPM)
        };
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = GameManager.ENEMY_LETHAL_BIT;
        fixtureDef.filter.maskBits = GameManager.MARIO_BIT;

        body.createFixture(fixtureDef).setUserData(this);

        edgeShape.dispose();
        polygonShape.dispose();
    }

    @Override
    public void update( float delta )
    {

        if (destroyed) {
            return;
        }

        if (toBeDestroyed) {
            world.destroyBody(body);
            setBounds(0, 0, 0, 0);
            destroyed = true;
            return;
        }

        if (playScreen.getMarioPosition().x + GameManager.V_WIDTH / 2 + 4 > body.getPosition().x )
            active = true;

        if (!active) {
            return;
        }

    }

}
