package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.util.ConverterUtil;

/**
 * Manages infinite loop - provides methods to start and stop infinite loop.
 * Infinite loop can be stopped via direct method or via command object.
 *
 * While running ,periodically updates game state and graphics
 *
 * User: TERRMITh
 * Date: 3.10.13
 * Time: 0:56
 * To change this template use File | Settings | File Templates.
 */
public class AnimationEngine {

    private static int DEFAULT_FPS = 64;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered
    private static final int NO_DELAYS_PER_YIELD = 16;
    /* Number of frames with a delay of 0 ms before the animation thread yields
    to other running threads. */
    private static int MAX_FRAME_SKIPS = 2;   // was 2;
    private final GameEngine game;
    private final GraphicEngine graphics;

    // period between drawing in ns
    private long period;
    // used to stop the animation thread
   // private volatile boolean running = false;
    private SystemCommand cmd;


    public AnimationEngine(SystemCommand cmd, GraphicEngine graphics, GameEngine game){
        this.cmd = cmd;
        this.game = game;
        this.graphics = graphics;
        // compute period in ms and convert to ns
        this.period = (1000 / DEFAULT_FPS) * 1000000L;
        System.out.println("fps: " + DEFAULT_FPS + "; period: " + period + " ms");
    }

    public void start() {
        /* The frames of the animation are drawn inside the while loop. */
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;

        beforeTime = System.nanoTime();

        while(!cmd.isTerminated()) {
            System.out.println("animator " + cmd.isTerminated());
            game.update();
            graphics.update();

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) {   // some time left in this cycle
                try {
                    Thread.sleep(ConverterUtil.nanosToMilis(sleepTime));  // nano -> ms
                } catch(InterruptedException ex){}
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else {    // sleepTime <= 0; the frame took longer than the period
                excess -= sleepTime;  // store excess time value
                overSleepTime = 0L;

                if (++noDelays >= NO_DELAYS_PER_YIELD) {
                    Thread.yield();   // give another thread a chance to start
                    noDelays = 0;
                }
            }

            beforeTime = System.nanoTime();

      /* If frame animation is taking too long, update the game state
         without rendering it, to get the updates/sec nearer to
         the required FPS. */
            int skips = 0;
            while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
                excess -= period;
                game.update();    // update state but don't render
                skips++;
            }
        }

    }
}
