package View;

import Model.Enemy;
import Model.Hero;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import View.SpriteMangement.TestDisplayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used to test Changes before implementing them in the BattleGroundView
 */
public class TestView extends JFrame {
    protected static final Logger logger = LogManager.getLogger();
    private final int GRID_ROWCOUNT = 4;
    private final int GRID_COLUMNCOUNT = 3;

    private final List<Hero> _heroes;
    private final List<Enemy> _enemies;
    private JPanel _Panel;
    private GridBagConstraints _gridBagConstraints;

    public TestView(List<Hero> heroes, List<Enemy> enemies) {
        _heroes = heroes;
        _enemies = enemies;
        initializeBattleField();
    }

    private void initializeBattleField() {
        initializeGrid();
        placeEnemies();
        placeHeroes();
        placeActionButtons();
        pack(); // Adjust frame to fit the preferred size and layouts of its subcomponents
    }

    private void initializeGrid() {
        _Panel = new JPanel(new GridBagLayout());
        this.getContentPane().add(_Panel);
        _gridBagConstraints = new GridBagConstraints();
    }

    private void placeEnemies() {
        int yPosition = 0;
        for (var item : _enemies) {
            if (item instanceof Enemy) {
                var displayer = new TestDisplayer(item);
                addToGrid(displayer, 0, yPosition, 1, 1);
                yPosition++;
            }
        }
    }

    private void placeHeroes() {
        int yPosition = 0;
        for (var item : _heroes) {
            if (item instanceof Hero) {
                var displayer = new TestDisplayer(item);
                addToGrid(displayer, 2, yPosition, 1, 1);
                yPosition++;
            }
        }
    }

    private JButton _AttackButton = new JButton("Angriff");
    private JButton _DefendButton = new JButton("Abwehr");

    private void placeActionButtons() {
        int yPosition = Math.max(_heroes.size(), _enemies.size());

        // Add Attack Button
        _AttackButton.setEnabled(false);
        addToGrid(_AttackButton, 0, yPosition, 3, 1);

        // Add Defend Button
        yPosition += 1;
        _DefendButton.setEnabled(false);
        addToGrid(_DefendButton, 0, yPosition, 3, 1);
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        _Panel.add(component, gbc);
    }


}
