/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.item;

import game.MapObjects;

/**
 *
 * @author Michal
 */
public abstract class Items extends MapObjects {
    
    private boolean visible = false;
    
    public boolean isVisible(){
        return visible;
    }
}
