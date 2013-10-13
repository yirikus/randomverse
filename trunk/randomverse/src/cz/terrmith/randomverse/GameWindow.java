package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.AnimationEngine;
import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.GraphicEngine;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.input.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;

/**
 * main class
 * User: TERRMITh
 * Date: 3.10.13
 * Time: 0:24
 * To change this template use File | Settings | File Templates.
 */
public class GameWindow extends JFrame implements Runnable{

    public static final int SCREEN_W = 800;
    public static final int SCREEN_H = 600;
    public static final int SCREEN_BIT_DEPTH = 32;
    private int pWidth, pHeight;     // panel dimensions

    private static final int NUM_BUFFERS = 2;    // used for page flipping

    // used for full-screen exclusive mode
    private GraphicsDevice graphicsDevice;

    // used at game termination
    private boolean finishedOff = false;

    private AnimationEngine animationEngine;
    private Thread animator;
    private Command systemCommand;
    /**
     * Constructor - creates new game window in a fullscreen mode
     */
    public GameWindow(){
        super(Randomverse.WINDOW_NAME); // window name
        this.systemCommand = new Command();
        initFullscreen();
        ImageLoader iml =  new ImageLoader("/image_config.txt","/images/");
        GameEngine gameEngine = new Randomverse(systemCommand, SCREEN_W, SCREEN_H);
        GraphicEngine graphicEngine = createGraphicEngine(systemCommand, iml, gameEngine);

        this.animationEngine = new AnimationEngine(systemCommand, graphicEngine, gameEngine);
        hideMouse();
        readyForTermination();
        //initGame();

        addKeyListener(new InputHandler(systemCommand));
        //isPaused=true;
        start();
    }

    /**
     * Main Method
     * @param args
     */
    public static void main(String args[]){
        try{
            new GameWindow();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * initialise and start the thread
     */
    private void start() {
        if (animator == null || systemCommand.isTerminated()) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * This method handles animation
     */
    public void run(){
        //start infinite loop
        animationEngine.start();
        // finish off if loop terminates
        finishOff();
    }

    /**
     * Switches application to fullscreen
     *
     */
    private void initFullscreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();

        setUndecorated(true);    // no menu bar, borders, etc. or Swing components
        setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
        setResizable(false);

        if (!graphicsDevice.isFullScreenSupported()) {
            System.out.println("Full-screen exclusive mode not supported");
            System.exit(0);
        }
        graphicsDevice.setFullScreenWindow(this); // switch on full-screen exclusive mode

        // we can now adjust the display modes, if we wish
        showCurrentMode();

        //setDisplayMode(Global.SCREEN_X, Global.SCREEN_Y, 32);   // or try 8 bits
        //setDisplayMode(1024, 768, 16);
        setDisplayMode(SCREEN_W, SCREEN_H, SCREEN_BIT_DEPTH);

        // reportCapabilities();
    }

    /**
     * print the display mode details for the graphics device
     */
    private void showCurrentMode() {
        DisplayMode displayMode = graphicsDevice.getDisplayMode();
        System.out.println("Current Display Mode: (" +
                displayMode.getWidth() + "," + displayMode.getHeight() + "," +
                displayMode.getBitDepth() + "," + displayMode.getRefreshRate() + ")  ");
    }

    /** Switch on page flipping: NUM_BUFFERS == 2 so
     *  there will be a 'primary surface' and one 'back buffer'.
     *
     *  The use of invokeAndWait() is to avoid a possible deadlock
     *  with the event dispatcher thread. Should be fixed in J2SE 1.5
     *
     *  createBufferStrategy) is an asynchronous operation, so sleep
     *  a bit so that the getBufferStrategy() call will get the
     *  correct details.
     */
    private GraphicEngine createGraphicEngine(Command cmd, ImageLoader iml, GameEngine gameEngine){
        try {
            EventQueue.invokeAndWait(
                    new Runnable() {
                        public void run(){ createBufferStrategy(NUM_BUFFERS);
                        }
                    });
        }catch (Exception e) {
            System.out.println("Error while creating buffer strategy");
            System.exit(0);
        }

        try {  // sleep to give time for the buffer strategy to be carried out
            Thread.sleep(500);  // 0.5 sec
        } catch(InterruptedException ex){}

        return new GraphicEngine(getBufferStrategy(), cmd, getBounds().width, getBounds().height, iml, gameEngine);
    }

    /** attempt to set the display mode to the given width, height, and bit depth
     */
    private void setDisplayMode(int width, int height, int bitDepth){
        if (!graphicsDevice.isDisplayChangeSupported()) {
            System.out.println("Display mode changing not supported");
            return;
        }

        if (!isDisplayModeAvailable(width, height, bitDepth)) {
            System.out.println("Display mode (" + width + "," +
                    height + "," + bitDepth + ") not available");
            return;
        }

        DisplayMode displayMode = new DisplayMode(width, height, bitDepth,
                DisplayMode.REFRESH_RATE_UNKNOWN);   // any refresh rate
        try {
            graphicsDevice.setDisplayMode(displayMode);
            System.out.println("Display mode set to: (" + width + "," +
                    height + "," + bitDepth + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Error setting Display mode (" + width + "," +
                    height + "," + bitDepth + ")");  }

        try {  // sleep to give time for the display to be changed
            Thread.sleep(1000);  // 1 sec
        } catch(InterruptedException ex){}
    }

    /**
     * Check that a displayMode with this width, height, bit depth is available.
     *  We don't care about the refresh rate, which is probably
     *  REFRESH_RATE_UNKNOWN anyway.
     */
    private boolean isDisplayModeAvailable(int width, int height, int bitDepth){
        DisplayMode[] modes = graphicsDevice.getDisplayModes();
        //showModes(modes);

        for(int i = 0; i < modes.length; i++) {
            if (width == modes[i].getWidth()
                    && height == modes[i].getHeight()
                    && bitDepth == modes[i].getBitDepth()) {

                return true;
            }
        }
        return false;
    }

    /**
     * pretty print the display mode information in modes
     */
    private void showModes(DisplayMode[] modes){
        System.out.println("Modes");
        for(int i = 0; i < modes.length; i++) {
            System.out.print("(" + modes[i].getWidth() + "," +
                    modes[i].getHeight() + "," +
                    modes[i].getBitDepth() + "," +
                    modes[i].getRefreshRate() + ")  " );
            if ((i+1)%4 == 0)
                System.out.println();
        }
        System.out.println();
    }

    private void readyForTermination(){
        // for shutdown tasks
        // a shutdown may not only come from the program
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run(){
                systemCommand.setTerminated(true);
                finishOff();
            }
        });
    }

    /**
     *    Tasks to do before terminating. Called at end of start()
     *  and via the shutdown hook in readyForTermination().
     *
     *  The call at the end of start() is not really necessary, but
     *  included for safety. The flag stops the code being called
     *  twice.
     */
    private void finishOff(){
        if (!finishedOff) {
            finishedOff = true;
            //you can print stats here if you need
            restoreScreen();
            System.exit(0);
        }
    }

    /**
     * Switch off full screen mode. This also resets the
     * display mode if it's been changed.
     */
    private void restoreScreen(){
        Window window = graphicsDevice.getFullScreenWindow();
        if (window != null)
            window.dispose();
        graphicsDevice.setFullScreenWindow(null);
    } // end of restoreScreen()

    /**
     *Hides mouse cursor
     *source: http://www.rgagnon.com/javadetails/java-0440.html
     */
    private void hideMouse(){
        int[] pixels = new int[16 * 16];
        Image image = Toolkit.getDefaultToolkit().createImage(
            new MemoryImageSource(16, 16, pixels, 0, 16));
        Cursor transparentCursor =
            Toolkit.getDefaultToolkit().createCustomCursor
                 (image, new Point(0, 0), "invisibleCursor");
        setCursor(transparentCursor);

    }


}
