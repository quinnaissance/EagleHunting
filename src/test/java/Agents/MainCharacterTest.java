package Agents;
//import com.sun.tools.javac.Game.Main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainCharacterTest {


    @BeforeEach
    public void initializeMainCharacters(){
        MainCharacter mainCharacter = new MainCharacter(0,0);
        MainCharacter secondaryMainCharacter = new MainCharacter(5,5,50,150);
    }

    @Test
    public void testGetHealth(){
        MainCharacter mainCharacter = new MainCharacter(0,0);
        MainCharacter secondaryMainCharacter = new MainCharacter(5,5,50,150);
        assertEquals(100,mainCharacter.getHealth(),"mainCharacter has 100 hp");
        assertEquals(50, secondaryMainCharacter.getHealth(), "secondMainCharacter has 50 hp");
    }

    @Test
    public void testAddCharacterScore(){
        MainCharacter mainCharacter = new MainCharacter(0,0);
        MainCharacter secondaryMainCharacter = new MainCharacter(5,5,50,150);
        secondaryMainCharacter.addCharacterScore(50);
        assertEquals(0, mainCharacter.getCharacterScore(),"mainCharacter has 0 points");
        assertEquals(50,secondaryMainCharacter.getCharacterScore(), "SecondMainCharacter gained 50 points");
    }

    @Test
    public void testSetMaxHealth(){
        MainCharacter mainCharacter = new MainCharacter(0,0);
        MainCharacter secondaryMainCharacter = new MainCharacter(5,5,50,150);
        secondaryMainCharacter.setMaxHealth(23);
        assertEquals(23, secondaryMainCharacter.getMaxHealth(), "SecondMainCharacter health changed to 23");
    }

    @Test
    public void testSetHealth(){
        MainCharacter mainCharacter = new MainCharacter(0,0);
        MainCharacter secondaryMainCharacter = new MainCharacter(5,5,50,150);
        mainCharacter.setHealth(132);
        assertEquals(132, mainCharacter.getHealth(), "MainCharacter health changed to 132");
    }

}
