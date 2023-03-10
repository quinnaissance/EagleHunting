package Display;

import Display.Assets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AssetTest {

    @Test
    public void testAssetMapLoad() {
        assertNotEquals("", Assets.map.get("MainCharacter"));
    }


}
