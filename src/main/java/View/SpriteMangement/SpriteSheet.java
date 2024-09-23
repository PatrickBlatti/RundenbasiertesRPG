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

    /**
     * Returns the current Image to display.
     * @param progress: Progress within the animation
     * @return the Image for the current progress
     */
    public BufferedImage getSprite(double progress) {
        int frame = (int) (count() * progress);
        boolean endOfAnimation = (_previousProgress > progress && !_isNewAnimation);
        _previousProgress = progress;

        if (endOfAnimation){
            _state = _defaultState;
        }

        if (_state != null){
            Range range = _stateRanges.get(_state);
            frame = range.Start + (int) ((range.End - range.Start) * progress);
        }

        _isNewAnimation = false;
        return sprites.get(frame);
    }

    private HashMap<State, Range> _stateRanges = new HashMap<>();

    /**
     * With this function new states and their corresponding Indexranges on the Spritesheet can be added.
     * @param state: Which state does it belong to
     * @param startIndex: Where does the animation start
     * @param endIndex: Where does the animation end
     */
    public void addStateRange(State state,int startIndex, int endIndex)
    {
        _stateRanges.put(state, new Range(startIndex, endIndex));
    }

    private State _state;
    private State _defaultState;
    private boolean _isNewAnimation;

    /**
     * Sets a new default State.
     * @param state: Default State.
     */
    public void setState(State state)
    {
        _state = state;
        _defaultState = state;
    }

    /**
     * Change the Animation to another state.
     * @param state the new Animation.
     */
    public void setAnimationState(State state){
        _state = state;
        _isNewAnimation = true;
    }


    /**
     * Return whether Animation is new
     * @return whether Animation is new
     */
    public boolean getIsNewAnimation(){
        return _isNewAnimation;
    }

    /**
     * Returns whether the Idle Animation is shown
     * @return whether the Idle Animation is shown
     */
    public boolean IsIdling(){
        return _state == State.Idle;
    }

    /**
     * Enum to hold all the possible States to Display
     */
    public enum State{
        Idle,
        Attacking,
        Attacked
    }

    /**
     * Range with Start and End. Used to manage which state is where on a Sprite.
     */
    public static class Range{
        public final int Start;
        public final int End;
        public Range (int start, int end){
            Start = start;
            End = end;
        }
    }
}