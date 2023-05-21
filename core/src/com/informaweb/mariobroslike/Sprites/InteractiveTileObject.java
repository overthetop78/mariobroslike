package com.informaweb.mariobroslike.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.informaweb.mariobroslike.MarioBrosLike;

public abstract class InteractiveTileObject {
    protected World world; // On déclare une variable world de type World pour pouvoir utiliser le monde
    protected TiledMap map; // On déclare une variable map de type TiledMap pour pouvoir utiliser la carte
    protected TiledMapTile tile; // On déclare une variable tile de type TiledMapTile pour pouvoir utiliser la tuile
    protected Rectangle bounds; // On déclare une variable bounds de type Rectangle pour pouvoir utiliser le rectangle
    protected Body body; // On déclare une variable body de type Body pour pouvoir utiliser le corps de l'objet

    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds) {
        this.world = world; // On récupère le monde
        this.map = tiledMap; // On récupère la carte
        this.bounds = bounds; // On récupère le rectangle

        // On crée le corps de l'objet
        BodyDef bdef = new BodyDef(); // On crée un BodyDef pour pouvoir créer l'objet dans le monde
        FixtureDef fdef = new FixtureDef(); // On crée un FixtureDef pour pouvoir créer l'objet dans le monde
        PolygonShape shape = new PolygonShape(); // On crée un PolygonShape pour pouvoir créer l'objet dans le monde

        bdef.type = BodyDef.BodyType.StaticBody; // On indique que l'objet est statique
            bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBrosLike.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBrosLike.PPM); // On indique la position de l'objet
            body = world.createBody(bdef); // On crée l'objet dans le monde
            shape.setAsBox(bounds.getWidth() / 2 /MarioBrosLike.PPM, bounds.getHeight() / 2 / MarioBrosLike.PPM); // On indique la taille de l'objet
            fdef.shape = shape; // On indique la forme de l'objet
            body.createFixture(fdef); // On crée l'objet dans le monde
    }
}