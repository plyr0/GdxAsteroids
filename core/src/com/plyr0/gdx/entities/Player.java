package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.plyr0.gdx.main.Game;

import java.util.List;

public class Player extends SpaceObject {
    private static final int MAX_BULLETS = 10;
    private static final int SHAPE_POINTS = 4;
    private boolean left;
    private boolean right;
    private boolean up;
    private float maxSpeed = 300; // px/s
    private float accelaration = 200;
    private float decelaration = 10;

    private float[] flamex;
    private float[] flamey;
    private float accelarationTimer;

    private List<Bullet> bullets;

    public Player(List<Bullet> bullets) {
        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;
        shapex = new float[SHAPE_POINTS];
        shapey = new float[SHAPE_POINTS];
        radians = MathUtils.PI / 2;
        rotationSpeed = MathUtils.PI;
        flamex = new float[3];
        flamey = new float[3];
        this.bullets = bullets;
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

    private void setFlame() {
        flamex[0] = x + MathUtils.cos(radians - 5.0f / 6.0f * MathUtils.PI) * 5;
        flamey[0] = y + MathUtils.sin(radians - 5.0f / 6.0f * MathUtils.PI) * 5;
        flamex[1] = x + MathUtils.cos(radians - MathUtils.PI) * (6 + accelarationTimer * 50);
        flamey[1] = y + MathUtils.sin(radians - MathUtils.PI) * (6 + accelarationTimer * 50);
        flamex[2] = x + MathUtils.cos(radians + 5.0f / 6.0f * MathUtils.PI) * 5;
        flamey[2] = y + MathUtils.sin(radians + 5.0f / 6.0f * MathUtils.PI) * 5;
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

    public void shoot() {
         if(bullets.size()<MAX_BULLETS){
             bullets.add(new Bullet(x, y, radians));
         }
    }

    @Override
    public void update(float dt) {
        if (left) {
            radians += rotationSpeed * dt;
        } else if (right) {
            radians -= rotationSpeed * dt;
        }

        if (up) {
            dx += MathUtils.cos(radians) * accelaration * dt;
            dy += MathUtils.sin(radians) * accelaration * dt;
            accelarationTimer += dt;
            if (accelarationTimer > 0.1f) accelarationTimer = 0;
        } else {
            accelarationTimer = 0;
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

        if (up) setFlame();

        wrap();
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(1, 1, 1, 1);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < SHAPE_POINTS; i++) {
            renderer.line(shapex[i], shapey[i], shapex[(i + 1) % SHAPE_POINTS], shapey[(i + 1) % SHAPE_POINTS]);
        }
        if (up) for (int i = 0; i < 3; i++) {
            renderer.line(flamex[i], flamey[i], flamex[(i + 1) % 3], flamey[(i + 1) % 3]);
        }
        renderer.end();
    }


}
