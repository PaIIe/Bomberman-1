/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Actors;
import game.Anim;
import game.Level;
import game.Wall;
import game.Walls;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Bombs extends Actors {

    private int explodeTime;
    Animation explodingBomb;
    private boolean exploded;
    Level level = Level.getLevel();
    private boolean intersectWithPlayer;

    public Bombs() throws SlickException {
        animation = new Animation(Anim.getAnimation("resources/actors/bomb", 2), 200);
        explodingBomb = new Animation(Anim.getAnimation("resources/actors/expbomb", 1), 20);
        explodeTime = 300;
        exploded = false;
        intersectWithPlayer = true;
        
    }

    @Override
    public void act() {
        if(!level.getPlayer().intersects(this)){
            this.intersectWithPlayer = false;
        }
        
        if (!exploded) {
            if (explodeTime == 0) {
                animation = explodingBomb;
                exploded = true;
                explodeTime = 50;
                try {
                    createFlames();
                } catch (SlickException ex) {
                }
            } else {
                explodeTime--;
            }
        } else {
            if (explodeTime == 0) {
                level.getListOfObjects().remove(this);
                level.getPlayer().setBomb();
            } else {
                explodeTime--;
            }
        }
    }

    public void createFlames() throws SlickException {
        int range = level.getPlayer().getRange();
        lookAround(Direction.EAST, range);
        lookAround(Direction.WEST, range);
        lookAround(Direction.NORTH, range);
        lookAround(Direction.SOUTH, range);
    }

    public void lookAround(Direction direction, int range) throws SlickException {
        int oldX = this.getX();
        int oldY = this.getY();
        switch (direction) {
            case EAST:
                for (int r = 0; r < range; r++) {
                    this.x += (16 + 32 * r);
                    if (!this.intersectWithWall()) {
                        Flame flame = new Flame(Direction.EAST);
                        flame.setPosition(oldX + 32 + 32 * r, oldY);
                        level.addToLevel(flame);
                    } else {
                        checkWall(this.getX()+32, this.getY());
                        this.x -= (16 + 32 * r);
                        break;
                    }
                    this.x -= (16 + 32 * r);
                }
                break;
            case WEST:
                for (int r = 0; r < range; r++) {
                    this.x -= (16 + 32 * r);
                    if (!this.intersectWithWall()) {
                        Flame flame = new Flame(Direction.WEST);
                        flame.setPosition(oldX - 32 - 32 * r, oldY);
                        level.addToLevel(flame);
                    } else {
                        checkWall(this.getX(), this.getY());
                        this.x += (16 + 32 * r);
                        break;
                    }
                    this.x += (16 + 32 * r);
                }
                break;
            case SOUTH:
                for (int r = 0; r < range; r++) {
                    this.y += (16 + 32 * r);
                    if (!this.intersectWithWall()) {
                        Flame flame = new Flame(Direction.SOUTH);
                        flame.setPosition(oldX, oldY + 32 + 32 * r);
                        level.addToLevel(flame);
                    } else {
                        checkWall(this.getX(), this.getY()+32);
                        this.y -= (16 + 32 * r);
                        break;
                    }
                    this.y -= (16 + 32 * r);
                }
                break;
            case NORTH:
                for (int r = 0; r < range; r++) {
                    this.y -= (16 + 32 * r);
                    if (!this.intersectWithWall()) {
                        Flame flame = new Flame(Direction.NORTH);
                        flame.setPosition(oldX, oldY - 32 - 32 * r);
                        level.addToLevel(flame);
                    } else {
                        checkWall(this.getX(), this.getY());
                        this.y += (16 + 32 * r);
                        break;
                    }
                    this.y += (16 + 32 * r);
                }
                break;
        }
    }

    public void checkWall(int x, int y) {
        for (int i = 0; i < level.getMap().getWalls().toArray().length; i++) {
            Walls o = (Walls) level.getMap().getWalls().toArray()[i];
            if (((o.getX() / 32) == (x / 32)) && ((o.getY() / 32) == (y / 32)) && (o instanceof Wall)) {
                level.getMap().getWalls().remove(o);
                try {
                    WallInFire wall = new WallInFire();
                    wall.setPosition(o.getX(), o.getY());
                    level.addToLevel(wall);
                } catch (SlickException ex) {
                    
                }
            }
        } 
    }

    /**
     * @return the intersectWithPlayer
     */
    public boolean canIntersectWithPlayer() {
        return intersectWithPlayer;
    }
}
