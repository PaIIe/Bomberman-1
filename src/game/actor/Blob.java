/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Actors;
import game.Anim;
import game.Level;
import game.MapObjects;
import game.item.Items;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Enemy extends Actors {

    private Animation leftAnimation;
    private Animation rightAnimation;
    private Direction direction;
    private int randomSteps;
    private int randomDirection;
    private Level level = Level.getLevel();

    public Enemy() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/actors/blob_w", 1), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/actors/blob_e", 1), 250);
        super.animation = this.leftAnimation;
        this.animation.start();
        direction = Direction.WEST;
        randomSteps = 0;
    }

    @Override
    public void act() {
        walk();
    }

    public void walk() {
        int oldX = this.getX();
        int oldY = this.getY();
        if (randomSteps == 0) {
            generateMovement();
        }
        switch (randomDirection) {
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
        if (this.intersectWithWall()) {
            this.setPosition(oldX, oldY);
            randomSteps = 0;
        }
        for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
            if (o.intersects(this)) {
                if (o instanceof Flame) {
                    level.getListOfObjects().remove(this);
                }
                if((o instanceof Bombs) || (o instanceof Items)){
                    this.setPosition(oldX, oldY);
                    randomSteps = 0;
                }
            }
        }
    }

    public void generateMovement() {
        Random r = new Random();
        randomDirection = r.nextInt(4) * 90;
        randomSteps = r.nextInt(50) + 3;
    }
}
