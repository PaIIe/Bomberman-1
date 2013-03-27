/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.actor.Player;

/**
 *
 * @author Michal
 */
public class Statistics {
    private Level level = Level.getLevel();
    private static final Statistics instance = new Statistics();
    private Player hrac;
    private int enemiesKilled;
    private int wallsDestroyed;
    private int itemsUsed;
    private int playingTime;
    private long startTime;
    
    
   private Statistics(){
       hrac = level.getPlayer();
       enemiesKilled = 0;
       wallsDestroyed = 0;
       itemsUsed = 0;
       startTime = System.currentTimeMillis();
       
   } 
   
   public void restart(){
       enemiesKilled = 0;
       wallsDestroyed = 0;
       itemsUsed = 0;
       startTime = System.currentTimeMillis();
   }
   
    public static Statistics getStatistics(){
        return instance;
    }
    
    public void incWallsDestroyed(){
        wallsDestroyed++;
    }
    
    public void incEnemiesKilled(){
        enemiesKilled++;
    }

    public void incItemsUsed(){
        itemsUsed++;
    }
    
    /**
     * @return the playingTime
     */
    public int getPlayingTime() {
        return (int)(System.currentTimeMillis() - startTime)/1000;
    }

    /**
     * @return the enemiesKilled
     */
    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    /**
     * @return the wallsDestroyed
     */
    public int getWallsDestroyed() {
        return wallsDestroyed;
    }
}
