package com.polygongames.mario.actors.maptiles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.polygongames.mario.actors.Collider;
import com.polygongames.mario.actors.Mario;
import com.polygongames.mario.actors.effects.FlippingCoin;
import com.polygongames.mario.actors.items.Flower;
import com.polygongames.mario.actors.items.Mushroom;
import com.polygongames.mario.gamesys.GameManager;
import com.polygongames.mario.screens.LevelScreen;
import com.polygongames.mario.screens.PlayScreen;

public class Lava extends MapTileObject
{
    public Lava( PlayScreen playScreen, float x, float y, TiledMapTileMapObject mapObject) {
        super(playScreen, x, y, mapObject);
    }

    protected void defBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16 / GameManager.PPM / 2, 16 / GameManager.PPM / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = GameManager.LAVA_BIT;
        fixtureDef.filter.maskBits = GameManager.MARIO_BIT;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }


    @Override
    public void onTrigger(Collider other) {
        if (other.getUserData() instanceof Mario) {
            ((Mario) other.getUserData()).suddenDeath();
        }

    }

}
