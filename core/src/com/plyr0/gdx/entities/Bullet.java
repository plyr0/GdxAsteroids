package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {

    private float lifeTime;
    private float lifeTimeClock;
    private boolean remove;

    public Bullet(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
        float speed = 350;
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;
        width = height = 2;
        lifeTimeClock = 0;
        lifeTime = 1;
    }


    public boolean shouldRemove() {
        return remove;
    }

    @Override
    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
        wrap();
        lifeTimeClock += dt;
        if (lifeTimeClock >= lifeTime) remove = true;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(1, 1, 1, 1);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(x - width / 2, y - height / 2, width / 2);
        renderer.end();
    }
}
