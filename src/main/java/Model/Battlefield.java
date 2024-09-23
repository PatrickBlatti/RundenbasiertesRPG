package Model;

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
    public List<Enemy> get_Enemies(){
        return _Enemies;
    }



    /**
     * Execute Attack from attacker to attacked.
     * ToDo: Correct implementation of dataflow of who attacks whom.
     * @param attacker: the Attacker
     * @param attacked: the Attacked
     */
    public void Attack(FightingEntity attacker, FightingEntity attacked){
        getCurrentFightingEntity().Attack(_Enemies.get(0));
        EndTurnForFightingEntity();
    }

    /**
     * Returns whether the Victory Condition is met.
     * @return whether the Victory Condition is met.
     */
    public boolean VictoryConditionReached(){
        return get_Enemies().stream().allMatch(x -> x.get_HitPoints() <= 0);
    }

    /**
     * Returns whether the Victory Condition is met.
     * @return whether the Victory Condition is met.
     */
    public boolean LooseConditionReached(){
        return get_Enemies().stream().allMatch(x -> x.get_HitPoints() <= 0);
    }
}
