package View.SpriteMangement;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manages different Sprites in a Spritesheet.
 * <p>
 * Taken from <a href="https://stackoverflow.com/questions/35472233/load-a-sprites-image-in-java">this Stackoverflow Question</a>
 */
public class SpriteSheet {

    private final List<BufferedImage> sprites;

    public SpriteSheet(List<BufferedImage> sprites) {
        this.sprites = new ArrayList<>(sprites);
    }

    public int count() {
        return sprites.size();
    }


    private double _previousProgress = 0;
    public BufferedImage getSprite(double progress) {
        int frame = (int) (count() * progress);
        boolean endOfAnimation = (_previousProgress > progress && !_isNewAnimation);
        _previousProgress = progress;

        if (_state != null){
            Range range = _stateRanges.get(_state);
            frame = range.Start + (int) ((range.End - range.Start) * progress);
            //endOfAnimation = (frame >= range.End - 1);
        }

        if (endOfAnimation){
            _state = _defaultState;
            //_defaultState = null;
        }

        _isNewAnimation = false;
        return sprites.get(frame);
    }

    private HashMap<State, Range> _stateRanges = new HashMap<>();
    public void addStateRange(State state,int startIndex, int endIndex)
    {
        _stateRanges.put(state, new Range(startIndex, endIndex));
    }

    private State _state;
    private State _defaultState;
    private boolean _isNewAnimation;
    public void setState(State state)
    {
        _state = state;
        _defaultState = state;
    }
    public void setAnimationState(State state){
        _defaultState = _state;
        _state = state;
        _isNewAnimation = true;
    }

    public boolean getIsNewAnimation(){
        return _isNewAnimation;
    }

    public enum State{
        Idle,
        Attacking,
        Attacked
    }


    public static class Range{
        public final int Start;
        public final int End;
        public Range (int start, int end){
            Start = start;
            End = end;
        }
    }
}