package View.SpriteMangement;

import Model.FightingEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import  org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A JPanel which displays a FightingEntity.
 * It automatically chooses the right Sprite and animation ranges.
 */
public class FightingEntityDisplayer extends JPanel {
    protected static final Logger logger = LogManager.getLogger();
    private SpriteSheet _spriteSheet;
    private SpriteEngine _spriteEngine;
    private FightingEntity _fightingEntity;
    private JProgressBar _progressBar;

    /**
     * Creates a new FightingEntityDisplayer and initializes it.
     * @param fightingEntity: FightingEntity to Display
     */
    public FightingEntityDisplayer(FightingEntity fightingEntity) {
        _fightingEntity = fightingEntity;
        initializeSprite();

        _progressBar = new JProgressBar();
        _progressBar.setMaximum(fightingEntity.get_maxHitPoints());
        _progressBar.setValue(_fightingEntity.get_HitPoints());

        _progressBar.setStringPainted(true);
        _progressBar.setForeground(Color.green);
        this.add(_progressBar);

    }

    /**
     * Initializes the Sprite.
     */
    private void initializeSprite(){
        try {
            File f = new File(SpriteMetaDataGenerator.get_SpritePath(_fightingEntity));
            BufferedImage sheet = ImageIO.read(f);

            _spriteSheet =  new SpriteSheetBuilder().
                    withSheet(sheet).
                    withColumns(SpriteMetaDataGenerator.get_ColumnCount(_fightingEntity)).
                    withRows(SpriteMetaDataGenerator.get_RowCount(_fightingEntity)).
                    withSpriteCount(SpriteMetaDataGenerator.get_SpritCount(_fightingEntity)).
                    build();

            addAnimationRanges(_fightingEntity);

            _spriteSheet.setState(SpriteSheet.State.Idle);

            _spriteEngine = new SpriteEngine(25);
            _spriteEngine.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(_spriteSheet.getIsNewAnimation()){ _spriteEngine.invaldiate(); }
                    repaint();
                }
            });
            _spriteEngine.start();
        } catch (IOException ex) {
            logger.error("SpriteSheet konnte nicht erstellt werden. \n{}", ex.toString());
        }
    }

    /**
     * Configures the Animation Ranges of the FightingEntity.
     * @param fightingEntity: FightingEntity to Display
     */
    private void addAnimationRanges(FightingEntity fightingEntity) {
        var range = SpriteMetaDataGenerator.get_IdleRange(fightingEntity);
        _spriteSheet.addStateRange(SpriteSheet.State.Idle, range.Start, range.End);

        range = SpriteMetaDataGenerator.get_AttackingRange(fightingEntity);
        _spriteSheet.addStateRange(SpriteSheet.State.Attacking, range.Start, range.End);

        range = SpriteMetaDataGenerator.get_AttackedRange(fightingEntity);
        _spriteSheet.addStateRange(SpriteSheet.State.Attacked, range.Start,range.End);
    }

    /**
     * Executes the corresponding animation for the state
     * @param state: new state of the Sprite.
     */
    public void doAnimation(SpriteSheet.State state){
        _spriteSheet.setAnimationState(state);
    }

    private int _preferredHeight = 200;
    private int _preferredWidth = 200;

    /**
     * Allows to override the preferred Dimensions.
     * @param height: preferred Height.
     * @param width: preferred Width.
     */
    public void set_PreferredDimension(int height, int width){
        _preferredHeight = height;
        _preferredWidth = width;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(_preferredWidth, _preferredHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println(_fightingEntity.get_HitPoints());
        _progressBar.setValue(_fightingEntity.get_HitPoints()); //Might be problematic for Performance
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        //System.out.println("Cycle Progress: " + spriteEngine.getCycleProgress());
        BufferedImage sprite = _spriteSheet.getSprite(_spriteEngine.getCycleProgress());
        int x = (getWidth() - sprite.getWidth()) / 2;
        int y = (getHeight() - sprite.getHeight()) / 2;
        g2d.drawImage(sprite, x, y, this);
        g2d.dispose();
    }

}