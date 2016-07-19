package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends SpaceObject {

    private float timeToLive = 1;
    private float timeCounter;
    private boolean dead = true;

    public Particle() {
        width = height = 1;
        speed = 50;
    }

    public void spawn(float x, float y) {
        this.x = x;
        this.y = y;
        radians = MathUtils.random(MathUtils.PI2);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;
        dead = false;
        timeCounter = 0;
    }

    @Override
    public void update(float dt) {
        if (dead) return;
        x += dx * dt;
        y += dy * dt;
        timeCounter += dt;
        if (timeCounter > timeToLive) dead = true;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (dead) return;
        renderer.setColor(1, 1, 1, 1);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.point(x - width / 2, y - height / 2, 0);
        renderer.end();
    }
}
