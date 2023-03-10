package Fixed;

import Agents.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BonusRewardTest {

    @Test
    public void testLifespan(){
        BonusReward br1 = new BonusReward(30,30);
        BonusReward br2 = new BonusReward(40,40,100);
        br2.getLifespan();
        assertEquals(100,br2.getLifespan());
        br2.reduceLifespan();
        assertEquals(99,br2.getLifespan());
    }
    @Test
    public void testRetired(){
        BonusReward br1 = new BonusReward(30,30);
        BonusReward br2 = new BonusReward(40,40,100);
        br1.setRetired(true);
        assertEquals(true,br1.isRetired());
    }

}
