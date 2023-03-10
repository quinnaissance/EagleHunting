package Agents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class EnemyTest {


    @Test
    public void testNumberOfRandomMoves(){
        Enemy staticEnemy = new Enemy(0,0,1,false,25);
        Enemy dynamicEnemy = new Enemy(4,4,2,true,50);
        dynamicEnemy.setCountdown(10);
        assertEquals(0,staticEnemy.getCountdown(), "staticEnemy does not have any random moves");
        assertEquals(10,dynamicEnemy.getCountdown(),"dynamicEnemy will move randomly for 10 moves");
        dynamicEnemy.minusCountdown();
        assertEquals(9,dynamicEnemy.getCountdown(),"dynamicEnemy's random movement decrease by 1");
    }

    @Test
    public void testGetPunishment(){
        Enemy staticEnemy = new Enemy(0,0,1,false,25);
        Enemy dynamicEnemy = new Enemy(4,4,2,true,50);
        assertEquals(25, staticEnemy.getPunishment(), "staticEnemy has 25 punishment value");
        assertEquals(50, dynamicEnemy.getPunishment(), "dynamicEnemy has 50 punishment value");
    }

    @Test
    public void testRetired(){
        Enemy staticEnemy = new Enemy(0,0,1,false,25);
        Enemy dynamicEnemy = new Enemy(4,4,2,true,50);
        dynamicEnemy.setRetired(true);
        assertEquals(false, staticEnemy.isRetired(),"staticEnemy is not retired");
        assertEquals(true, dynamicEnemy.isRetired(),"dynamicEnemy is not retired");
    }

    @Test
    public void testStuck(){
        Enemy staticEnemy = new Enemy(0,0,1,false,25);
        Enemy dynamicEnemy = new Enemy(4,4,2,true,50);
        dynamicEnemy.setStuck(true);
        assertEquals(false,staticEnemy.isStuck(),"staticEnemy is not stuck");
        assertEquals(true,dynamicEnemy.isStuck(),"dynamicEnemy is stuck");
    }

    @Test
    public void testToString(){
        Enemy staticEnemy = new Enemy(0,0,1,false,25);
        Enemy dynamicEnemy = new Enemy(4,4,2,true,50);
        assertEquals("EnemyA",staticEnemy.toString(),"staticEnemy is non-movable enemy");
        assertEquals("EnemyB",dynamicEnemy.toString(),"dynamicEnemy is movable enemy");
    }
}
