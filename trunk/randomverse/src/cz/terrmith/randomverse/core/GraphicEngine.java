package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 3.10.13
 * Time: 2:20
 * To change this template use File | Settings | File Templates.
 */
public class GraphicEngine {

    private final GameEngine gameEngine;
    private BufferStrategy bufferStrategy;
    private final Command cmd;
    private final int width;
    private final int height;
    private final ImageLoader iml;

    public GraphicEngine(BufferStrategy bufferStrategy, Command cmd, int width, int height, ImageLoader iml, GameEngine gameEngine){
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
            Graphics graphics = bufferStrategy.getDrawGraphics();
            gameRender(graphics);
            if(cmd.isScreenshot()){
                saveScreenshot();
                cmd.setScreenshot(false);
            }
            graphics.dispose();
            if (!bufferStrategy.contentsLost()) {
                bufferStrategy.show();
            } else {
                System.out.println("Contents Lost");
            }
            // Sync the display on some systems.
            // (on Linux, this fixes event queue problems)
            Toolkit.getDefaultToolkit().sync();
        } catch (Exception e) {
            e.printStackTrace();
            cmd.setTerminated(true);
        }
    }  // end of screenUpdate()

    private void saveScreenshot() {
            try {
                System.out.println("saving screenshot "+System.currentTimeMillis());
                // retrieve image  \
                BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
                Graphics g = bufferedImage.createGraphics();
                gameRender(g);
                File outputfile = new File("C:/saved_" + System.currentTimeMillis() + ".png");
                ImageIO.write(bufferedImage, "png", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

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
        g2.setColor(Color.black);
        g2.fillRect(0, 0, width, height);

        // render sprites
        SpriteCollection sprites = gameEngine.getSpriteCollection();
        sprites.drawLayer(SpriteLayer.BACKGROUND, g2, iml);
        sprites.drawLayer(SpriteLayer.NPC, g2, iml);
        sprites.drawLayer(SpriteLayer.PROJECTILE, g2, iml);
        sprites.drawLayer(SpriteLayer.PLAYER, g2, iml);

        gameEngine.drawGUI(g2, iml);
    }
}
