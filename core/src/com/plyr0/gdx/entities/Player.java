package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.plyr0.gdx.main.Game;

public class Player extends SpaceObject {
    protected boolean left;
    protected boolean right;
    protected boolean up;

    protected float maxSpeed = 300; // px/s
    protected float accelaration = 200;
    protected float decelaration = 10;

    protected static final int SHAPE_POINTS = 4;

    public Player() {
        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;
        shapex = new float[SHAPE_POINTS];
        shapey = new float[SHAPE_POINTS];
        radians = MathUtils.PI / 2;
        rotationSpeed = MathUtils.PI;
    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;
        shapex[1] = x + MathUtils.cos(radians - 4.0f / 5.0f * MathUtils.PI) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4.0f / 5.0f * MathUtils.PI) * 8;
        shapex[2] = x + MathUtils.cos(radians + MathUtils.PI) * 5;
        shapey[2] = y + MathUtils.sin(radians + MathUtils.PI) * 5;
        shapex[3] = x + MathUtils.cos(radians + 4.0f / 5.0f * MathUtils.PI) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4.0f / 5.0f * MathUtils.PI) * 8;
    }

    public void setLeft(boolean flag) {
        left = flag;
    }

    public void setRight(boolean flag) {
        right = flag;
    }

    public void setUp(boolean flag) {
        up = flag;
    }

    public void update(float dt) {
        if (left) {
            radians += rotationSpeed * dt;
        } else if (right) {
            radians -= rotationSpeed * dt;
        }

        if (up) {
            dx += MathUtils.cos(radians) * accelaration * dt;
            dy += MathUtils.sin(radians) * accelaration * dt;
        }

        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * decelaration * dt;
            dy -= (dy / vec) * decelaration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        x += dx * dt;
        y += dy * dt;

        setShape();
        wrap();
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(1, 1, 1, 1);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < SHAPE_POINTS; i++) {
            renderer.line(shapex[i], shapey[i], shapex[(i + 1) % SHAPE_POINTS], shapey[(i + 1) % SHAPE_POINTS]);
        }
        renderer.end();
    }
}
