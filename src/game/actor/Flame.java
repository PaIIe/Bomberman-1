/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Anim;
import game.Level;
import game.MapObjects;
import game.item.Items;
import game.map.Portal;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Flame extends Actors{
    private int time;
    private Level level = Level.getLevel();
    private boolean broomsMode = false;
    private int portalX;
    private int portalY;
    
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
            if(o.intersects(this) && o instanceof Items && ((Items)o).isVisible() && !(o instanceof Portal)){
                level.removeFromLevel(o);
            }
            if(o instanceof Portal && o.intersects(this) && ((Portal)o).isVisible()){
                broomsMode = true;
                portalX = o.getX();
                portalY = o.getY();
            }
        }
        if(time==0){
            level.getListOfObjects().remove(this);
            if(broomsMode){
                for(int i=0; i<5; i++){
                    try {
                        Broom broom = new Broom();
                        broom.setPosition(portalX, portalY);
                        level.addToLevel(broom);
                    } catch (SlickException ex) {
                        ex.printStackTrace();
                    }
                }
                broomsMode=false;
            }
        }
        else{
            time--;
        }
    }
    
    @Override
    public boolean intersects(MapObjects actor) {    
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+6, actor.getY()+6, 18, 18);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX()+2, this.getY()+2, this.animation.getWidth()-4, this.animation.getHeight()-4);
        return objekt.intersects(predmet);                    
    }
    
}
