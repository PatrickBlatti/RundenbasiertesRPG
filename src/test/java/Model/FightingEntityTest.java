package Model;

import static org.junit.jupiter.api.Assertions.*;

class FightingEntityTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void get_AttackValue() {
//        var fe = new Hero(FightingEntity.Type.Warrior, 100);
//        int av = fe.get_AttackValue();
//        assertEquals(20, av);
    }

    @org.junit.jupiter.api.Test
    void get_DefenseValue() {
    }

    @org.junit.jupiter.api.Test
    void get_HitPoints() {
        var fe = new Hero(FightingEntity.Type.Warrior, 100);
        int av = fe.get_HitPoints();
        assertEquals(100, av);
    }

    @org.junit.jupiter.api.Test
    void set_maxHitPoints() {
    }

    @org.junit.jupiter.api.Test
    void get_maxHitPoints() {
    }

    @org.junit.jupiter.api.Test
    void reduceHitpoints() {
    }

    @org.junit.jupiter.api.Test
    void attack() {
    }

    @org.junit.jupiter.api.Test
    void get_Type() {
    }
}