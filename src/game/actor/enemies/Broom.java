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
public class Broom extends Enemies {
    private int oldX;
    private int oldY;
    private int randomSteps;
    private int randomDirection;
    private final static int SPEED = 2;

    public Broom() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/broom", 1), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/broom", 1), 250);
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
        switch (randomDirection) {
            case 0:
                direction = Direction.NORTH;
                this.y-=SPEED;
                break;
            case 90:
                direction = Direction.EAST;
                this.animation = rightAnimation;
                this.x+=SPEED;
                break;
            case 180:
                direction = Direction.SOUTH;
                this.y+=SPEED;
                break;
            case 270:
                direction = Direction.WEST;
                this.animation = leftAnimation;
                this.x-=SPEED;
                break;
        }
        randomSteps--;

    }

    public void generateMovement() {
        Random r = new Random();
        randomDirection = r.nextInt(4) * 90;
        randomSteps = r.nextInt(50) + 3;
    }

    @Override
    public void changeDirection() {    
        this.setPosition(oldX, oldY);
        randomSteps = 0;
    }
}
