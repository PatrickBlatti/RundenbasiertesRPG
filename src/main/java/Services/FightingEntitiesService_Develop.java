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

    public List<Hero> get_Heroes(FightingEntity.Type type){
        List<Hero> heroes = new ArrayList<Hero>();

        heroes.add(new Hero(type, 100));
        return heroes;
    }


    public List<Enemy> get_Enemies(int stage) {
        List<Enemy> enemies = new ArrayList<Enemy>();
        if (stage == 1) {
            enemies.add(new Enemy(FightingEntity.Type.Boar, 100));
        }
        else if (stage == 2) {
            enemies.add(new Enemy(FightingEntity.Type.Mimic, 100));
        }
        else if (stage == 3) {
            enemies.add(new Enemy(FightingEntity.Type.Mushroom, 100));
        }
        else if (stage == 4) {
            enemies.add(new Enemy(FightingEntity.Type.Ghost, 100));
        }

        for(Enemy item : enemies){
            item.set_Stage(stage);
        }

        return enemies;
    }
}
