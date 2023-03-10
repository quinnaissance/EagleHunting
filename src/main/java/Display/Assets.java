package Display;

import java.util.HashMap;
import java.util.Map;

/**
 * Links different MazeObjects to their assets (sprite images, animations, etc.)
 */
public class Assets {

    // Information
    public static final String GAME_TITLE = "Eagle Hunting";
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final String GAME_ICON = "resources/Image/icon.png";
    public static final String MENU_FONT = "resources/Fonts/woodpecker.otf";

    // Locations of game assets
    public static final String MAZE_FONT = "resources/Fonts/consola.ttf";
    public static final String MAZE_FONT_8BIT = "resources/Fonts/8bit.ttf";
    public static final String MAZE_BGM = "resources/Audio/sea_shanty_2.mp3";
    public static final String MAZE_BG = "resources/Image/eagle.jpg";

    // SFX
    public static final String MAZE_SFX_BONUS_REVEAL = "resources/Audio/bonus.wav";
    public static final String MAZE_SFX_BONUS_GET = "resources/Audio/bonusGet.wav";
    public static final String MAZE_SFX_ENDTILE = "resources/Audio/endtile.wav";
    public static final String MAZE_SFX_HIT = "resources/Audio/hit.wav";
    public static final String MAZE_SFX_LOSE = "resources/Audio/lose.wav";
    public static final String MAZE_SFX_CLICK = "resources/Audio/click.wav";
    public static final String MAZE_SFX_ITEM = "resources/Audio/item.wav";
    public static final String MAZE_SFX_WIN = "resources/Audio/win.wav";


    public static final String INTROPAGE_BG = "resources/Image/IntroPage_bg.png";
    public static final String PAUSEPAGE_BG = "resources/Image/PausePage_bg.png";

    public static final String LOSEPAGE_BG = "resources/Image/EndPage_bg.png";
    public static final String LOSEPAGE_FONT = "resources/Fonts/kraut.ttf";

    public static final String WINPAGE_BG = "resources/Image/WinPage_bg.png";
    public static final String WINPAGE_FONT = "resources/Fonts/signpainter.ttf";

    public static final String MO_WALL_BLOCK = "resources/Image/tree.png";
    public static final String MO_MAINCHARACTER = "resources/Image/eagle3.png";
    public static final String MO_ENEMY = "resources/Image/hunter.png";
    public static final String MO_ENEMYA = "resources/Image/enemyA_2.png";
    public static final String MO_ENEMYB = "";
    public static final String MO_REWARD = "resources/Image/egg2.png";
    public static final String MO_BONUSREWARD = "resources/Image/chick.png";
    public static final String MO_EVENTTILE = "resources/Image/EventTile.png";
    public static final String MO_START = "resources/Image/nest2.png";
    public static final String MO_FINISH = "resources/Image/endBlock.png";

    public static final String LVL_TEST = "resources/Levels/lvlTest2.txt";
    public static final String LVL_TEST_TWO = "resources/Levels/lvlTest3.txt";
    public static final String LVL_TEST_THREE = "resources/Levels/lvlTest4.txt";
    public static final String LVL_TUTORIAL = "resources/Levels/lvlTutorial.txt";
    public static final String LVL_ONE = "resources/Levels/lvlOne.txt";
    public static final String LVL_ONE_TEST = "resources/Levels/lvlOne_test.txt";
    public static final String LVL_ONE_BG = "resources/Image/levelBackground.png";



    // HashMap mapping MazeObject types to image resources
    public static Map<String, String> map;
    static {
        map = new HashMap<>();

        map.put("Wall", MO_WALL_BLOCK);

        map.put("MainCharacter", MO_MAINCHARACTER);
        map.put("Enemy", MO_ENEMY);
        map.put("EnemyA", MO_ENEMYA);
        map.put("EnemyB", MO_ENEMY);
        map.put("Reward", MO_REWARD);
        map.put("BonusReward", MO_BONUSREWARD);

        map.put("EventTile", MO_EVENTTILE);
        map.put("StartTile", MO_START);
        map.put("EndTile", MO_FINISH);
    }
}
