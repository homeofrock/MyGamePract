package com.practgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.practgame.game.Screens.GunScreen;
import com.practgame.game.Screens.LoadScreen;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PauseScreen;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Screens.SettingsScreen;
import com.practgame.game.Screens.StartScreen;
import com.practgame.game.Sprites.Gun;
import com.practgame.game.Utils.AppPreferences;
import com.practgame.game.Utils.LevelInfo;
import com.practgame.game.Utils.MusicManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PractGame extends Game {
	// defining BitMasks for collision
    public static final short DEFAULT_BIT = 1; // ground
    public static final short PLAYER_BIT = 2; // player
    public static final short RECHARGE_BIT = 4; // recharge - item
    public static final short GUN_BIT = 8; // gun - item
    public static final short ENEMY_BIT = 32;
    public static final short BULLET_BIT = 64;
    public static final short LIGHT_BIT = 16;
    public final static short MASK_LIGHT = DEFAULT_BIT;
	public final static short LIGHT_GROUP = 3;
	public final static short JUMPBLOCK_BIT = 128;

	public static SpriteBatch batch;

	public PlayScreen playScreen;
	public StartScreen startScreen;
	public MenuLevel menuLevel;
	private LoadScreen loadScreen;
	public PauseScreen pauseScreen;
	public SettingsScreen settingsScreen;
	public GunScreen gunScreen;

	// stores the number of a level
	public  Integer levelLine1;
	public Integer levelLine2;
	public Integer levelLine3;
	public int worldType;

	public static float PPM = 100; // pixels per meter

	public ArrayList <LevelInfo> levelList1;
    public ArrayList <LevelInfo> levelList2;
    public ArrayList <LevelInfo> levelList3;

	public AssetManager manager;
	public MusicManager musicManager;

	private Preferences prefs;
	private boolean toMenu;

	 private I18NBundle i18NBundle; // i18n = internationalization

	 public HashMap <String, Gun> gunMap;

	@Override
	public void create() {
		levelList1 = new ArrayList<LevelInfo>();
		levelList2 = new ArrayList<LevelInfo>();
		levelList3 = new ArrayList<LevelInfo>();
		gunMap = new HashMap<String, Gun>();

		getPrefs();

		loadScreen = new LoadScreen(this);
		setScreen(loadScreen);

		manager = loadScreen.manager;;
        musicManager = new MusicManager(manager);
        musicManager.setSound("title.ogg");

        levelLine1 = prefs.getInteger(AppPreferences.PREF_WORLD_1, 0); // saving progress
        levelLine2 = prefs.getInteger(AppPreferences.PREF_WORLD_2, 0);
        levelLine3 = prefs.getInteger(AppPreferences.PREF_WORLD_3, 0);
		batch = new SpriteBatch();
		playScreen = new PlayScreen(this);
		startScreen = new StartScreen(this);
		menuLevel = new MenuLevel(this);
		pauseScreen = new PauseScreen(this);
		settingsScreen = new SettingsScreen(this);
		gunScreen = new GunScreen(gunMap, this);
		toMenu = false;

		//localization, this time english and german are available (only for lobby-guy phrases)
		FileHandle baseFileHandle = Gdx.files.internal("i18n/strings");
		String lang = java.util.Locale.getDefault().toString();
		Locale locale = new Locale(lang); // suitable i18n bundle is chosen automatically (there is also default bundle)
		// i18N == internationalization
		i18NBundle = I18NBundle.createBundle(baseFileHandle, locale);
    }

    public void changeScreen(int worldType){
            switch (worldType){
                case 1:
					if(levelLine1 == levelList1.size()){
                        levelLine1 = 0;
                        playScreen.shotsMade = 0;
						prefs.putInteger(AppPreferences.PREF_SHOTS_1, playScreen.shotsMade);

                        prefs.flush();
                        toMenu = true;
                        musicManager.setSound("title.ogg");
                        this.setScreen(menuLevel);
                    } else {
                        prefs.putInteger(AppPreferences.PREF_WORLD_1, levelLine1);
                        prefs.flush();

                        if(levelLine1 == 0)
							playScreen.shotsMade = 0;


                        playScreen.shotsMade = prefs.getInteger(AppPreferences.PREF_SHOTS_1);

						this.worldType = 1;
						playScreen.setLevel(levelList1.get(levelLine1).mapInfo);
                    }
                    break;

                case 2:
					if(levelLine2 == levelList2.size()){
						levelLine2 = 0;
						playScreen.shotsMade = 0;
						prefs.putInteger(AppPreferences.PREF_SHOTS_2, playScreen.shotsMade);

						prefs.flush();
						toMenu = true;
						musicManager.setSound("title.ogg");
						this.setScreen(menuLevel);
					} else {
						prefs.putInteger(AppPreferences.PREF_WORLD_2, levelLine2);
						prefs.flush();

						if(levelLine2 == 0)
							playScreen.shotsMade = 0;

						playScreen.shotsMade = prefs.getInteger(AppPreferences.PREF_SHOTS_2);

                        this.worldType = 2;
                        playScreen.setLevel(levelList2.get(levelLine2).mapInfo);
					}
                    break;
                case 3:
                    if(levelLine3 == levelList3.size()){
                        levelLine3 = 0;
                        playScreen.shotsMade = 0;
						prefs.putInteger(AppPreferences.PREF_SHOTS_3, playScreen.shotsMade);

						prefs.putInteger(AppPreferences.PREF_WORLD_3, 0);
                        prefs.flush();
                        toMenu = true;
                        musicManager.setSound("title.ogg");
                        this.setScreen(menuLevel);
                    } else {
                        prefs.putInteger(AppPreferences.PREF_WORLD_3, levelLine3);
                        prefs.flush();

                        if(levelLine3 == 0)
                            playScreen.shotsMade = 0;

                        playScreen.shotsMade = prefs.getInteger(AppPreferences.PREF_SHOTS_3);

                        this.worldType = 3;
                        playScreen.setLevel(levelList3.get(levelLine3).mapInfo);
                    }
                    break;
            }
            if(!toMenu)
                setScreen(playScreen);
            else
                toMenu = false;
    }

	private Preferences getPrefs(){
		if (prefs == null)
			prefs = Gdx.app.getPreferences(AppPreferences.PREFS_NAME);
		return prefs;
	}

	public void cleanSafeData(){
		prefs.putInteger(AppPreferences.PREF_WORLD_1, 0);
        prefs.putInteger(AppPreferences.PREF_WORLD_2, 0);
        prefs.putInteger(AppPreferences.PREF_WORLD_3, 0);

		prefs.putBoolean(AppPreferences.PREFS_IS_REDLINE_UNLOCKED, false);
		prefs.putBoolean(AppPreferences.PREFS_IS_INFINITY_UNLOCKED, false);
		prefs.putBoolean(AppPreferences.PREFS_IS_ACCELERATOR_UNLOCKED, false);
		prefs.putBoolean(AppPreferences.PREFS_IS_TPSL2_UNLOCKED, false);

		prefs.putString(AppPreferences.PREFS_GUN, "acr130");
		playScreen.shotsMade = 0;
		prefs.putInteger(AppPreferences.PREF_SHOTS_1, 0);
		prefs.putInteger(AppPreferences.PREF_SHOTS_2, 0);
        prefs.putInteger(AppPreferences.PREF_SHOTS_3, 0);

		prefs.flush(); // yes, one flush() is enough for all put()'s

		lockAllGuns();
        playScreen.player.updateGun();
        levelLine1 = 0;
        levelLine2 = 0;
        levelLine3 = 0;
	}

	public void lockAllGuns(){
		gunMap.get("redLine").lock();
		gunMap.get("infinity").lock();
		gunMap.get("accelerator").lock();
		gunMap.get("tpsl2").lock();
	}

	public I18NBundle getBundle(){
		return i18NBundle;
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
		musicManager.dispose();
		menuLevel.dispose();
		playScreen.dispose();
		settingsScreen.dispose();
		pauseScreen.dispose();
		startScreen.dispose();
		loadScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}
}