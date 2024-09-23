package Model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base Class for all Fighting Entities in the Game.
 */
public abstract class FightingEntity {
    private static final Logger log = LogManager.getLogger(FightingEntity.class);

    /**
     * Instantiates a Fighting Entity of a specific Type.
     * @param type of the FightingEntity
     * @param maxHP of the FightingEntity
     */
    public FightingEntity(Type type, int maxHP){
        _type = type;
        _maxHitPoints = maxHP;
        _hitPoints = maxHP;
    }

    protected int _attackValue;
    /**
     * Gets the Attack Value of a Fighting Entity.
     * The maximal Damage a Fighting Entity can make. It can be mitigated by the Defense Value.
     * @return A double how much Damage the Fighting Entity can make.
     *
     */
    public int get_AttackValue(){
       return _attackValue;
    }

    protected int _defenseValue;
    /**
     * Gets the Defense Value of the Fighting Entity.
     * @return  How high the Defense Value is.
     */
    public int get_DefenseValue(){
        return _defenseValue;
    }

    protected int _hitPoints;
    /**
     * Gets the HitPoints of the Fighting Entity.
     * @return HitPoints of the Entity
     */
    public int get_HitPoints(){
        return _hitPoints;
    }

    protected int _maxHitPoints;
    public void set_maxHitPoints(int maxHitPoints){
        _maxHitPoints = maxHitPoints;
    }
    public int get_maxHitPoints(){
        return _maxHitPoints;
    }

    private String _Message;
    public String get_Message(){ return _Message; }
    public void set_Message(String message){_Message = message; }

    /**
     * Reduces the HitPoints.
     * @param value: amount by which the HitPoints will be reduced.
     */
    public void ReduceHitpoints(int value){
        this._hitPoints -= value;
    }

    /**
     * Executes an attack against the target.
     * @param target: Fighting Entity that is attacked.
     */
    public void Attack(FightingEntity target){
        if(Math.random() * 100 > CalculateHitChance(target)){
            log.log(Level.INFO, "Fighting Entity " + this._type.toString() + " missed " + target._type.toString());
            return;
        }

        int damage = this.get_AttackValue() - target.get_DefenseValue();
        target.ReduceHitpoints(damage);
        log.log(Level.INFO, "Fighting Entity " + this._type.toString() + " attacked " + target._type.toString() + " for damage " + damage);
    }

    /**
     * Calculates the Chance of Success for hitting the Target.
     * @param target Target of the Action.
     * @return the Chance of Success.
     */
    protected int CalculateHitChance(FightingEntity target){
        var ret = 80;
        if (target instanceof Enemy){ ret -= ((Enemy) target).get_Evasion();}
        return ret;
    }


    private String _DispolayMessage = "";
    public String get_DisplayMessage(){
        return _DispolayMessage;
    }

    protected Type _type = null;
    /**
     * Returns the Type of the Fighting Entity.
     * Used to determine which Sprite should be shown.
     */
    public Type get_Type(){
        return _type;
    }

    public enum Type{
        //Heroes
        Warrior(1),
        Mage(2),
        Bowman(3),
        Wizard(4),
        Paladin(5),
        Boar(100),
        Mimic(101),
        Ghost(102),
        Mushroom(103)

        ;


        Type(int i) {

        }
    }
}
