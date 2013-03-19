/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Michal
 */
public abstract class Actors extends MapObjects {

    public enum Direction{
        NORTH,
        WEST,
        EAST, 
        SOUTH
    }
    
    private Level level = Level.getLevel();
    
    public abstract void act();
    
    public boolean intersectWithWall() {
        for(Walls w: level.getMap().getWalls()){
            if(this.intersects(w)){
                return true;
            }
            
        }
        return false;
    }
}
