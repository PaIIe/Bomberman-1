/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.Level;
import game.actor.Blob;
import game.actor.Broom;
import game.actor.Cron;
import game.actor.Player;
import game.item.BombsUp;
import game.item.KickUp;
import game.item.RangeUp;
import game.item.SpeedUp;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Michal
 */
public class Map {

    private TiledMap mapa;
    private Level level = Level.getLevel();
    private List<Walls> Walls;
    private int[][] wallMap;

    public Map(Level level) {
        this.level = level;
    }

    public void loadMap(String level) {
        try {
            mapa = new TiledMap("resources/map/" + level + ".tmx");
            Walls = new ArrayList<Walls>();
            wallMap=new int[mapa.getWidth()][];
            for(int i = 0; i< mapa.getWidth(); i++){
                wallMap[i]=new int[mapa.getHeight()];
            }
            processMap();
        } catch (SlickException ex) {
            System.out.println("Chyba pri nacitani mapy");;
        }
    }

    private void processMap() throws SlickException {
        //vynuluje mapu stien
        for(int x=0; x < mapa.getWidth(); x++){
            for(int y=0; y<mapa.getHeight(); y++){
                wallMap[x][y]=0;
            }
        }
        // detect all objects in map
        for (int i = 0; i < mapa.getObjectCount(0); i++) {
            switch (mapa.getObjectType(0, i)) {
                case "player":
                    Player hrac = new Player();
                    hrac.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(hrac);
                    level.setPlayer(hrac);
                    break;
                case "blob":
                    Blob blob = new Blob();
                    blob.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(blob);
                    break;
                case "cron":
                    Cron cron = new Cron();
                    cron.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(cron);
                    break;
                case "broom":
                    Broom broom = new Broom();
                    broom.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(broom);
                    break;
                case "bomb":
                    BombsUp bomba = new BombsUp();
                    bomba.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(bomba);
                    break;
                case "speed":
                    SpeedUp speed = new SpeedUp();
                    speed.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(speed);
                    break;
                case "kick":
                    KickUp kick = new KickUp();
                    kick.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(kick);
                    break;
                case "range":
                    RangeUp range = new RangeUp();
                    range.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(range);
                    break;
                case "portal":
                    Portal portal = new Portal();
                    portal.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(portal);
                    break;
            }
        }
        // detect all walls in map     
        int wallX;
        int wallY;
        for (int i = 0; i < mapa.getObjectCount(1); i++) {
            wallX = mapa.getObjectX(1, i);
            wallY = mapa.getObjectY(1, i);
            wallMap[wallX/32][wallY/32]=1;
            switch (mapa.getObjectType(1, i)) {
                case "wall":
                    Wall stena = new Wall();
                    Walls.add(stena);
                    stena.setPosition(wallX, wallY);
                    break;

                case "block":
                    Block blok = new Block();
                    Walls.add(blok);
                    blok.setPosition(wallX, wallY);
                    break;
            }
        }
    }

    public TiledMap getTiledMap() {
        return this.mapa;
    }

    public List<Walls> getWalls() {
        return Walls;
    }
    
    public int[][] getWallMap(){
        return wallMap;
    }
}
