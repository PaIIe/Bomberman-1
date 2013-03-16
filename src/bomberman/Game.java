/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;
import game.Actors;
import game.Level;
import game.MapObjects;
import game.actor.Player;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
/**
 *
 * @author Michal
 */
public class Game extends BasicGame {
private Player hrac;
public TextField text;
private Level level;
    
    public Game(){
    super("Bomberman");
}
    
    @Override
    public void init(GameContainer gc) throws SlickException {
      level = Level.getLevel();
      level.loadLevel("level01w");
      hrac = level.getPlayer();      
      TrueTypeFont font = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14), true);
      text = new TextField(gc, font, 150, 245, 450, 40);
      
      
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException { 
        Input input = gc.getInput();
        if( input.isKeyDown(Input.KEY_ESCAPE)) {
            gc.exit();
        }
  //     for (MapObjects o : level.getListOfObjects()) {
  //       ((Actors)o).act();       
  //     } 
        for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
            MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
            if(o instanceof Actors){
                ((Actors)o).act();
            
        }
     //   text.setText("Pocet objektov je:"+map.getMap().);
    }
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        level.show();
        for (MapObjects o : level.getListOfObjects()) {
         o.getAnimation().draw(o.getX(),o.getY());         
       } 
        level.showWalls();
       // text.render(gc, grphcs);
      
    }  

    
}
