package View;

import Model.Enemy;
import Model.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import View.SpriteMangement.FightingEntityDisplayer;
import View.SpriteMangement.SpriteSheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used to test Changes before implementing them in the BattleGroundView
 */
public class Test2View extends JFrame {
    protected static final Logger logger = LogManager.getLogger();
    private final int GRID_ROWCOUNT = 4;
    private final int GRID_COLUMNCOUNT = 3;

    private final List<Hero> _heroes;
    private List<FightingEntityDisplayer> _heroDisplayers = new ArrayList<>();
    private final List<Enemy> _enemies;
    private List<FightingEntityDisplayer> _enemyDisplayers = new ArrayList<>();
    private BackgroundPanel _Panel;
    private GridBagConstraints _gridBagConstraints;
    private BufferedImage _backgroundImage;
    private int _currentHeroIndex = -1;

    public Test2View(List<Hero> heroes, List<Enemy> enemies) {
        _heroes = heroes;
        _enemies = enemies;
        initializeBattleField();
    }

    private void initializeBattleField() {
        initializeGridWithBackground();
        placeEnemies();
        placeHeroes();
        placeActionButtons();
        pack(); // Adjust frame to fit the preferred size and layouts of its subcomponents
    }

    private void initializeGridWithBackground() {
        try {
            _backgroundImage = ImageIO.read(new File("C:\\Users\\pblat\\OneDrive - TEKO Schweizerische Fachschule AG\\Dokumente\\TEKO\\OOP\\Sommersemester 2024\\TurnbasedRPG\\src\\main\\resources\\background4.png"));
        } catch (IOException e) {
            logger.error("Failed to load background image", e);
        }

        _Panel = new BackgroundPanel(_backgroundImage);
        _Panel.setLayout(new GridBagLayout());
        this.getContentPane().add(_Panel);

        _gridBagConstraints = new GridBagConstraints();

        addSpacerPanel();
    }

    private void addSpacerPanel() {
        var spacerPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(275, 300);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        spacerPanel.setOpaque(false);
        addToGrid(spacerPanel, 1, 0, 1, 1);
    }

    private void placeEnemies() {
        var yPosition = 0;

        for (var item : _enemies) {
            if (item instanceof Enemy) {
                var displayer = new FightingEntityDisplayer(item);
                addToGrid(displayer, 0, yPosition, 1, 1);
                displayer.setOpaque(false);

                _enemyDisplayers.add(displayer);
                yPosition++;
            }
        }
    }

    private void placeHeroes() {
        var constraints = _gridBagConstraints;

        var yPosition = 0;
        for (var item : _heroes) {
            constraints.gridx = 2;
            constraints.gridy = 0;

            if (item instanceof Hero) {
                constraints.gridy = yPosition;
                var displayer = new FightingEntityDisplayer(item);
                addToGrid(displayer, 2, yPosition, 1, 1);

                _heroDisplayers.add(displayer);
                yPosition++;
            }
        }
    }

    private JButton _AttackButton = new JButton("Angriff");
    private JButton _DefendButton = new JButton("Abwehr");

    private void placeActionButtons() {
        var yPosition = Math.max(_heroes.size(), _enemies.size());

        // Add Attack Button
        _AttackButton.setEnabled(false);
        _AttackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _heroDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacking);
                _enemyDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacked);
            }
        });
        addToGrid(_AttackButton, 0, yPosition, 3, 1);

        // Add Defend Button
        yPosition += 1;
        _DefendButton.setEnabled(false);
        addToGrid(_DefendButton, 0, yPosition, 3, 1);
    }

    public void setCurrentHero(int index) {
        _AttackButton.setEnabled(true);
        _DefendButton.setEnabled(false);
        _currentHeroIndex = index;
    }

    /**
     * Add a Component at a defined Position in the Grid.
     *
     * @param component: component to add to the grid
     * @param x: X-Position
     * @param y: Y-Position
     * @param width: width of the component
     * @param height: height of the component
     */
    private void addToGrid(Component component, int x, int y, int width, int height) {
        _gridBagConstraints.gridx = x;
        _gridBagConstraints.gridy = y;
        _gridBagConstraints.gridwidth = width;
        _gridBagConstraints.gridheight = height;
        _gridBagConstraints.weightx = 1.0;
        _gridBagConstraints.weighty = 1.0;
        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _Panel.add(component, _gridBagConstraints);
    }

    // Panel that paints the background image
    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

}
