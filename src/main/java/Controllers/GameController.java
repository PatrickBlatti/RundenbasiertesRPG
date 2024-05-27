package Controllers;

import EventInterfaces.ICustomEventInterface;
import Factories.ServiceFactory;
import Model.Battlefield;

/**
 * Starting point of the Game. Controls the different phases of the Game and instantiates the UI.
 * Access will be done through get_Instance.
 */
public class GameController {
    private static GameController _Instance;
    private GameController(){

    }

    public static GameController get_Instance(){
        if(_Instance == null){
            _Instance = new GameController();
            _Instance.Initialize();
        }
        return _Instance;
    }

    /**
     * Initializes the Game
     */
    private void Initialize(){
        CreateNewBattle();

    }

    /**
     * Creates a Stage appropriate Battlefield.
     */
    private void CreateNewBattle(){
        //Dispose of old Battlefield.

        _Stage += 1;
        _Battlefield = new Battlefield(ServiceFactory.get_FightingEntitiesService().get_Heroes(),
                ServiceFactory.get_FightingEntitiesService().get_Enemies(_Stage));

        _Battlefield.AddVictoryEventHandler(new ICustomEventInterface() {
            @Override
            public void Execute() {
                CreateNewBattle();
            }
        });
    }


    private int _Stage = 0;
    public int get_Stage(){
        return  _Stage;
    }

    private Battlefield _Battlefield;

    /**
     * Current Battlefield.
     * @return the current battlefield.
     */
    public Battlefield get_Battlefield(){
        return _Battlefield;
    }

    /**
     * Player lost the Game. Teardown / restart happens here.
     */
    public void GameOver(){
        //Validation über aktuelles Battlefield.
        //GameOver => Neustart oder schliessen des Spiels.
    }

    /**
     * Starting point of the Game.
     * @param args
     */
    public static void main(String[] args) {
        GameController gc = GameController.get_Instance();
        //Weiter Implementation folgt
    }
}
