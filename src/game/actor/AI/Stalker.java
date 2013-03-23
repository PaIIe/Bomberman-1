/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.AI;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

/**
 *
 * @author Michal
 */
public class Stalker {
    private PathFinder pathFinder;
    private Map map;
    private Path path;
    
    public Stalker(){
        map = new Map();
        pathFinder = new AStarPathFinder(map,100,false);
        
    }
    
    public Path getPath(Mover mover, int sx, int sy, int tx, int ty){
        path = pathFinder.findPath(mover, sx, sy, tx, ty);
        return path;
    }
}
