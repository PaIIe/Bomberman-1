/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Michal
 */
public abstract class Anim {
    
  public static Image[] getAnimation(String pattern, int frames) throws SlickException{
        Image[] images = new Image[frames];
        for(int i=1; i<=frames; i++){         
            images[i-1] = new Image(pattern+0+i+".png");
        }
        return images;
    }
    
}
