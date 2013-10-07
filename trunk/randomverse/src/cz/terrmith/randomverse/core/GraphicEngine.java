package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.SystemCommand;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 3.10.13
 * Time: 2:20
 * To change this template use File | Settings | File Templates.
 */
public class GraphicEngine {

    private final GameEngine gameEngine;
    private Graphics graphics;
    private BufferStrategy bufferStrategy;
    private final SystemCommand cmd;
    private final int width;
    private final int height;
    private final ImageLoader iml;

    public GraphicEngine(BufferStrategy bufferStrategy, SystemCommand cmd, int width, int height, ImageLoader iml, GameEngine gameEngine){
       this.bufferStrategy = bufferStrategy;
        this.cmd = cmd;
        this.width = width;
        this.height = height;
        this.iml = iml;
        this.gameEngine = gameEngine;
    }

    /**
     * Updates graphics
     */
    public void update(){
        // use active rendering
        try {
            graphics = bufferStrategy.getDrawGraphics();
            gameRender(graphics);
            graphics.dispose();
            if (!bufferStrategy.contentsLost())
                bufferStrategy.show();
            else
                System.out.println("Contents Lost");
            // Sync the display on some systems.
            // (on Linux, this fixes event queue problems)
            Toolkit.getDefaultToolkit().sync();
        } catch (Exception e) {
            e.printStackTrace();
            cmd.setTerminated(true);
        }
    }  // end of screenUpdate()

    /**
     * Render images
     * @param graphics
     */
    private void gameRender(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        /*g2.setRenderingHint
            (RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
*/
        // clear the background
        g2.setColor(Color.darkGray);
        g2.drawString("Randomverse",0,0);
        g2.fillRect(0, 0, width, height);

        // render sprites
        SpriteCollection sprites = gameEngine.getSpriteCollection();
        sprites.drawLayer(SpriteLayer.BACKGROUND, g2, iml);
        sprites.drawLayer(SpriteLayer.NPC, g2, iml);
        sprites.drawLayer(SpriteLayer.PARTICLE, g2, iml);
        sprites.drawLayer(SpriteLayer.PLAYER, g2, iml);
    }
}
