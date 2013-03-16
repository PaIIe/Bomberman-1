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
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Flame extends Actors{
    private int time;
    private Level level = Level.getLevel();
    
    public Flame(Direction direction) throws SlickException{
        if(direction==Direction.NORTH || direction==Direction.SOUTH){
        animation = new Animation(Anim.getAnimation("resources/actors/flame_ns", 1),20);
        }
        else{
        animation = new Animation(Anim.getAnimation("resources/actors/flame_we", 1),20);
        }
        time=50;
    }

    @Override
    public void act() {
         for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
            if(o instanceof Items && ((Items)o).isVisible()){
                level.removeFromLevel(o);
            }
            
            
        }
        if(time==0){
            level.getListOfObjects().remove(this);
        }
        else{
            time--;
        }
    }
    
}
