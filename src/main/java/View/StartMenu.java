package View;

import Controllers.GameController;
import Model.FightingEntity;
import View.SpriteMangement.SpriteMetaDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartMenu extends JFrame {
    protected static final Logger logger = LogManager.getLogger();
    private BufferedImage _backgroundImage;
    private BackgroundPanel _panel;
    private GridBagConstraints _gridBagConstraints;

    /**
     * Constructor of the StartMenu. Here the Heroes Class can be chosen.
    */
    public StartMenu() {
        initializeStartMenu();
    }

    /**
     * Initialization of all visual elements on the StartMenu
     */
    private void initializeStartMenu() {
        initializeGridWithBackground();
        addSpacerPanels();
        placeText();
        placeHeroButtons();

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
        _gridBagConstraints.insets= new Insets(5, 5, 5, 5);

    }

    /**
     * Places all Information on the UI
     */
    private void placeText() {
        var introductionQuestion = new JTextField("Gott sei Dank, ein Held! Wir werden von Monstern angegriffen. Könnt Ihr uns Helfen?");
        introductionQuestion.setBackground(Color.BLUE);
        introductionQuestion.setForeground(Color.WHITE);
        introductionQuestion.setHorizontalAlignment(JTextField.CENTER);
        addToGrid(introductionQuestion, 1,1,1,1);
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
     * Adds Spacers for empty display areas.
     */
    private void addSpacerPanels() {
        var spacerPanelTop = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 100);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        spacerPanelTop.setOpaque(false);
        addToGrid(spacerPanelTop, 0, 0, 3, 1);

        var spacerPanelBottom = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 100);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        spacerPanelBottom.setOpaque(false);
        addToGrid(spacerPanelBottom, 0, 7, 3, 1);

        var spacerPanelLeft = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        spacerPanelLeft.setOpaque(false);
        addToGrid(spacerPanelLeft, 0, 1, 1, 4);


        var spacerPanelRight = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        spacerPanelRight.setOpaque(false);
        addToGrid(spacerPanelRight, 2, 1, 4, 1);
    }
    /**
     * Creates the Buttons for the Choice of Heroes.
     */
    private void placeHeroButtons() {
        //Add Warrior Button
        var spearButton = new JButton("Für etwas habe ich diesen Speer");
        addToGrid(spearButton, 1,2,1,1);
        spearButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                informChoice(FightingEntity.Type.Warrior);
            }
        });

        //Add Warrior Button
        var paladinButton = new JButton("Mein Schwert steht euch zur Verfügung");
        addToGrid(paladinButton, 1,3,1,1);
        paladinButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                informChoice(FightingEntity.Type.Paladin);
            }
        });

        //Add Wizard Button
        var wizardButton = new JButton("Ich wollte sowieso meine Zauber üben");
        addToGrid(wizardButton, 1,5,1,1);
        wizardButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                informChoice(FightingEntity.Type.Wizard);
            }
        });

        //Add Wizard Button
        var bowmanButton = new JButton("Oh, wo sind die Nadelkissen für meine Pfeile");
        addToGrid(bowmanButton, 1,6,1,1);
        bowmanButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                informChoice(FightingEntity.Type.Bowman);
            }
        });
    }

    private void informChoice(FightingEntity.Type type){
        GameController.get_Instance().StartGame(type);
        this.dispose();
    }
}