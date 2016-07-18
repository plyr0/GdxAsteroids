package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends SpaceObject {
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    private int type;
    private int numPoints;
    private float[] dists;

    public Asteroid(float x, float y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

        if (type == SMALL) {
            numPoints = 8;
            width = height = 12;
            speed = MathUtils.random(70, 100);
        } else if (type == MEDIUM) {
            numPoints = 10;
            width = height = 20;
            speed = MathUtils.random(50, 60);
        } else if (type == LARGE) {
            numPoints = 12;
            width = height = 40;
            speed = MathUtils.random(20, 30);
        }
        rotationSpeed = MathUtils.random(-1, 1);
        radians = MathUtils.random(MathUtils.PI2);
        dx = speed * MathUtils.cos(radians);
        dy = speed * MathUtils.sin(radians);

        shapex = new float[numPoints];
        shapey = new float[numPoints];
        dists = new float[numPoints];

        int radius = width / 2;
        for (int i = 0; i < numPoints; i++) {
            dists[i] = MathUtils.random(radius / 2, radius);
        }
        setShape();
    }

    private void setShape() {
        float angle = 0;
        float angleStep = MathUtils.PI2 / numPoints;
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + dists[i] * MathUtils.cos(angle + radians);
            shapey[i] = y + dists[i] * MathUtils.sin(angle + radians);
            angle += angleStep;
        }
    }

    public int getType() {
        return type;
    }

    @Override
    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
        radians += rotationSpeed * dt;
        setShape();
        wrap();
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < numPoints; i++) {
            renderer.line(shapex[i], shapey[i], shapex[(i + 1) % numPoints], shapey[(i + 1) % numPoints]);
        }
        renderer.end();
    }
}
