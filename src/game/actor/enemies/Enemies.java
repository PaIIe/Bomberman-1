/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.enemies;

import game.Level;
import game.MapObjects;
import game.actor.Actors;
import game.actor.Bombs;
import game.actor.Flame;
import game.score.Score;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;

/**
 *
 * @author Michal
 */
public abstract class Enemies extends Actors {

    /**
     *
     */
    protected Animation leftAnimation;
    /**
     *
     */
    protected Animation rightAnimation;
    /**
     *
     */
    protected Animation upAnimation;
    /**
     *
     */
    protected Animation downAnimation;
    /**
     *
     */
    protected Direction direction;
    /**
     *
     */
    protected Level level = Level.getLevel();
    private Score stat = Score.getScore();
    /**
     *
     */
    protected int oldX;
    /**
     *
     */
    protected int oldY;
    /**
     *
     */
    protected int steps;

    /**
     * provides basic behaviour for all enemies |
     * if intersects with wall or bomb, has to change direction(implemented in subtype classes) |
     * if intersects with flames, removes itself from level
     */
    @Override
    public void act() {
        if (this.intersectWithWall()) {
            changeDirection();
        }
        for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
            if (o.intersects(this)) {
                if (o instanceof Flame) {                    
                    level.getListOfObjects().remove(this);
                    stat.incEnemiesKilled();
                    }

                if ((o instanceof Bombs)) {
                    changeDirection();
                }
            }
        }
    }

    /**
     * overrides intersect method, specified for this concrete class
     * @param actor
     * @return
     */
    @Override
    public boolean intersects(MapObjects actor) {
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX() + 6, actor.getY() + 6, 18, 18);
        if(this instanceof Dragon){
        Rectangle2D objekt = new Rectangle2D.Float(this.getX() + 2, this.getY() + 2, 28, 28);    
        return objekt.intersects(predmet);
        }
        else{
        Rectangle2D objekt = new Rectangle2D.Float(this.getX() + 2, this.getY() + 2, this.animation.getWidth() - 4, this.animation.getHeight() - 4);
        return objekt.intersects(predmet);
        }
        
    }

    /**
     *
     */
    public abstract void changeDirection();
}
