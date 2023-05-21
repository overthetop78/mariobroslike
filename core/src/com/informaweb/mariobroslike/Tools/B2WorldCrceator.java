package com.informaweb.mariobroslike.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Sprites.Brick;
import com.informaweb.mariobroslike.Sprites.Coin;

public class B2WorldCrceator {
    public B2WorldCrceator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef(); // On crée un BodyDef pour pouvoir créer des objets dans le monde
        PolygonShape shape = new PolygonShape(); // On crée un PolygonShape pour pouvoir créer des objets dans le monde
        FixtureDef fdef = new FixtureDef(); // On crée un FixtureDef pour pouvoir créer des objets dans le monde
        Body body; // On déclare une variable body de type Body pour pouvoir créer des objets dans le monde

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // On récupère le rectangle de l'objet

            bdef.type = BodyDef.BodyType.StaticBody; // On indique que l'objet est statique
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBrosLike.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBrosLike.PPM); // On indique la position de l'objet
            body = world.createBody(bdef); // On crée l'objet dans le monde
            shape.setAsBox(rect.getWidth() / 2 /MarioBrosLike.PPM, rect.getHeight() / 2 / MarioBrosLike.PPM); // On indique la taille de l'objet
            fdef.shape = shape; // On indique la forme de l'objet
            body.createFixture(fdef); // On crée l'objet dans le monde
        }

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // On récupère le rectangle de l'objet

            bdef.type = BodyDef.BodyType.StaticBody; // On indique que l'objet est statique
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBrosLike.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBrosLike.PPM); // On indique la position de l'objet
            body = world.createBody(bdef); // On crée l'objet dans le monde
            shape.setAsBox(rect.getWidth() / 2 /MarioBrosLike.PPM, rect.getHeight() / 2 / MarioBrosLike.PPM); // On indique la taille de l'objet
            fdef.shape = shape; // On indique la forme de l'objet
            body.createFixture(fdef); // On crée l'objet dans le monde
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // On récupère le rectangle de l'objet

            new Brick(world, map, rect); // On crée l'objet dans le monde
           
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // On récupère le rectangle de l'objet

            new Coin(world, map, rect); // On crée l'objet dans le monde
        }
    }

}