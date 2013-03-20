/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Actors;
import game.Level;
import game.MapObjects;
import game.item.Items;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;

/**
 *
 * @author Michal
 */
public abstract class Enemies extends Actors{
    protected Animation leftAnimation;
    protected Animation rightAnimation;
    protected Direction direction;
    protected Level level = Level.getLevel();
   
    @Override
    public void act() {
        if (this.intersectWithWall()){
            changeDirection();
        }
       for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
            if (o.intersects(this)) {
                if (o instanceof Flame) {
                    level.getListOfObjects().remove(this);
                }
                if((o instanceof Bombs) || (o instanceof Items)){
                    changeDirection();
                }
            }
        }
    }
    
     @Override
    public boolean intersects(MapObjects actor) {    
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+6, actor.getY()+6, 18, 18);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX()+2, this.getY()+2, this.animation.getWidth()-4, this.animation.getHeight()-4);
        return objekt.intersects(predmet);                    
    }
    
    public abstract void changeDirection();
    
}
