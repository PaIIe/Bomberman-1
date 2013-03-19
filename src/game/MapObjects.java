/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.actor.Bombs;
import game.actor.Blob;
import game.actor.Flame;
import game.actor.Player;
import game.item.Items;
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
   /*     if(this instanceof Player){
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+4, actor.getY()+4, 24, 24);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), this.animation.getWidth(), this.animation.getHeight());
        return objekt.intersects(predmet);
        }
     */   
        if(actor instanceof Items || actor instanceof Blob){
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX()+6, actor.getY()+6, 18, 18);
        Rectangle2D objekt = new Rectangle2D.Float(this.getX()+4, this.getY()+4, this.animation.getWidth()-8, this.animation.getHeight()-8);
        return objekt.intersects(predmet);
        }               
        
        if(actor instanceof Walls){
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), 32, 32);    
        Rectangle2D objekt = new Rectangle2D.Float(this.getX()+4, this.getY()+4, this.animation.getWidth()-8, this.animation.getHeight()-8);
        return objekt.intersects(predmet);       
        }
       
        Rectangle2D predmet = new Rectangle2D.Float(actor.getX(), actor.getY(), actor.getAnimation().getWidth(), actor.getAnimation().getHeight());
        Rectangle2D objekt = new Rectangle2D.Float(this.getX(), this.getY(), this.animation.getWidth(), this.animation.getHeight());
        return objekt.intersects(predmet);
    }

}
