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
        System.out.println("key typed! " + e.getKeyChar());
        ctrl.setKeyTyped(String.valueOf(e.getKeyChar()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
	    ctrl.setAnyKey(true);
        processCommand(keyCode, true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
	    ctrl.setAnyKey(false);
       processCommand(keyCode, false);
    }

    private void processCommand(int keyCode, boolean value){
       //toggle buttons
        if ((keyCode == KeyEvent.VK_ESCAPE) && value){
            ctrl.setPrevious(value);
        }else if (keyCode == KeyEvent.VK_Q && value){
            ctrl.setInventory(value);

        //press buttons
        }else if (keyCode == KeyEvent.VK_UP){
            ctrl.setUp(value);
        }else if (keyCode == KeyEvent.VK_DOWN){
            ctrl.setDown(value);
        }else if (keyCode == KeyEvent.VK_LEFT){
            ctrl.setLeft(value);
        }else if (keyCode == KeyEvent.VK_RIGHT){
            ctrl.setRight(value);
        }else if (keyCode == KeyEvent.VK_CONTROL){
            ctrl.setAction1(value);
        }else if (keyCode == KeyEvent.VK_SPACE){
            ctrl.setAction2(value);
        }else if (keyCode == KeyEvent.VK_A){
            ctrl.setAction3(value);
        }else if (keyCode == KeyEvent.VK_S){
            ctrl.setAction4(value);
        } else if ((keyCode == KeyEvent.VK_PRINTSCREEN || keyCode == KeyEvent.VK_P) && value) {
            System.out.println("screenshot " + value + ", keyCode: " + keyCode);
            ctrl.setScreenshot(value);
        }


    }
}
