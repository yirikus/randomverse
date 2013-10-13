package cz.terrmith.randomverse.core.input;

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

    private final Command ctrl;

    public InputHandler(Command ctrl){
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
            ctrl.setPrevious(value);
        }else if (keyCode == KeyEvent.VK_Q && value){
            ctrl.setInventory(value);

        //press buttons
        }else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){
            ctrl.setUp(value);
        }else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){
            ctrl.setDown(value);
        }else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT){
            ctrl.setLeft(value);
        }else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT){
            ctrl.setRight(value);
        }else if (keyCode == KeyEvent.VK_CONTROL){
            ctrl.setShoot(value);
        }
    }
}
