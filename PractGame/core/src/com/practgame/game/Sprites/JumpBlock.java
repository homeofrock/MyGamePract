package com.practgame.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;

public class JumpBlock extends InteractiveTileObject {
    public JumpBlock(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds, false, "jumpingBlock");
        setCategoryFilter(PractGame.DEFAULT_BIT);
    }
}
