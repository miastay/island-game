import java.awt.KeyEventDispatcher; 
import java.awt.KeyboardFocusManager;
import java.util.List;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyboardListener implements Updateable{
    Map<Integer, Boolean> keysDown = new HashMap<>();
    Map<Integer, Boolean> keysDownThisFrame = new HashMap<>();
    public List<Integer> activeKeys = new ArrayList<>();
    List<Integer> keysJustHit = new ArrayList<>();
    
    void StartKeyListener(){
        for(int i = 0; i < activeKeys.size(); i++){
            keysDown.put(activeKeys.get(i), false);
            keysDownThisFrame.put(activeKeys.get(i), false);
        }
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                synchronized (KeyboardListener.class) {
                    if(activeKeys.contains(e.getKeyCode())){
                        switch(e.getID()){
                            case KeyEvent.KEY_PRESSED:
                            	if(!keysDown.get(e.getKeyCode())) {
	                                keysDown.put(e.getKeyCode(), true);
	                                keysJustHit.add(e.getKeyCode());
	                                System.out.println("Key pressed: " + e.getKeyCode());
                            	}
                                return true;
                            case KeyEvent.KEY_RELEASED:
                                keysDown.put(e.getKeyCode(), false);
                                System.out.println("Key released: " + e.getKeyCode());
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
        keysDownThisFrame.put(keyCode, false);
    }
    
    public void removeKey(int keyCode) {
    	activeKeys.remove(keyCode);
    	keysDown.remove(keyCode);
    	keysDownThisFrame.remove(keyCode);
    }
    
    public boolean getKey(int keyCode){
        return keysDown.get(keyCode);
    }
    
    public boolean getKeyDown(int keyCode) {
    	return keysDownThisFrame.get(keyCode);
    }

	@Override
	public void update() {
		
		for(int keyCode : activeKeys) {
			keysDownThisFrame.put(keyCode, false);
		}
		for(int keyCode : keysJustHit) {
			keysDownThisFrame.put(keyCode, true);
		}
		keysJustHit.clear();
	}
}
