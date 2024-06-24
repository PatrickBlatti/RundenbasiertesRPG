package View;

import Model.FightingEntity;
import View.SpriteMangement.*;

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


public class FightingEntityDisplayer extends JPanel {
    protected static final Logger logger = LogManager.getLogger();
    private SpriteSheet spriteSheet;
    private SpriteEngine spriteEngine;
    private FightingEntity _figthingEntity;

    public FightingEntityDisplayer(FightingEntity fightingEntity) {
        try {
            _figthingEntity = fightingEntity;
            File f = new File(SpriteMetaDataGenerator.get_SpritePath(_figthingEntity));
            BufferedImage sheet = ImageIO.read(f);

            spriteSheet =  new SpriteSheetBuilder().
                    withSheet(sheet).
                    withColumns(SpriteMetaDataGenerator.get_ColumnCount(_figthingEntity)).
                    withRows(SpriteMetaDataGenerator.get_RowCount(_figthingEntity)).
                    withSpriteCount(SpriteMetaDataGenerator.get_SpritCount(_figthingEntity)).
                    build();

            addAnimationRanges(fightingEntity);

            spriteSheet.setState(SpriteSheet.State.Idle);

            spriteEngine = new SpriteEngine(25);
            spriteEngine.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(spriteSheet.getIsNewAnimation()){ spriteEngine.invaldiate(); }
                    repaint();
                }
            });
            spriteEngine.start();
        } catch (IOException ex) {
            logger.error("SpriteSheet konnte nicht erstellt werden. \n{}", ex.toString());
        }
    }

    private void addAnimationRanges(FightingEntity fightingEntity) {
        var range = SpriteMetaDataGenerator.get_IdleRange(fightingEntity);
        spriteSheet.addStateRange(SpriteSheet.State.Idle, range.Start, range.End);

        range = SpriteMetaDataGenerator.get_AttackingRange(fightingEntity);
        spriteSheet.addStateRange(SpriteSheet.State.Attacking, range.Start, range.End);

        range = SpriteMetaDataGenerator.get_AttackedRange(fightingEntity);
        spriteSheet.addStateRange(SpriteSheet.State.Attacked, range.Start,range.End);
    }

    public void doAnimation(SpriteSheet.State state){
        spriteSheet.setAnimationState(state);
    }

    private int _preferredHeight = 200;
    private int _preferredWidth = 200;
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
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        //System.out.println("Cycle Progress: " + spriteEngine.getCycleProgress());
        BufferedImage sprite = spriteSheet.getSprite(spriteEngine.getCycleProgress());
        int x = (getWidth() - sprite.getWidth()) / 2;
        int y = (getHeight() - sprite.getHeight()) / 2;
        g2d.drawImage(sprite, x, y, this);
        g2d.dispose();
    }

}