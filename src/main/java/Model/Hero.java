package Model;

/**
 * Used for the Playable Characters in the Game.
 */
public class Hero extends  FightingEntity{

    public Hero(Type type, int maxHP) {
        super(type, maxHP);
    }

    @Override
    public int get_AttackValue() {
        return 30;
    }
}
