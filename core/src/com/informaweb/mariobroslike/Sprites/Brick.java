package com.informaweb.mariobroslike.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Scenes.Hud;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds); // On appelle le constructeur de la classe mère InteractiveTileObject
        fixture.setUserData(this); // On indique que l'objet est un objet interactif
        setCategoryFilter(MarioBrosLike.BRICK_BIT); // On indique la catégorie de l'objet 
    }

    @Override
    public void onHeadHit() { // On redéfinit la méthode onHeadHit() de la classe mère InteractiveTileObject pour pouvoir gérer la collision avec la tête du personnage 
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(MarioBrosLike.DESTROYED_BIT); // On indique la catégorie de l'objet pour pouvoir le détruire (voir la méthode setCategoryFilter() de la classe mère InteractiveTileObject)
        getCell().setTile(null); // On supprime la tuile de la carte
        Hud.addScore(200); // On ajoute 200 points au score
        MarioBrosLike.manager.get("audio/sounds/breakblock.wav", com.badlogic.gdx.audio.Sound.class).play(); // On joue le son de la brique cassée
    }
    
}