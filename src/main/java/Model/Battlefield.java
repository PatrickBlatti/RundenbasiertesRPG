package Model;

import Controllers.GameController;
import EventInterfaces.ICustomEventInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all information pertaining a single battle.
 */
public class Battlefield {

    /**
     * Instantiates the Battlefield.
     */
    public Battlefield(List<Hero> heroes, List<Enemy> enemies)
    {
        _Heroes = heroes;
        _Enemies = enemies;

        _FightingOrder= new ArrayList<>();
        _FightingOrder.addAll(heroes);
        _FightingOrder.addAll(enemies);
    }
//region "Fighting Entity management"
    private final List<FightingEntity> _FightingOrder;
    private int _Fightposition = 0;

    /**
     * Returns the Fighting Entity whose Turn it is
     * @return the Fighting Entity whose Turn it is
     */
    public FightingEntity getCurrentFightingEntity(){
        return _FightingOrder.get(_Fightposition % _FightingOrder.size());
    }

    public void EndTurnForFightingEntity(){
        _Fightposition += 1;

        FightingEntity current = getCurrentFightingEntity();
        if (current instanceof  Enemy){
            get_FightingEntityAI().MakeMove(getCurrentFightingEntity(), this);
            EndTurnForFightingEntity();
        }
    }

    private FightingEntityAI _FightingEntityAI;
    private FightingEntityAI get_FightingEntityAI(){
        if (_FightingEntityAI == null){
            _FightingEntityAI =  new FightingEntityAI();
        }
        return _FightingEntityAI;
    }
//endregion

    private final List<Hero> _Heroes;
    /**
     * All Active Heroes on the Battlefield.
     */
    public List<Hero> get_Heroes(){
        return _Heroes;
    }

    private final List<Enemy> _Enemies;
    /**
     * All active Enemies on the Battlefield.
     */
    private List<Enemy> Enemies(){
        return _Enemies;
    }


    //region EventHandling

    private final List<ICustomEventInterface> _VictoryEventHandlers = new ArrayList<>();
    /**
     * Adds an EventHandler for the Victory Event.
     * @param eventHandler Eventhandler for Victory.
     */
    public void AddVictoryEventHandler(ICustomEventInterface eventHandler){
        _VictoryEventHandlers.add(eventHandler);
    }

    /**
     * Raise Victory-Event for all Listeners.
     */
    private void RaiseVictoryEvent(){
        for(ICustomEventInterface item : _VictoryEventHandlers){
            item.Execute();
        }
    }


    private final List<ICustomEventInterface> _LossEventHandler = new ArrayList<>();
    /**
     * Adds an Eventhandler for the Loss Event.
     * @param eventHandler Eventhandler for Loosing.
     */
    private void AddLossEvent(ICustomEventInterface eventHandler){
        _LossEventHandler.add(eventHandler);
    }

    /**
     * Raise Loss-Event for all Listeners.
     */
    private void RaiseLossEvent(){
        for(ICustomEventInterface item : _LossEventHandler){
            item.Execute();
        }
    }

    //endregion
}
