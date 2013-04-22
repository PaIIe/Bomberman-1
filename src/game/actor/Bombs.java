/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Anim;
import game.Level;
import game.MapObjects;
import game.item.Items;
import game.map.Wall;
import game.map.Walls;
import game.score.Score;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Bombs extends Actors {

    private int explodeTime;
    private Animation explodingBomb;
    private boolean exploded;
    private Level level = Level.getLevel();
    private Score stat = Score.getScore();
    private boolean intersectWithPlayer;
    private final static int STEP = 2;
    private int steps;
    private Direction direction;

    public Bombs() throws SlickException {
        animation = new Animation(Anim.getAnimation("resources/actors/bomb", 2), 200);
        explodingBomb = new Animation(Anim.getAnimation("resources/actors/expbomb", 1), 20);
        explodeTime = 200;
        exploded = false;
        intersectWithPlayer = true;
        steps = 0;

    }

    @Override
    public void act() {
        if (!level.getPlayer().intersects(this)) {
            this.intersectWithPlayer = false;
        }
        if (steps > 0) {
            for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
                MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
                if (o instanceof Enemies && o.intersects(this)) {
                    steps=0;
                    setExplodeTime(0);
                }
            }
            switch (direction) {
                case EAST:
                    this.x += STEP;
                    break;
                
                case WEST:
                    this.x -= STEP;
                    break;

                case NORTH:
                    this.y -= STEP;
                    break;

                case SOUTH:
                    this.y += STEP;
                    break;
            }
            steps--;
        } else if (!exploded) {
            if (explodeTime == 0) {
                animation = explodingBomb;
                exploded = true;
                setExplodeTime(50);
                try {
                    createFlames();
                } catch (SlickException ex) {
                    ex.printStackTrace();
                }
            } else {
                explodeTime--;
            }
        } else {
            if (explodeTime == 0) {
                level.getMap().getWallMap()[this.getX() / 32][this.getY() / 32] = 0;
                level.getListOfObjects().remove(this);
                level.getPlayer().incBomb();
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
                        checkWall(this.getX() + 32, this.getY());
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
                        checkWall(this.getX(), this.getY() + 32);
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
                level.getMap().getWallMap()[o.getX() / 32][o.getY() / 32] = 0;
                stat.incWallsDestroyed();
                checkItem(o.getX(), o.getY());
                try {
                    WallInFire wall = new WallInFire();
                    wall.setPosition(o.getX(), o.getY());
                    level.addToLevel(wall);
                } catch (SlickException ex) {
                    System.out.println("SlickException - WallInFire creating fail");
                }
            }
        }
    }

    public void checkItem(int x, int y) {
        for (int i = 0; i < level.getListOfObjects().toArray().length; i++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[i];
            if (o instanceof Items && o.getX() == x && o.getY() == y) {
                ((Items) o).setVisible(true);
            }
        }
    }

    @Override
    public boolean intersects(MapObjects actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX() + 6, actor.getY() + 6, 18, 18);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX() + 2, this.getY() + 2, this.animation.getWidth() - 4, this.animation.getHeight() - 4);
        return objekt.intersects(predmet);
    }

    /**
     * @return the intersectWithPlayer
     */
    public boolean canIntersectWithPlayer() {
        return intersectWithPlayer;
    }

    public void setX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y += y;
    }

    public void makeMove(Direction direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    /**
     * @param explodeTime the explodeTime to set
     */
    public void setExplodeTime(int explodeTime) {
        this.explodeTime = explodeTime;
    }
}
