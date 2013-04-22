/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.Anim;
import game.Level;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class WallInFire extends Actors{ 
    private int time;
    Level level = Level.getLevel();
    
    public WallInFire() throws SlickException{
        switch(level.getLevelNumber()){
            case 1: animation = new Animation(Anim.getAnimation("resources/map/wallf", 5),180);
                break;
            case 2: animation = new Animation(Anim.getAnimation("resources/map/wallbf", 5),180);
                break;
            default: animation = new Animation(Anim.getAnimation("resources/map/wallf", 5),180);
                
        }
        time=50;
    }
    
    @Override
    public void act() {
        if(time==0){
            level.removeFromLevel(this);
        }
        else {
            time--;
        }
    }
    
}
