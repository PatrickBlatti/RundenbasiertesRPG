package Factories;

import Services.IFightingEntitiesService;

/**
 * Provides Access to abstracted Services for creation, get from outside, save to outside functions.
 */
public  class ServiceFactory {


    private static IFightingEntitiesService _FightingEntitiesService;
    /**
     * Service for getting Heroes and Enemies.
     */
    public static IFightingEntitiesService get_FightingEntitiesService(){
        if (_FightingEntitiesService == null){
            _FightingEntitiesService = new Services.FightingEntitiesService_Develop();
        }
        return _FightingEntitiesService;
    }
}
