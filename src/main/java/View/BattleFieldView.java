package View;

import Controllers.GameController;
import EventInterfaces.IFightingEntityActionInterface;
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
import java.util.Timer;
import java.util.TimerTask;

import View.SpriteMangement.FightingEntityDisplayer;
import View.SpriteMangement.SpriteMetaDataGenerator;
import View.SpriteMangement.SpriteSheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Displays the Battlefield.
 */
public class BattleFieldView extends JFrame {
    protected static final Logger logger = LogManager.getLogger();
    private final int GRID_ROWCOUNT = 4;
    private final int GRID_COLUMNCOUNT = 3;

    private final List<Hero> _heroes;
    private final List<FightingEntityDisplayer> _heroDisplayers = new ArrayList<>();
    private final List<Enemy> _enemies;
    private final List<FightingEntityDisplayer> _enemyDisplayers = new ArrayList<>();
    private BackgroundPanel _panel;
    private GridBagConstraints _gridBagConstraints;
    private BufferedImage _backgroundImage;
    private int _currentHeroIndex = -1;

    /**
     * Constructor of the BattleFieldView
     *
     * @param heroes:  Heroes on the BattleField
     * @param enemies: Enemies on the BattleField
     */
    public BattleFieldView(List<Hero> heroes, List<Enemy> enemies) {
        _heroes = heroes;
        _enemies = enemies;
        initializeBattleField();
    }


    public void EndHeroTurn() {
        var hero = _heroDisplayers.get(0);
        var enemy = _enemyDisplayers.get(0);
        if (!hero.IsIdling() || !enemy.IsIdling()) {
            var bfv = this;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    bfv.EndHeroTurn();
                }
            }, 500);
            return;
        }


        _enemyDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacking);
        _heroDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacked);
        GameController.get_Instance().ExecuteEnemyTurn();
        _AttackButton.setEnabled(true);
    }

    /**
     * Initialization of all visual elements on the BattleField
     */
    private void initializeBattleField() {
        initializeGridWithBackground();
        placeEnemies();
        placeHeroes();
        placeActionButtons();
    }

    /**
     * Creates a GridBag with a Background.
     */
    private void initializeGridWithBackground() {
        try {
            _backgroundImage = ImageIO.read(new File(SpriteMetaDataGenerator.get_BackgroundPath()));
        } catch (IOException e) {
            logger.error("Failed to load background image", e);
        }

        _panel = new BackgroundPanel(_backgroundImage);
        _panel.setLayout(new GridBagLayout());
        this.getContentPane().add(_panel);

        _gridBagConstraints = new GridBagConstraints();

        addSpacerPanel();

    }

    /**
     * Creates all visual elements of the enemies.
     */
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

    /**
     * Creates all visual elements of the heroes.
     */
    private void placeHeroes() {
        var yPosition = 0;

        for (var item : _heroes) {

            if (item instanceof Hero) {
                var displayer = new FightingEntityDisplayer(item);
                addToGrid(displayer, 3, yPosition, 1, 1);
                displayer.setOpaque(false);

                _heroDisplayers.add(displayer);
                yPosition++;
            }
        }
    }

    private final JButton _AttackButton = new JButton("Angriff");
    private final JButton _DefendButton = new JButton("Abwehr");

    /**
     * Creates the ActionButtons for the Battle.
     */
    private void placeActionButtons() {
        var yPosition = Math.max(_heroes.size(), _enemies.size()) + 1;

        //Add Attack Button
        _AttackButton.setEnabled(false);
        _AttackButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _AttackButton.setEnabled(false);
                _heroDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacking);
                _enemyDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacked);
                for (var item : _attackEventListeners) {
                    item.executeAction(_enemies.get(0));
                }
            }

        });
        addToGrid(_AttackButton, 0, yPosition, 4, 1);

        //Add Defend Button
        yPosition += 1;
        _DefendButton.setVisible(false); //At the moment only Attacks are supported
        _DefendButton.setEnabled(false);
        addToGrid(_DefendButton, 0, yPosition, 4, 1);
    }

    private final List<IFightingEntityActionInterface> _attackEventListeners = new ArrayList<>();

    public void addAttackListener(IFightingEntityActionInterface eventListener) {
        _attackEventListeners.add(eventListener);
    }

    public void clearAttackListeners() {
        _attackEventListeners.clear();
    }

    /**
     * Sets which heroes Turn is currently
     *
     * @param index
     */
    public void setCurrentHero(int index) {
        _AttackButton.setEnabled(true);
        _DefendButton.setEnabled(false);
        _currentHeroIndex = index;
    }

    /**
     * Add a Component at a defined Position in the Grid.
     *
     * @param component: component to add to the grid
     * @param x:         X-Position
     * @param y:         Y-Position
     * @param width:     width of the component
     * @param height:    height of the component
     */
    private void addToGrid(Component component, int x, int y, int width, int height) {
        _gridBagConstraints.gridx = x;
        _gridBagConstraints.gridy = y;
        _gridBagConstraints.gridwidth = width;
        _gridBagConstraints.gridheight = height;
        _gridBagConstraints.weightx = 1.0;
        _gridBagConstraints.weighty = 1.0;
        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _panel.add(component, _gridBagConstraints);
    }

    /**
     * Adds Space between Enemies and Heroes.
     */
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

}


