/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Anim;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Cron extends Enemies {
    private int oldX;
    private int oldY;
    private int randomSteps;
    private int generatedDirection;
    

    public Cron() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/cron_w", 1), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/cron_e", 1), 250);
        super.animation = this.leftAnimation;
        this.animation.start();
        direction = Direction.WEST;
        randomSteps = 0;
    }

    @Override
    public void act() {
        walk();
        super.act();
    }

    public void walk() {
        oldX = this.getX();
        oldY = this.getY();
        if (randomSteps == 0) {
            generateMovement();
        }
        switch (generatedDirection) {
            case 0:
                direction = Direction.NORTH;
                this.y--;
                break;
            case 90:
                direction = Direction.EAST;
                this.animation = rightAnimation;
                this.x++;
                break;
            case 180:
                direction = Direction.SOUTH;
                this.y++;
                break;
            case 270:
                direction = Direction.WEST;
                this.animation = leftAnimation;
                this.x--;
                break;
        }
        randomSteps--;

    }

    public void generateMovement() {
        Player hrac = (Player) level.getPlayer();
        if ((hrac.getX() - 200 < this.getX() && hrac.getX() + 200 > this.getX()) || 
                (hrac.getY() - 200 < this.getY() && hrac.getY() + 200 > this.getY())) {
            stalk();
        } else {
        Random r = new Random();
        generatedDirection = r.nextInt(4) * 90;
        randomSteps = r.nextInt(50) + 3;
        }
    }

    public void stalk() {
        int z;
        Player hrac = (Player) level.getPlayer();
        Random r = new Random();
        z = r.nextInt(2);
        switch (z) {
            case 0:
                if (hrac.getX() < this.getX()) {
                    this.generatedDirection = 270;
                } else {
                    this.generatedDirection = 90;
                }
                randomSteps = 45;
                break;
            case 1:
                if (hrac.getY() < this.getY()) {
                    this.generatedDirection = 0;
                } else {
                    this.generatedDirection = 180;
                }
                randomSteps = 45;
                break;
        }
    }

    @Override
    public void changeDirection() {
        this.setPosition(oldX, oldY);
        randomSteps = 0;
    }
}
