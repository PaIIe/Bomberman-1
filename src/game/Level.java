/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.map.Map;
import game.map.Walls;
import game.map.Wall;
import game.actor.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michal
 */
public class Level {

    private ArrayList<MapObjects> listOfObjects;
    private static final Level instance = new Level();
    private Player player;
    private Map mapa = new Map(this);

    private Level() {
        listOfObjects = new ArrayList<MapObjects>();
    }

    public static Level getLevel() {
        return instance;
    }

    public void loadLevel(String level) {
        mapa.loadMap(level);
    }

    public void show() {
        mapa.getTiledMap().render(0, 0);
        /*    for(int y=0; y < mapa.getMap().getHeight(); y++){
         for(int x=0; y < mapa.getMap().getWidth(); x++){
         if(mapa.getWalls()[x][y] instanceof Wall){
         mapa.getWalls()[x][y].getAnimation().draw(mapa.getWalls()[x][y].getX(),mapa.getWalls()[x][y].getY());
         }
         }
         } */
    }

    public void showWalls() {
        for (Walls w : mapa.getWalls()) {
            if (w instanceof Wall) {
                w.getAnimation().draw(w.getX(), w.getY());
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addToLevel(MapObjects o) {
        listOfObjects.add(o);
    }

    public void removeFromLevel(MapObjects o) {
        listOfObjects.remove(o);
    }

    public List<MapObjects> getListOfObjects() {
        return listOfObjects;
    }

    public Map getMap() {
        return mapa;
    }
}