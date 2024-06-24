package View.SpriteMangement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;

public class SpriteActionListener implements ActionListener {
    private final SpriteSheet _spriteSheet;
    private final SpriteEngine _engine;
    private Runnable _repaint;

    public SpriteActionListener(SpriteSheet spriteSheet, SpriteEngine engine, Runnable repaint){
        this._spriteSheet = spriteSheet;
        this._engine = engine;
        this._repaint = repaint;
    }

    /**
     * Invoked when an action occurs.
     * Updates the Sprite.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(_spriteSheet.getIsNewAnimation()){ _engine.invaldiate(); }
        _repaint.run();
    }
}
