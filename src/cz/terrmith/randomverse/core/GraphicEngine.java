package cz.terrmith.randomverse.core;

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

    private Graphics graphics;
    private BufferStrategy bufferStrategy;
    private SystemCommand cmd;
    private int width;
    private int height;

    public GraphicEngine(BufferStrategy bufferStrategy, SystemCommand cmd, int width, int height){
       this.bufferStrategy = bufferStrategy;
        this.cmd = cmd;
        this.width = width;
        this.height = height;
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
    }
}
