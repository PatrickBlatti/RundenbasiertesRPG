package Services;

import Model.Enemy;
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
        heroes.add(new Hero());
        return heroes;
    }


    public List<Enemy> get_Enemies(int stage) {
        List<Enemy> heroes = new ArrayList<Enemy>();
        heroes.add(new Enemy());
        return heroes;
    }
}
