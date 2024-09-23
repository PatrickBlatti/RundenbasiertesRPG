package Controllers;


import EventInterfaces.IFightingEntityActionInterface;
import Factories.ServiceFactory;
import Model.Battlefield;
import Model.FightingEntity;
import View.BattleFieldView;
import View.StartMenu;

import javax.swing.*;

import static java.awt.Component.CENTER_ALIGNMENT;
import static javax.swing.JOptionPane.showConfirmDialog;

/**
 * Starting point of the Game. Controls the different phases of the Game and instantiates the UI.
 * Access will be done through get_Instance.
 */
public class GameController {
    private static GameController _Instance;
    private BattleFieldView _BattleFieldView;
    private FightingEntity.Type _HeroType = FightingEntity.Type.Warrior; //Fallback Value if something went wrong


    private GameController() {

    }

    public static GameController get_Instance() {
        if (_Instance == null) {
            _Instance = new GameController();
        }
        return _Instance;
    }

    /**
     * Initializes the Game
     */
    private void Initialize(FightingEntity.Type type) {
        _HeroType = type;
        CreateNewBattle(type);
    }

    /**
     * Creates a Stage appropriate Battlefield.
     */
    private void CreateNewBattle(FightingEntity.Type type) {
        _Stage += 1;
        _Battlefield = new Battlefield(ServiceFactory.get_FightingEntitiesService().get_Heroes(type),
                ServiceFactory.get_FightingEntitiesService().get_Enemies(_Stage));
    }


    private int _Stage = 0;


    private Battlefield _Battlefield;

    /**
     * Current Battlefield.
     *
     * @return the current battlefield.
     */
    public Battlefield get_Battlefield() {
        return _Battlefield;
    }

    /**
     * Player lost the Game. Teardown / restart happens here.
     */
    public void GameOver(boolean won) {
        String message = won ? "Ihr habt gewonnen, wollt Ihr noch einmal spielen?" : "Ihr habt verloren, wollt Ihr es noch einmal probieren?";
        var x = showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.YES_OPTION) {
            _BattleFieldView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            _Stage = 0;
            showStartMenu();
        } else if (x == JOptionPane.NO_OPTION) {
            _BattleFieldView.dispose();
        }
    }


    /**
     * Starting point of the Game.
     *
     * @param args
     */
    public static void main(String[] args) {
        GameController gc = GameController.get_Instance();
        gc.showStartMenu();
    }

    /**
     * Starts the battle with a specific Hero Type.
     * @param type of the Hero.
     */
    public void StartGame(FightingEntity.Type type) {
        this.Initialize(type);
        DisplayBattle();
        AddViewEventHandlers();
    }

    /**
     * Registers functions to the Events from the View.
     */
    private void AddViewEventHandlers(){
        _BattleFieldView.clearAttackListeners();

        _BattleFieldView.addAttackListener(new IFightingEntityActionInterface() {
            @Override
            public void executeAction(FightingEntity attacked) {
                var gc = GameController.get_Instance();
                gc._Battlefield.Attack(null, null);

                if (HandleVictoryLoss(gc)){
                   _BattleFieldView.EndHeroTurn();
                }

            }
        });
    }

    /**
     * Executes the Action of the Enemy
     */
    public void ExecuteEnemyTurn(){
        var bf = this.get_Battlefield();
        bf.MakeEnemyMove();
        HandleVictoryLoss(this);
    }

    private boolean HandleVictoryLoss(GameController gc){
        if (gc._Battlefield.VictoryConditionReached() && _Stage < 4) {
            JOptionPane.showMessageDialog(null,"Uff, das war Knapp. \n Oh nein, dort Hinten hat es noch mehr Monster!");
            CreateNewBattle(_HeroType);
            DisplayBattle();
            AddViewEventHandlers();
            return false;
        } else if (gc._Battlefield.VictoryConditionReached() && _Stage >= 4) {
            GameOver(true);
            return false;
        }
        else if (gc._Battlefield.LooseConditionReached()) {
            gc.GameOver(false);
            return false;
        }
        return true;
    }

    /**
     * Creates the View for the battle.
     */
    private void DisplayBattle(){
        if (_BattleFieldView != null) {
            _BattleFieldView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            _BattleFieldView.dispose();
        }
        BattleFieldView jFrame = new BattleFieldView(this.get_Battlefield().get_Heroes(), this.get_Battlefield().get_Enemies());

        jFrame.setSize(800, 800);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        _BattleFieldView = jFrame;

        //Set GameLogic to View
        _BattleFieldView.setCurrentHero(0);
    }

    /**
     * Displays the Start Menu.
     */
    private void showStartMenu() {
        StartMenu jFrame = new StartMenu();

        jFrame.setSize(800, 800);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

    }

}
