package Model;

/**
 * Base Class for all Fighting Entities in the Game.
 */
public abstract class FightingEntity {

    protected double _attackValue;
    /**
     * Gets the Attack Value of a Fighting Entity.
     * The maximal Damage a Fighting Entity can make. It can be mitigated by the Defense Value.
     * @return A double how much Damage the Fighting Entity can make.
     *
     */
    public double get_AttackValue(){
       return _attackValue;
    }

    protected double _defenseValue;
    /**
     * Gets the Defense Value of the Fighting Entity.
     * @return  How high the Defense Value is.
     */
    double get_DefenseValue(){
        return _defenseValue;
    }

    protected double _hitPoints;
    /**
     * Gets the HitPoints of the Fighting Entity.
     * @return HitPoints of the Entity
     */
    double get_HitPoints(){
        return _hitPoints;
    }


    /**
     * Reduces the HitPoints.
     * @param value: amount by which the HitPoints will be reduced.
     */
    public void ReduceHitpoints(double value){
        this._hitPoints -= value;
    }

    /**
     * Executes an attack against the target.
     * @param target: Fighting Entity that is attacked.
     */
    public void Attack(FightingEntity target){
        if(Math.random() * 100 > CalculateHitChance(target)){ return; }
        double damage = this.get_AttackValue() - target.get_DefenseValue();
        target.ReduceHitpoints(damage);
    }

    /**
     * Calculates the Chance of Success for hitting the Target.
     * @param target Target of the Action.
     * @return the Chance of Success.
     */
    private int CalculateHitChance(FightingEntity target){
        return 80;
    }
}
