package com.informaweb.mariobroslike.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Scenes.Hud;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet; // On déclare une variable set de type TiledMapTileSet pour pouvoir utiliser les tuiles
    private final int BLANK_COIN = 28; // On déclare une variable BLANK_COIN de type int pour pouvoir utiliser la tuile vide

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds); // On appelle le constructeur de la classe mère InteractiveTileObject
        tileSet = map.getTileSets().getTileSet("tileset_gutter"); // On récupère les tuiles
        fixture.setUserData(this); // On indique que l'objet est un objet interactif
        setCategoryFilter(MarioBrosLike.COIN_BIT); // On indique la catégorie de l'objet
    }

    @Override
    public void onHeadHit() {
       Gdx.app.log("Coin", "Collision");
       if(getCell().getTile().getId() == BLANK_COIN) { // Si la tuile est vide
        MarioBrosLike.manager.get("audio/sounds/bump.wav", com.badlogic.gdx.audio.Sound.class).play(); // On joue le son de la brique
       } else {  // Si la tuile n'est pas vide
        MarioBrosLike.manager.get("audio/sounds/coin.wav", com.badlogic.gdx.audio.Sound.class).play(); // On joue le son de la pièce
       }
       getCell().setTile(tileSet.getTile(BLANK_COIN)); // On indique la tuile vide
         Hud.addScore(100); // On ajoute 100 points au score
    }
    
}