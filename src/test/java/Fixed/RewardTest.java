package Fixed;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RewardTest {
        @Test
        public void getScore(){
            Reward reward = new Reward(20,20,20);
            Reward r = new Reward(30,30);
            assertEquals(20,20,reward.getScore());
            assertEquals(30,30,r.getScore());

        }

    }
