/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import bomberman.Game.GameState;
import game.Anim;
import game.Level;
import game.MapObjects;
import game.score.Score;
import game.item.BombsUp;
import game.item.KickUp;
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
    private Animation speedLeftAnimation;
    private Animation speedRightAnimation;
    private Animation speedDownAnimation;
    private Animation speedUpAnimation;
    private Animation puttingLeft;
    private Animation puttingRight;
    private Animation puttingDown;
    private Animation puttingUp;
    private Animation celebrating;
    private Animation dying;
    private Direction direction;
    private int bombsCount;
    private int range;
    private int speed;
    private int speedTime;
    private int kickTime;
    private boolean puttingBomb = false;
    private int puttingTime = 0;
    private boolean stopTime;
    private boolean ghostMode;
    private Level level = Level.getLevel();
    private Score stat = Score.getScore();
    private Input input = new Input(480);

    public Player() throws SlickException {
        leftAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_w", 3), 250);
        rightAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_e", 3), 250);
        downAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_s", 3), 250);
        upAnimation = new Animation(Anim.getAnimation("resources/player/Bomberman_n", 3), 250);
        speedLeftAnimation = new Animation(Anim.getAnimation("resources/player/speedy_w", 1), 250);
        speedRightAnimation = new Animation(Anim.getAnimation("resources/player/speedy_e", 1), 250);
        speedDownAnimation = new Animation(Anim.getAnimation("resources/player/speedy_s", 1), 250);
        speedUpAnimation = new Animation(Anim.getAnimation("resources/player/speedy_n", 1), 250);
        puttingLeft = new Animation(Anim.getAnimation("resources/player/putbomb_w", 4), 100);
        puttingRight = new Animation(Anim.getAnimation("resources/player/putbomb_e", 4), 100);
        puttingUp = new Animation(Anim.getAnimation("resources/player/putbomb_n", 4), 100);
        puttingDown = new Animation(Anim.getAnimation("resources/player/putbomb_s", 4), 100);
        celebrating = new Animation(Anim.getAnimation("resources/player/celebrating", 8), 300);
        dying = new Animation(Anim.getAnimation("resources/player/dying", 9), 300);
        super.animation = this.downAnimation;
        direction = Direction.SOUTH;
        super.animation.stop();
        bombsCount = 5;
        range = 1;
        speed = 1;
        speedTime = 0;
        kickTime = 0;
        stopTime = false;
        ghostMode = false;
    }

    @Override
    public void act() {
        if(input.isKeyDown(Input.KEY_G)){
            if(!ghostMode){
                ghostMode=true;
            }
            else{
                ghostMode=false;
            }
        }
        if (puttingTime > 0) {
            puttingTime--;
            if (puttingTime == 0) {
                Bombs bomba;
                try {
                    bomba = new Bombs();
                    level.addToLevel(bomba);
                    bomba.setPosition((this.getX() + 15) / 32 * 32, (this.getY() + 15) / 32 * 32);
                    level.getMap().getWallMap()[bomba.getX() / 32][bomba.getY() / 32] = 1;
                } catch (SlickException ex) {
                    ex.printStackTrace();
                }
                puttingBomb = false;
            }
        }
        if (speedTime > 0) {
            speedTime--;
            if (speedTime == 0) {
                speed -= 2;
            }
        }
        if (kickTime > 0) {
            kickTime--;
        }
        putBomb();
        walk();
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
                if (speedTime > 0) {
                    this.animation = this.speedLeftAnimation;
                } else {
                    this.animation = this.leftAnimation;
                }
                direction = Direction.WEST;
                this.animation.start();
                this.x -= speed;
            } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                if (speedTime > 0) {
                    this.animation = this.speedRightAnimation;
                } else {
                    this.animation = this.rightAnimation;
                }
                direction = Direction.EAST;
                this.animation.start();
                this.x += speed;
            } else if (input.isKeyDown(Input.KEY_DOWN)) {
                if (speedTime > 0) {
                    this.animation = this.speedDownAnimation;
                } else {
                    this.animation = this.downAnimation;
                }
                direction = Direction.SOUTH;
                this.animation.start();
                this.y += speed;
            } else if (input.isKeyDown(Input.KEY_UP)) {
                if (speedTime > 0) {
                    this.animation = this.speedUpAnimation;
                } else {
                    this.animation = this.upAnimation;
                }
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
                if (o instanceof Portal) {
                    level.setGameState(GameState.FINISHED);
                    this.animation = celebrating;
                }
                if (o instanceof Enemies || o instanceof Flame) {
                    animation = dying;
                    level.setGameState(GameState.FAILED);
                }
                if (o instanceof Bombs) {
                    if (!((Bombs) o).canIntersectWithPlayer()) {
                        if (kickTime > 0) {
                            kickBomb((Bombs) o);
                            this.setPosition(hracX, hracY);
                        } else {
                            this.setPosition(hracX, hracY);
                        }
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
                    speed += 2;
                    speedTime = 1000;
                    stat.incItemsUsed();
                }
                if (o instanceof KickUp) {
                    level.getListOfObjects().remove(o);
                    kickTime = 2000;
                    stat.incItemsUsed();
                }
            }
        }

        if (this.intersectWithWall()) {
            if(!ghostMode){
            this.setPosition(hracX, hracY);
            }
        }
    }

    public void kickBomb(Bombs bomb) {
        int r=1;
        switch (direction) {          
            case EAST:
                bomb.setX(32 * r);
                while (!bomb.intersectWithWall()) {
                    bomb.setX(-(32 * r));
                    r++;
                    bomb.setX(32 * r);
                }
                bomb.setX(-(32 * r));
                bomb.makeMove(direction, (r-1)*16);
                break;

            case WEST:
                bomb.setX(-(32 * r));
                while (!bomb.intersectWithWall()) {
                    bomb.setX(32 * r);
                    r++;
                    bomb.setX(-(32 * r));
                }
                bomb.setX(32 * r);
                bomb.makeMove(direction, (r-1)*16);
                break;
            case SOUTH:
                bomb.setY(32 * r);
                while (!bomb.intersectWithWall()) {
                    bomb.setY(-(32 * r));
                    r++;
                    bomb.setY(32 * r);
                }
                bomb.setY(-(32 * r));
                bomb.makeMove(direction, (r-1)*16);
                break;
            case NORTH:
                bomb.setY(-(32 * r));
                while (!bomb.intersectWithWall()) {
                    bomb.setY(32 * r);
                    r++;
                    bomb.setY(-(32 * r));
                }
                bomb.setY(32 * r);
                bomb.makeMove(direction, (r-1)*16);
                break;
        }
    }

/**
 * @return the range
 */
public int getRange() {
        return range;
    }

    public void incBomb() {
        bombsCount++;
    }

    /**
     * @return the stopTime
     */
    public boolean isStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(boolean stopTime) {
        this.stopTime = stopTime;
    }
   
}
