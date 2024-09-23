package Model;

/**
 * Is used for the Opponent of the Player
 */
public class Enemy extends FightingEntity {

    private int _Stage = 0;
    private void set_Stage(int stage) {
        _Stage = stage;
    }

    public Enemy(Type type, int maxHP) {
        super(type, maxHP);
    }

    @Override
    public int get_AttackValue() {
        return _Stage * 5;
    }

    @Override
    public int get_DefenseValue() {
        return _Stage * 2;
    }

    public int get_Evasion(){
        if (get_Type() == Type.Ghost) return 30;
        return 0;
    }
}
