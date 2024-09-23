package View.SpriteMangement;

import Model.FightingEntity;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Can be used to access the Metadata to the sprites for a Fighting Entity.
 */
public class SpriteMetaDataGenerator {
    private static String ResourcePath = "";

    private static String get_ResourcePath() {
        if (!ResourcePath.isEmpty()) {
            return ResourcePath;
        }
        var url = ClassLoader.getSystemClassLoader().getResource("");
        URI uri = null;
        try {
            uri = url.toURI();
            ResourcePath = uri.getPath();
            return uri.getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Path to the Sprite for the FightingEntity.
     *
     * @param fightingEntity which should be displayed
     * @return Path to Sprite.
     */
    public static String get_SpritePath(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
                return get_ResourcePath() + "/sprite-sheet-warrior-240x104.png";

            case Mage:
                return get_ResourcePath() + "/sprite-sheet-mage-151x100.png";

            case Bowman:
                return get_ResourcePath() + "/sprite-sheet-bowman-158x173.png";


            case Wizard:
                return get_ResourcePath() + "/sprite-sheet-wizard-161x106.png";

            case Paladin:
                return get_ResourcePath() + "/sprite-sheet-paladin-249x100.png";

            case Boar:
                return get_ResourcePath() + "/sprite-sheet-boar-239x178.png";

            case Mimic:
                return get_ResourcePath() + "/sprite-sheet-mimic-231x172.png";

            case Ghost:
                return get_ResourcePath() + "/sprite-sheet-ghost-112x93.png";

            case Mushroom:
                return get_ResourcePath() + "/sprite-sheet-mushroom-192x163.png";

            default:
                return "";
        }
    }

    /**
     * Returns how many columns the Sprite for the provided FightingEntity has.
     *
     * @param fightingEntity which fighting entity
     * @return column count
     */
    public static int get_ColumnCount(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
            case Mage:
                return 5;

            case Bowman:
                return 8;

            case Wizard:
            case Paladin:
                return 6;

            case Boar:
            case Mimic:
                return 6;

            default:
                return 5;
        }
    }

    /**
     * Returns how many rows the Sprite for the provided FightingEntity has.
     *
     * @param fightingEntity which fighting entity
     * @return row count
     */
    public static int get_RowCount(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
            case Mage:
                return 4;

            case Bowman:
                return 6;

            case Wizard:
                return 6;
            case Paladin:
                return 7;

            case Boar:
                return 6;
            case Mimic:
                return 5;

            default:
                return 5;
        }
    }

    /**
     * Returns how many Spriteparts the Sprite for the provided FightingEntity has.
     *
     * @param fightingEntity which fighting entity
     * @return sprite part count
     */
    public static int get_SpriteCount(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
                return 18;

            case Mage:
                return 16;

            case Bowman:
                return 46;

            case Wizard:
                return 36;

            case Paladin:
                return 40;

            case Boar:
                return 35;

            case Mimic:
                return 25;

            default:
                return 0;
        }
    }

    /**
     * Returns Range of Idle Animation
     *
     * @param fightingEntity which fighting entity
     * @return Range of Idle Animation
     */
    public static SpriteSheet.Range get_IdleRange(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
                return new SpriteSheet.Range(0, 5);
            case Mage:
                return new SpriteSheet.Range(0, 5);
            case Bowman:
                return new SpriteSheet.Range(0, 8);
            case Wizard:
                return new SpriteSheet.Range(0, 6);
            case Paladin:
                return new SpriteSheet.Range(0, 5);
            case Boar:
                return new SpriteSheet.Range(0, 6);
            case Mimic:
                return new SpriteSheet.Range(0, 6);
            case Ghost:
                return new SpriteSheet.Range(0, 5);
            case Mushroom:
                return new SpriteSheet.Range(0, 5);
            default:
                return new SpriteSheet.Range(0, 0);
        }
    }

    /**
     * Returns Range of Idle Animation
     *
     * @param fightingEntity which fighting entity
     * @return Range of Idle Animation
     */
    public static SpriteSheet.Range get_AttackingRange(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
                return new SpriteSheet.Range(7, 13);
            case Mage:
                return new SpriteSheet.Range(6, 14);
            case Bowman:
                return new SpriteSheet.Range(9, 17);
            case Wizard:
                return new SpriteSheet.Range(16, 36);
            case Paladin:
                return new SpriteSheet.Range(12, 19);
            case Boar:
                return new SpriteSheet.Range(7, 19);
            case Mimic:
                return new SpriteSheet.Range(7, 12);
            case Ghost:
                return new SpriteSheet.Range(6, 11);
            case Mushroom:
                return new SpriteSheet.Range(6, 21);
            default:
                return new SpriteSheet.Range(0, 0);
        }
    }

    /**
     * Returns Range of Idle Animation
     *
     * @param fightingEntity which fighting entity
     * @return Range of Idle Animation
     */
    public static SpriteSheet.Range get_AttackedRange(FightingEntity fightingEntity) {
        switch (fightingEntity.get_Type()) {
            case Warrior:
                return new SpriteSheet.Range(13, 19);
            case Mage:
                //return new SpriteSheet.Range(0,5);
            case Bowman:
                //return new SpriteSheet.Range(0,8);
            case Wizard:
                //return new SpriteSheet.Range(0,6);
            case Paladin:
                //return new SpriteSheet.Range(0,5);
            case Boar:
                return new SpriteSheet.Range(5, 9);
            case Mimic:
                //return new SpriteSheet.Range(0,6);
            default:
                return new SpriteSheet.Range(0, 0);
        }
    }

    public static String get_BackgroundPath() {
        return get_ResourcePath() + "/background4.png";
    }
}
