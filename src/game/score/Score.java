/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.score;

import game.Level;
import game.actor.Player;

/**
 *
 * @author Michal
 */
public class Score implements Comparable<Score> {

    private Level level = Level.getLevel();
    private static final Score instance = new Score();
    private Player hrac;
    private int enemiesKilled;
    private int wallsDestroyed;
    private int itemsUsed;
    private int playingTime;
    private long startTime;
    private String name;

    private Score() {
        hrac = level.getPlayer();
        enemiesKilled = 0;
        wallsDestroyed = 0;
        itemsUsed = 0;
        startTime = System.currentTimeMillis();
        name = System.getProperty("user.name");
    }

    public void loadData(String name, int time, int enemies, int walls, int items) {
        this.name = name;
        this.playingTime = time;
        this.enemiesKilled = enemies;
        this.wallsDestroyed = walls;
        this.itemsUsed = items;
    }

    public void restart() {
        enemiesKilled = 0;
        wallsDestroyed = 0;
        itemsUsed = 0;
        startTime = System.currentTimeMillis();
    }

    public static Score getScore() {
        return instance;
    }

    public void incWallsDestroyed() {
        wallsDestroyed++;
    }

    public void incEnemiesKilled() {
        enemiesKilled++;
    }

    public void incItemsUsed() {
        itemsUsed++;
    }

    /**
     * @return the playingTime
     */
    public int getPlayingTime() {
        return (int) (System.currentTimeMillis() - startTime) / 1000;
    }
    
    public int PlayingTime() {
        return playingTime;
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

    /**
     * @return the itemsUsed
     */
    public int getItemsUsed() {
        return itemsUsed;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Score o) {
        if (o.getPlayingTime() > o.getPlayingTime()) {
            return 1;
        } else if (o.getPlayingTime() < o.getPlayingTime()) {
            return -1;
        } else {
            if (o.getEnemiesKilled() > o.getEnemiesKilled()) {
                return -1;
            } else if (o.getEnemiesKilled() < o.getEnemiesKilled()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}