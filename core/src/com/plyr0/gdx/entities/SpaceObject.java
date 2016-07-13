package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.plyr0.gdx.main.Game;

public abstract class SpaceObject {
    protected float x;
    protected float y;

    protected float dx;
    protected float dy;

    protected float radians;
    protected float speed;
    protected float rotationSpeed;

    protected int width;
    protected int height;

    protected float[] shapex;
    protected float[] shapey;

    protected void wrap() {
        if (x < 0) x = Game.WIDTH;
        if (y < 0) y = Game.HEIGHT;
        if (x > Game.WIDTH) x = 0;
        if (y > Game.HEIGHT) y = 0;
    }

    public abstract void update(float dt);

    public abstract void draw(ShapeRenderer renderer);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
