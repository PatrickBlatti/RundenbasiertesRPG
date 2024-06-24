package Services;

import Model.Enemy;
import Model.FightingEntity;
import Model.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Creation of Heroes for Develop purposes. Production should use another Service with
 * implemented Save / Load Functions
 */
public class FightingEntitiesService_Develop  implements IFightingEntitiesService {

    public List<Hero> get_Heroes(){
        List<Hero> heroes = new ArrayList<Hero>();

        heroes.add(new Hero(FightingEntity.Type.Warrior, 100));

        //heroes.add(new Hero(FightingEntity.Type.Wizard));
        return heroes;
    }


    public List<Enemy> get_Enemies(int stage) {
        List<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(FightingEntity.Type.Boar, 100));
        //enemies.add(new Enemy(FightingEntity.Type.Mimic));
        return enemies;
    }
}
