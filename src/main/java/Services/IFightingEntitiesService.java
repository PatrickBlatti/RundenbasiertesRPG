package Services;

import Model.Enemy;
import Model.Hero;

import java.util.List;

/**
 * Delivers access to all functions for Fighting Entities, which create, come or go outside this Programm.
 */
public interface IFightingEntitiesService {
    /**
     * Instantiates the Heroes.
     * @return All Heroes.
     */
    public List<Hero> get_Heroes();

    /**
     * Instantiates the Enemies for the Stage
     * @return Enemies for the Stage
     */
    public List<Enemy> get_Enemies(int stage);


}
