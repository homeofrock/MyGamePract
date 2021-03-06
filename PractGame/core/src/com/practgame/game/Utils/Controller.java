package com.practgame.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practgame.game.PractGame;


public class Controller {
    private float bsize = 20;
    Viewport viewport;
    public  Stage stage;
    private boolean leftPressed, rightPressed;

    public boolean upPressed;
    public boolean bPressed;

    OrthographicCamera cam;
    ImageButton aButton, leftButton, rightButton;
    public ImageButton bButton;

    private AssetManager manager;

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isBPressed(){
        return bPressed;
    }

    public Controller(AssetManager manager) {
        this.manager = manager;
        cam = new OrthographicCamera();
        viewport = new FitViewport(160, 90, cam);
        stage = new Stage(viewport, PractGame.batch);
        Gdx.input.setInputProcessor(stage);

        Table tableMove = new Table();
        tableMove.left().bottom();

        Table tableAction = new Table();
        tableAction.setFillParent(true);
        tableAction.right().bottom();

        Texture aTexture = manager.get("gc/aDark.png");
        Texture aPressedTexture = manager.get("gc/aLight.png");
        aButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(aTexture)),
                new TextureRegionDrawable(new TextureRegion(aPressedTexture)));
        aButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true; // upButton is an aButton
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Texture rightTexture = manager.get("gc/rightDark.png");
        Texture rightPressedTexture = manager.get("gc/rightLight.png");
        rightButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(rightTexture)),
                new TextureRegionDrawable(new TextureRegion(rightPressedTexture)));
        rightButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Texture leftTexture = manager.get("gc/leftDark.png");
        Texture leftPressedTexture = manager.get("gc/leftLight.png");
        leftButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leftTexture)),
                new TextureRegionDrawable(new TextureRegion(leftPressedTexture)));
        leftButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Texture bTexture = manager.get("gc/bDark.png");
        Texture bPressedTexture = manager.get("gc/bLight.png");
        bButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(bTexture)),
                new TextureRegionDrawable(new TextureRegion(bPressedTexture)));

        bButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bPressed = false;
                bButton.setTouchable(Touchable.enabled);
            }
        });

        tableMove.row().pad(2,2,2,2);
        tableMove.add(leftButton).size(bsize, bsize).padBottom(10);
        tableMove.add();
        tableMove.add(rightButton).size(bsize, bsize).padBottom(10);
        tableMove.pack();

        tableAction.row().pad(2,2,2,2);
        tableAction.add(aButton).size(bsize,bsize).padBottom(10);
        tableAction.add();
        tableAction.add(bButton).size(bsize, bsize).padBottom(10);
        tableAction.pack();

        stage.addActor(tableMove);
        stage.addActor(tableAction);
    }

    public void draw(){
        stage.draw();
    }

// method needed to avoid uncontrollable sticky keys
    public void touchUpAll(){
        bPressed = false;
        upPressed = false;
        leftPressed = false;
        rightPressed = false;

        bButton.getClickListener().cancel();
        aButton.getClickListener().cancel();
        leftButton.getClickListener().cancel();
        rightButton.getClickListener().cancel();
    }

    public void resize(int width, int height){
        viewport.update(width , height);
    }
}
