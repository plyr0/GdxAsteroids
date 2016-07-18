package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends SpaceObject {

    private float time;
    private float timeCounter;
    private boolean remove;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
        width = height = 1;
        speed = 50;
        radians = MathUtils.random(MathUtils.PI2);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;
        timeCounter = 0;
        time = 1;
    }

    public boolean shouldRemove() {
        return remove;
    }

    @Override
    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
        timeCounter += dt;
        if (timeCounter > time) remove = true;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(1, 1, 1, 1);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.point(x - width / 2, y - height / 2, 0);
        renderer.end();
    }
}
