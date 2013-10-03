package cz.terrmith.randomverse.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key listener that sets user command objects
 * with commands according to keyboard binding.
 *
 *
 *
 * User: TERRMITh
 * Date: 3.10.13
 * Time: 1:02
 * To change this template use File | Settings | File Templates.
 */
public class InputHandler implements KeyListener {

    private final SystemCommand ctrl;

    public InputHandler(SystemCommand ctrl){
        this.ctrl = ctrl;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        processCommand(keyCode, true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
       processCommand(keyCode, false);
    }

    private void processCommand(int keyCode, boolean value){
       //toggle buttons
        if ((keyCode == KeyEvent.VK_ESCAPE) && value){
            ctrl.setPreviousScreen(value);
            ctrl.setTerminated(value);
            System.out.println("terminate set to: " + value);
        }else if (keyCode == KeyEvent.VK_Q && value){
            ctrl.setInventoryShown(ctrl.isInventoryShown());

        //press buttons
        }else if (keyCode == KeyEvent.VK_W){
            ctrl.setUp(value);
        }else if (keyCode == KeyEvent.VK_S){
            ctrl.setDown(value);
        }else if (keyCode == KeyEvent.VK_A){
            ctrl.setLeft(value);
        }else if (keyCode == KeyEvent.VK_D){
            ctrl.setRight(value);
        }else if (keyCode == KeyEvent.VK_SPACE){
            ctrl.setShoot(value);
        }
    }
}
