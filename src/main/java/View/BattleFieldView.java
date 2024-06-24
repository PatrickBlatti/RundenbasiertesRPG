package View;

import Model.Enemy;
import Model.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import View.SpriteMangement.SpriteSheet;
import  org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BattleFieldView extends JFrame {
    protected static final Logger logger = LogManager.getLogger();
    private final int GRID_ROWCOUNT = 4;
    private final int GRID_COLUMNCOUNT = 3;

    private final List<Hero> _heroes;
    private List<FightingEntityDisplayer> _heroDisplayers = new ArrayList<>();
    private final List<Enemy> _enemies;
    private List<FightingEntityDisplayer> _enemyDisplayers = new ArrayList<>();
    private JPanel _Panel;
    private GridBagConstraints _gridBagConstraints;
    private BufferedImage _backgroundImage;
    private int _currentHeroIndex = -1;

    public BattleFieldView(List<Hero> heroes, List<Enemy> enemies){
        _heroes = heroes;
        _enemies = enemies;
        initializeBattleField();
    }

    private void initializeBattleField(){
        initializeGridWithBackground();
        placeEnemies();
        placeHeroes();
        placeActionButtons();
    }

    private void initializeGrid(){
        _Panel = new JPanel(new GridBagLayout());
        this.getContentPane().add(_Panel);
        this.setLayout(new GridBagLayout());
        _gridBagConstraints = new GridBagConstraints();


        addSpacerPanel();

        //TODO: Set Background
    }


    private void initializeGridWithBackground(){
        var backgroundPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(backgroundPanel);

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\pblat\\OneDrive - TEKO Schweizerische Fachschule AG\\Dokumente\\TEKO\\OOP\\Sommersemester 2024\\TurnbasedRPG\\src\\main\\resources\\background4.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundPanel.add(backgroundLabel, BorderLayout.CENTER);

        _Panel = new JPanel(new GridBagLayout());
        _Panel.setOpaque(false);
        backgroundPanel.add(_Panel);

        this.setLayout(new GridBagLayout());
        _gridBagConstraints = new GridBagConstraints();

        addSpacerPanel();

    }
    private void addSpacerPanel() {
        var spacerPanel = new JPanel(){
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
        addToGrid(spacerPanel,1,0, 1,1);
    }

    private void placeEnemies(){
        var yPosition = 0;

        for(var item : _enemies){

            if (item instanceof Enemy) {
                var displayer = new FightingEntityDisplayer(item);
                addToGrid(displayer, 0, yPosition, 1,1);

                _enemyDisplayers.add(displayer);
                yPosition++;
            }
        }

    }

    private void placeHeroes(){
        var constraints = _gridBagConstraints;

        var yPosition = 0;
        for(var item : _heroes){
            constraints.gridx = 2;
            constraints.gridy = 0;

            if (item instanceof Hero) {
                constraints.gridy = yPosition;
                var displayer = new FightingEntityDisplayer(item);
                addToGrid(displayer, 3, yPosition, 1,1);

                _heroDisplayers.add(displayer);
                yPosition++;
            }
        }

    }

    private JButton _AttackButton = new JButton("Angriff");
    private JButton _DefendButton = new JButton("Abwehr");
    private void placeActionButtons(){
        var yPosition =Math.max(_heroes.size(), _enemies.size()) + 1;

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
                _heroDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacking);
                _enemyDisplayers.get(_currentHeroIndex).doAnimation(SpriteSheet.State.Attacked);
            }

                                        });
        addToGrid(_AttackButton, 0, yPosition, 4,1);

        //Add Defend Button
        yPosition += 1;
        _DefendButton.setEnabled(false);
        addToGrid(_DefendButton, 0, yPosition, 4,1);
    }

    public void setCurrentHero(int index){
        _AttackButton.setEnabled(true);
        _DefendButton.setEnabled(false);
        _currentHeroIndex = index;
    }

    /**
     * Add a Component at a defined Position in the Grid.
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



}
