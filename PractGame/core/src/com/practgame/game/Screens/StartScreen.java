package com.practgame.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import  com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.practgame.game.PractGame;

public class StartScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720; // using 16/9 ( 1280X20, 1920X1080 )

    Texture backgroundtexture;
    private Stage stage;
    private Texture playTexture, playPressedTexture;
    private Texture settingsTexture, settingsPressedTexture;
    private ImageButton playButton, settingsButton;
    private PractGame maingame;

    private Sound clickSound;

   public  StartScreen(PractGame practGame){
        maingame = practGame;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(false);
        backgroundtexture = maingame.manager.get("mainMenuWall_hdpi_2.png");
        Image background = new Image(backgroundtexture);
        stage.addActor(background);
        playTexture = maingame.manager.get("ui/play.png");
        playPressedTexture = maingame.manager.get("ui/playDown.png");
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playPressedTexture)), new TextureRegionDrawable(new TextureRegion(playTexture)));

        playButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2, Align.center);

        ClickListener playListener  = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.setScreen(maingame.menuLevel);
                String rand ="sound/switch" + Integer.toString((int)(Math.random()*2 + 1)) + ".wav";
                clickSound = maingame.manager.get(rand);
                clickSound.play(maingame.playScreen.soundVolume); // TODO check for error (and below)
            }
        };
        playButton.addListener(playListener);
        stage.addActor(playButton);

        settingsTexture = maingame.manager.get("ui/settings.png");
        settingsPressedTexture = maingame.manager.get("ui/settingsDown.png");
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsTexture)),
                new TextureRegionDrawable(new TextureRegion(settingsPressedTexture)));

        settingsButton.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Align.center);
        ClickListener settingsListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maingame.setScreen(maingame.settingsScreen);
                String rand ="sound/switch" + Integer.toString((int)(Math.random()*2 + 1)) + ".wav";
                clickSound = maingame.manager.get(rand);
                clickSound.play(maingame.playScreen.soundVolume);
            }
        };
        settingsButton.addListener(settingsListener);

        stage.addActor(settingsButton);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        backgroundtexture.dispose();
    }
}
