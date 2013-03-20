/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import game.actor.Actors;
import game.Level;
import game.MapObjects;
import game.Statistics;
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
    private TextField text;
    private Level level;
    private long playingTime;
    private long startTime;
    private Statistics stat;

    public Game() {
        super("Bomberman");
        stat = Statistics.getStatistics();
      //  startTime = System.currentTimeMillis();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        level = Level.getLevel();
        level.loadLevel("level01w");
        hrac = level.getPlayer();
        TrueTypeFont font = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14), true);
        text = new TextField(gc, font, 150, 200, 200, 40);

    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            gc.exit();
        }
        if (input.isKeyPressed(Input.KEY_P)) {
            if (!level.getPlayer().isPause()) {
                level.getPlayer().setPause(true);
            } else {
                level.getPlayer().setPause(false);
            }
            playingTime = (System.currentTimeMillis() - startTime) / 1000;
       //     text.setText("Actual playing time is: " + playingTime);
        }

        if (!level.getPlayer().isPause()) {
            for (int x = 0; x < level.getListOfObjects().toArray().length; x++) {
                MapObjects o = (MapObjects) level.getListOfObjects().toArray()[x];
                if (o instanceof Actors) {
                    ((Actors) o).act();

                }
            }

        }
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        level.show();
        for (MapObjects o : level.getListOfObjects()) {
            o.getAnimation().draw(o.getX(), o.getY());
        }
        level.showWalls();
        //     text.render(gc, grphcs);
        if (level.getPlayer().isPause()) {
            int minutes = stat.getPlayingTime() / 60;
            int seconds = stat.getPlayingTime() % 60;
            grphcs.drawString("Actual playing time: " + minutes + " min " + seconds + " secs", 180, 8);
       //     grphcs.drawString("Enemies killed: " + stat.getEnemiesKilled(), 180, 30);
       //    grphcs.drawString("Walls Destroyed: " + stat.getWallsDestroyed(), 180, 50);
        }
    }
}
