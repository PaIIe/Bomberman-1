/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.newdawn.slick.Animation;

/**
 *
 * @author Michal
 */
public interface Objects {
    
    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();

    public Animation getAnimation();

    public void setAnimation(Animation anmtn);
    
    public void setPosition(int i, int i1);
    
}
