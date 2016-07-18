package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {

    private float lifeTime = 1;
    private float lifeTimeClock = 0;
    private boolean removeFlag;

    public Bullet(float x, float y, float radians) {
        this(x, y, radians, 0, 0);
    }

    public Bullet(float x, float y, float radians, float dx, float dy) {
        width = height = 2;
        speed = 350;
        this.x = x;
        this.y = y;
        this.radians = radians;
        this.dx = MathUtils.cos(radians) * speed + dx;
        this.dy = MathUtils.sin(radians) * speed + dy;
    }

    public boolean shouldRemove() {
        return removeFlag;
    }

    @Override
    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
        wrap();
        lifeTimeClock += dt;
        if (lifeTimeClock >= lifeTime) removeFlag = true;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(1, 1, 1, 1);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(x - width / 2, y - height / 2, width / 2);
        renderer.end();
    }
}
