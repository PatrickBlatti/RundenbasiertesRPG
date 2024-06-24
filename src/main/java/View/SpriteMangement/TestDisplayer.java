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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used for Tests in TestView / Test2View
 * Duplicates FightingEntityDisplayer.
 */
public class TestDisplayer extends JPanel {
    protected static final Logger logger = LogManager.getLogger();
    private SpriteSheet spriteSheet;
    private SpriteEngine spriteEngine;
    private FightingEntity _fightingEntity;

    public TestDisplayer(FightingEntity fightingEntity) {
        try {
            _fightingEntity = fightingEntity;
            File f = new File(SpriteMetaDataGenerator.get_SpritePath(_fightingEntity));
            BufferedImage sheet = ImageIO.read(f);

            spriteSheet = new SpriteSheetBuilder()
                    .withSheet(sheet)
                    .withColumns(SpriteMetaDataGenerator.get_ColumnCount(_fightingEntity))
                    .withRows(SpriteMetaDataGenerator.get_RowCount(_fightingEntity))
                    .withSpriteCount(SpriteMetaDataGenerator.get_SpritCount(_fightingEntity))
                    .build();

            spriteSheet.addStateRange(SpriteSheet.State.Idle, 0, 5);
            spriteSheet.addStateRange(SpriteSheet.State.Attacking, 6, 9);
            spriteSheet.addStateRange(SpriteSheet.State.Attacked, 10, 11);

            spriteSheet.setState(SpriteSheet.State.Idle);

            spriteEngine = new SpriteEngine(25);
            spriteEngine.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (spriteSheet.getIsNewAnimation()) {
                        spriteEngine.invaldiate();
                    }
                    repaint();
                }
            });
            spriteEngine.start();
        } catch (IOException ex) {
            logger.error("SpriteSheet konnte nicht erstellt werden. \n{}", ex.toString());
        }
    }

    private int _preferredHeight = 200;
    private int _preferredWidth = 200;

    public void set_PreferredDimension(int height, int width) {
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
        BufferedImage sprite = spriteSheet.getSprite(spriteEngine.getCycleProgress());
        int x = (getWidth() - sprite.getWidth()) / 2;
        int y = (getHeight() - sprite.getHeight()) / 2;
        g2d.drawImage(sprite, x, y, this);
        g2d.dispose();
    }
}
