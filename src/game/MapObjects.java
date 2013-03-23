/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;

/**
 *
 * @author Michal
 */
public abstract class MapObjects implements Objects {
    protected int x;
    protected int y;
    protected Animation animation;   
    
    
    public int getX() {
        return this.x;
    }

    /**
     * ziska y-ovu suradnicu
     * @return vracia hodnotu y-ovej suradnice
     */
    public int getY() {
        return this.y;
    }

    /**
     *  ziska primarnu animaciu
     * @return vracia aktualnu animaciu
     */
    public Animation getAnimation() {
        return this.animation;
    }

    /**
     * nastavi aktualnu animaciu
     * @param animation referenia na animaciu, ktora sa ma nastavit
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    /**
     * ziska sirku animacie
     * @return vracia hodnotu sirky animacie
     */
    public int getWidth() {
        return animation.getWidth();
    }

    /**
     * ziska vysku animacie
     * @return vracia hodnotu vysky animacie
     */
    public int getHeight() {
        return animation.getHeight();
    }
    
    /**
     * nastavi poziciu
     * @param x x-ova suradnica
     * @param y y-ova suradnica
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean intersects(MapObjects actor) {      
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), actor.getAnimation().getWidth(), actor.getAnimation().getHeight());
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), this.animation.getWidth(), this.animation.getHeight());
        return objekt.intersects(predmet);
    }

}
