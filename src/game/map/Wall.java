/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public class Wall extends Walls {
 
    public Wall() throws SlickException{
        animation = new Animation(Anim.getAnimation("resources/map/wall", 1),200);
    }
    
}
