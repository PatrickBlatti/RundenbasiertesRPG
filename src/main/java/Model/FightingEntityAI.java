package Model;

import java.util.List;

public class FightingEntityAI {

    /**
     * Executes the Action of a Fighting Entity for one turn.
     */
    public void MakeMove(FightingEntity entity, Battlefield battlefield){
        //At the moment only Enemies will take this path so only Heroes are targets. May Change in the future
        int targetIndex = (int) (Math.random() * battlefield.get_Heroes().size());
        entity.Attack(battlefield.get_Heroes().get(targetIndex));
    }
}
