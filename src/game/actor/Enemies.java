/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Actors;
import game.Level;
import game.MapObjects;
import game.item.Items;
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
    
    public abstract void changeDirection();
    
}
