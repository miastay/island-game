import java.awt.KeyEventDispatcher; 
import java.awt.KeyboardFocusManager;
import java.util.List;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyboardListener {
    Map<Integer, Boolean> keysDown = new HashMap<>();
    public List<Integer> activeKeys = new ArrayList<>();
    
    void StartKeyListener(){
        for(int i = 0; i < activeKeys.size(); i++){
            keysDown.put(activeKeys.get(i), false);
        }
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                synchronized (KeyboardListener.class) {
                    if(activeKeys.contains(e.getKeyCode())){
                        switch(e.getID()){
                            case KeyEvent.KEY_PRESSED:
                                keysDown.put(e.getKeyCode(), true);
                                return true;
                            case KeyEvent.KEY_RELEASED:
                                keysDown.put(e.getKeyCode(), false);
                                return true;
                        }
                    }
                    return false;
                }
            }
        });
    }
    
    public void addKey(int keyCode){
        activeKeys.add(keyCode);
        keysDown.put(keyCode, false);
    }
    
    public void removeKey(int keyCode) {
    	activeKeys.remove(keyCode);
    	keysDown.remove(keyCode);
    }
    
    public boolean getKey(int keyCode){
        return keysDown.get(keyCode);
    }
}
