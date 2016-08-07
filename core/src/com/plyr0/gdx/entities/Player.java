package com.plyr0.gdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.plyr0.gdx.managers.SoundManager;
import com.plyr0.gdx.primitives.Line;
import com.plyr0.gdx.primitives.Point;
import com.plyr0.gdx.main.Game;

import java.util.List;

public class Player extends SpaceObject {
    private static final int MAX_BULLETS = 4;
    private static final int SHAPE_POINTS = 4;
    private boolean left;
    private boolean right;
    private boolean up;
    private float maxSpeed = 300; // px/s
    private float accelaration = 200;
    private float decelaration = Game.DECELERATION;

    private long score = 0;
    private int extraLives = 3;
    private long requiredScore = 10000;

    private float[] flamex;
    private float[] flamey;
    private float accelarationTimer;

    private List<Bullet> bullets;

    private boolean dead;
    private boolean hit;
    private Line[] hitLines;
    private Point[] hitPoint;
    private float hitTime;
    private float hitTimeCount;

    public Player(List<Bullet> bullets) {
        x = Game.getWidth() / 2;
        y = Game.getHeight() / 2;
        shapex = new float[SHAPE_POINTS];
        shapey = new float[SHAPE_POINTS];
        radians = MathUtils.PI / 2;
        rotationSpeed = MathUtils.PI;
        flamex = new float[3];
        flamey = new float[3];
        this.bullets = bullets;
        hit = false;
        hitTimeCount = 0;
        hitTime = 3;
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
        if(flag && !up){
            SoundManager.loop("thruster");
        } else if(!flag) {
            SoundManager.stop("thruster");
        }
        up = flag;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isHit() {
        return hit;
    }

    public void shoot() {
        SoundManager.play("shoot");
        if (bullets.size() < MAX_BULLETS && !hit && !dead) {
            Bullet b;
            if (Game.PLAYER_SPEED_AFFECTS_BULLETS) {
                b = new Bullet(x, y, radians, dx, dy);
            } else {
                b = new Bullet(x, y, radians);
            }
            bullets.add(b);

            if (Game.SHOOTING_AFFECTS_PLAYER_SPEED) {
                dx -= b.dx / 20;
                dy -= b.dy / 20;
            }
        }
    }

    @Override
    public void update(float dt) {
        if (score >= requiredScore) {
            ++extraLives;
            requiredScore += 10000;
            SoundManager.stop("extralife");
        }
        if (hit) {
            SoundManager.stop("thruster");
            hitTimeCount += dt;
            if (hitTimeCount > hitTime) {
                dead = true;
                hitTimeCount = 0;
            }
            for (int i = 0; i < hitLines.length; i++) {
                hitLines[i].setLine(hitLines[i].x1 + hitPoint[i].x * 10 * dt,
                        hitLines[i].y1 + hitPoint[i].y * 10 * dt,
                        hitLines[i].x2 + hitPoint[i].x * 10 * dt,
                        hitLines[i].y2 + hitPoint[i].y * 10 * dt);
            }
            return;
        }

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
        if (hit) {
            for (Line hitLine : hitLines) {
                renderer.line(hitLine.x1, hitLine.y1, hitLine.x2, hitLine.y2);
            }
        } else {
            for (int i = 0; i < SHAPE_POINTS; i++) {
                renderer.line(shapex[i], shapey[i], shapex[(i + 1) % SHAPE_POINTS], shapey[(i + 1) % SHAPE_POINTS]);
            }
            if (up) for (int i = 0; i < 3; i++) {
                renderer.line(flamex[i], flamey[i], flamex[(i + 1) % 3], flamey[(i + 1) % 3]);
            }
        }
        renderer.end();
    }


    public void hit() {
        if (hit) return;
        hit = true;
        dx = dy = 0;
        left = right = up = false;
        hitLines = new Line[4];
        for (int i = 0; i < shapex.length; i++) {
            hitLines[i] = new Line(shapex[i], shapey[i], shapex[(i + 1) % shapex.length],
                    shapey[(i + 1) % shapex.length]);
        }
        hitPoint = new Point[4];
        hitPoint[0] = new Point(MathUtils.cos(radians - 1.5f), MathUtils.sin(radians - 1.5f));
        hitPoint[1] = new Point(MathUtils.cos(radians - 2.8f), MathUtils.sin(radians - 2.8f));
        hitPoint[2] = new Point(MathUtils.cos(radians + 2.8f), MathUtils.sin(radians + 2.8f));
        hitPoint[3] = new Point(MathUtils.cos(radians + 1.5f), MathUtils.sin(radians + 1.5f));
    }

    public void reset() {
        x = Game.getWidth() / 2;
        y = Game.getHeight() / 2;
        hit = dead = false;
        setShape();
    }

    public long getScore() {
        return score;
    }

    public int getExtraLives() {
        return extraLives;
    }

    public void decrementLives() {
        --extraLives;
    }

    public void incrementScore(long score) {
        this.score += score;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setShape();

    }
}
