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

    public float[] getShapey() {
        return shapey;
    }

    public float[] getShapex() {
        return shapex;
    }

    public boolean contains(float testx, float testy) {
        int nvert = shapex.length - 1;
        boolean even = false;
        for (int i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((shapey[i] > testy) != (shapey[j] > testy)) &&
                    (testx < (shapex[j] - shapex[i]) * (testy - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i])) {
                even = !even;
            }
        }
        return even;
    }

    public boolean intersects(SpaceObject other) {
        float[] otherx = other.getShapex();
        float[] othery = other.getShapey();
        for (int i = 0; i < otherx.length; i++) {
            if (contains(otherx[i], othery[i])) return true;
        }
        return false;
    }
}
