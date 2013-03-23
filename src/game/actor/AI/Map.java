/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor.AI;

import game.Level;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author Michal
 */
public class Map implements TileBasedMap {
   private Level level = Level.getLevel();
   private TiledMap mapa = level.getMap().getTiledMap();
   
   public Map(){
       
   }
   
    @Override
    public int getWidthInTiles() {
        return mapa.getWidth();
    }

    @Override
    public int getHeightInTiles() {
        return mapa.getHeight();
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        
    }

    @Override
    public boolean blocked(PathFindingContext context, int tx, int ty) {
        return level.getMap().getWallMap()[tx][ty]==1;
    }

    @Override
    public float getCost(PathFindingContext context, int tx, int ty) {
        return 1.0f;
    }
    
}
