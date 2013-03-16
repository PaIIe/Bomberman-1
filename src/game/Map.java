/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.actor.Enemy;
import game.actor.Player;
import game.item.BombsUp;
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
 //   private Walls[][] arrayOfWalls;
    private List<Walls> Walls;
   // private private List<MapObjects> objects = Level.getListOfObjects();

    public Map(Level level) {
     this.level = level;
    }

    public void loadMap(String level) {
        try {
            mapa = new TiledMap("resources/map/" + level + ".tmx");
          //  arrayOfWalls = new Walls[(mapa.getWidth())][];
            Walls = new ArrayList<Walls>();
//            for(int x=0; x < mapa.getWidth(); x++){
      //      arrayOfWalls[x] = new Walls[mapa.getHeight()];
      //      }
            processMap();
        } catch (SlickException ex) {
            System.out.println("Chyba pri nacitani mapy");;
        }
    }
    
    private void processMap() throws SlickException{
        // detect all objects in map
        for(int i = 0; i < mapa.getObjectCount(0); i++){
            switch(mapa.getObjectType(0, i)){
                case "player": 
                    Player hrac = new Player();
                    hrac.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(hrac);
                    level.setPlayer(hrac);
                    break;
                case "enemy": 
                    Enemy enemy = new Enemy();
                    enemy.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(enemy);
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
                case "range": 
                    RangeUp range = new RangeUp();
                    range.setPosition(mapa.getObjectX(0, i), mapa.getObjectY(0, i));
                    level.addToLevel(range);
                    break;     
                    
            }
        }
   // detect all walls in map     
        int wallX;
        int wallY;
        for(int i = 0; i < mapa.getObjectCount(1); i++){
            wallX=mapa.getObjectX(1, i);
            wallY=mapa.getObjectY(1, i);
            switch(mapa.getObjectType(1, i)){
                case "wall": Wall stena = new Wall();
             //       arrayOfWalls[wallX][wallY] = stena;
                    Walls.add(stena);
                    stena.setPosition(wallX, wallY);
                    break;
                    
                case "block": Block blok = new Block();
            //        arrayOfWalls[wallX][wallY] = blok;
                    Walls.add(blok);
                    blok.setPosition(wallX, wallY);
                    break;
            }
        }
    }
    
    public TiledMap getTiledMap(){
        return this.mapa;
    }
    
    public List<Walls> getWalls(){
        return Walls;
    }
}
