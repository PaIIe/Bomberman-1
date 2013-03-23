/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Anim;
import game.Level;
import game.MapObjects;
import game.Statistics;
import game.item.BombsUp;
import game.item.RangeUp;
import game.item.SpeedUp;
import game.map.Portal;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Player extends Actors {

    private Animation leftAnimation;
    private Animation rightAnimation;
    private Animation downAnimation;
    private Animation upAnimation;
    private Animation puttingLeft;
    private Animation puttingRight;
    private Animation puttingDown;
    private Animation puttingUp;
    private Direction direction;
    private int bombsCount;
    private int range;
    private int speed;
    private int speedTime;
    private boolean isAlive = true;
    private boolean puttingBomb = false;
    private int puttingTime = 0;
    private Level level = Level.getLevel();
    private Statistics stat = Statistics.getStatistics();
    private Input input = new Input(480);
    private boolean pause = false;

    public Player() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_w", 3), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_e", 3), 250);
        downAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_s", 3), 250);
        upAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_n", 3), 250);
        puttingLeft = new Animation(Anim.getAnimation("resources/player/putbomb_w", 4), 100);
        puttingRight = new Animation(Anim.getAnimation("resources/player/putbomb_e", 4), 100);
        puttingUp = new Animation(Anim.getAnimation("resources/player/putbomb_n", 4), 100);
        puttingDown = new Animation(Anim.getAnimation("resources/player/putbomb_s", 4), 100);
        super.animation = this.downAnimation;
        direction = Direction.SOUTH;
        super.animation.stop();
        bombsCount = 1;
        range = 1;
        speed = 1;
        speedTime = 0;
    }

    @Override
    public void act() {
        if (puttingTime > 0) {
            puttingTime--;
            if (puttingTime == 0) {
                Bombs bomba;
                try {
                    bomba = new Bombs();
                    level.addToLevel(bomba);
                    bomba.setPosition((this.getX() + 15) / 32 * 32, (this.getY() + 15) / 32 * 32);
                } catch (SlickException ex) {
                }
                puttingBomb = false;
            }
        }
        if (speedTime > 0) {
            speedTime--;
            if (speedTime == 0) {
                speed--;
            }
        }
        putBomb();
        walk();
        if (!isAlive) {
            System.exit(0);
        }
    }

    public void putBomb() {
        if (input.isKeyDown(Input.KEY_SPACE) && puttingBomb == false) {
            if (bombsCount > 0) {
                bombsCount--;
                puttingBomb = true;
                puttingTime = 30;
                switch (direction) {
                    case NORTH:
                        animation = puttingUp;
                        animation.restart();
                        break;
                    case SOUTH:
                        animation = puttingDown;
                        animation.restart();
                        break;
                    case EAST:
                        animation = puttingRight;
                        animation.restart();
                        break;
                    case WEST:
                        animation = puttingLeft;
                        animation.restart();
                        break;
                }
            }
        }
    }

    public void walk() {
        int hracX = this.getX();
        int hracY = this.getY();
        if (!puttingBomb) {
            if (input.isKeyDown(Input.KEY_LEFT)) {
                this.animation = this.leftAnimation;
                direction = Direction.WEST;
                this.animation.start();
                this.x -= speed;
            } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                this.animation = this.rightAnimation;
                direction = Direction.EAST;
                this.animation.start();
                this.x += speed;
            } else if (input.isKeyDown(Input.KEY_DOWN)) {
                this.animation = this.downAnimation;
                direction = Direction.SOUTH;
                this.animation.start();
                this.y += speed;
            } else if (input.isKeyDown(Input.KEY_UP)) {
                this.animation = this.upAnimation;
                direction = Direction.NORTH;
                this.animation.start();
                this.y -= speed;
            } else {
                this.animation.setCurrentFrame(0);
                this.animation.stop();
            }
        }

        for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
            if (o.intersects(this)) {
                if (o instanceof Portal){
                    this.setPause(true);
                }
                if (o instanceof Enemies || o instanceof Flame) {
                    this.isAlive = false;
                }
                if (o instanceof Bombs) {
                    if (!((Bombs) o).canIntersectWithPlayer()) {
                        this.setPosition(hracX, hracY);
                    }
                }
                if (o instanceof BombsUp) {
                    level.getListOfObjects().remove(o);
                    bombsCount++;
                    stat.incItemsUsed();
                }
                if (o instanceof RangeUp) {
                    level.getListOfObjects().remove(o);
                    range++;
                    stat.incItemsUsed();
                }
                if (o instanceof SpeedUp) {
                    level.getListOfObjects().remove(o);
                    speed++;
                    speedTime = 1000;
                    stat.incItemsUsed();
                }
            }
        }

        if (this.intersectWithWall()) {
            this.setPosition(hracX, hracY);
        }
    }

    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    public void setBomb() {
        bombsCount++;
    }

    /**
     * @return the pause
     */
    public boolean isPause() {
        return pause;
    }

    /**
     * @param pause the pause to set
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
