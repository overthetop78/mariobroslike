package com.informaweb.mariobroslike.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public class Goomba extends Enemy {

    private float stateTime; // On déclare une variable stateTime de type float pour pouvoir utiliser le temps écoulé depuis le dernier changement d'état du personnage
    private Animation<TextureRegion> walkAnimation; // On déclare une variable walkAnimation de type Animation pour pouvoir utiliser l'animation du personnage
    private Array<TextureRegion> frames; // On déclare une variable frames de type Array<TextureRegion> pour pouvoir utiliser les images de l'animation
    private boolean setToDestroy; // On déclare une variable setToDestroy de type boolean pour pouvoir détruire l'objet
    private boolean destroyed; // On déclare une variable destroyed de type boolean pour pouvoir détruire l'objet

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>(); // On crée un tableau pour stocker les images de l'animation
        for(int i = 0; i < 2; i++) // On parcourt les images de l'animation
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16)); // On ajoute l'image à l'animation
        walkAnimation = new Animation<TextureRegion>(0.4f, frames); // On crée l'animation en indiquant le temps entre chaque image et le tableau contenant les images
        stateTime = 0; // On initialise le temps écoulé depuis le dernier changement d'état du personnage à 0
        setBounds(getX(), getY(), 16 / MarioBrosLike.PPM, 16 / MarioBrosLike.PPM); // On indique la taille de l'image du personnage 
        setToDestroy = false; // On initialise la variable setToDestroy à true
        destroyed = false; // On initialise la variable destroyed à false
    }

    public void update(float dt) {
        stateTime += dt; // On incrémente le temps écoulé depuis le dernier changement d'état du personnage
        if(setToDestroy && !destroyed) { // Si l'objet doit être détruit et qu'il n'est pas déjà détruit
            world.destroyBody(b2body); // On détruit l'objet dans le monde
            destroyed = true; // On indique que l'objet est détruit
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16)); // On indique l'image du personnage
            stateTime = 0; // On initialise le temps écoulé depuis le dernier changement d'état du personnage à 0
        } else if(!destroyed) { // Si l'objet n'est pas détruit     
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2); // On indique la position du personnage
            setRegion(walkAnimation.getKeyFrame(stateTime, true)); // On indique l'image du personnage
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef(); // On crée un BodyDef pour pouvoir créer l'objet dans le monde
        bdef.position.set(64 /MarioBrosLike.PPM,32 /MarioBrosLike.PPM); // On indique la position de l'objet
        bdef.type = BodyDef.BodyType.DynamicBody; // On indique que l'objet est dynamique
        b2body = world.createBody(bdef); // On crée l'objet dans le monde

        FixtureDef fdef = new FixtureDef(); // On crée un FixtureDef pour pouvoir créer l'objet dans le monde
        CircleShape shape = new CircleShape(); // On crée un CircleShape pour pouvoir créer l'objet dans le monde
        shape.setRadius(6/MarioBrosLike.PPM); // On indique la taille de l'objet
        fdef.filter.categoryBits = MarioBrosLike.ENEMY_BIT; // On indique la catégorie de l'objet
        fdef.filter.maskBits = MarioBrosLike.GROUND_BIT | MarioBrosLike.COIN_BIT | MarioBrosLike.BRICK_BIT | MarioBrosLike.ENEMY_BIT | MarioBrosLike.OBJECT_BIT | MarioBrosLike.MARIO_BIT; // On indique les catégories avec lesquelles l'objet peut entrer en collision


        fdef.shape = shape; // On indique la forme de l'objet
        b2body.createFixture(fdef); // On crée l'objet dans le monde

        // Creer la tête du goomba
        PolygonShape head = new PolygonShape(); // On crée un PolygonShape pour pouvoir créer l'objet dans le monde
        Vector2[] vertice = new Vector2[4]; // On crée un tableau pour stocker les vecteurs
        vertice[0] = new Vector2(-5, 8).scl(1 / MarioBrosLike.PPM); // On indique la position du vecteur
        vertice[1] = new Vector2(5, 8).scl(1 / MarioBrosLike.PPM); // On indique la position du vecteur
        vertice[2] = new Vector2(-3, 3).scl(1 / MarioBrosLike.PPM); // On indique la position du vecteur
        vertice[3] = new Vector2(3, 3).scl(1 / MarioBrosLike.PPM); // On indique la position du vecteur
        head.set(vertice); // On indique la forme de l'objet
        fdef.shape = head; // On indique la forme de l'objet
        fdef.restitution = 0.5f; // On indique la restitution de l'objet
        fdef.filter.categoryBits = MarioBrosLike.ENEMY_HEAD_BIT; // On indique la catégorie de l'objet
        b2body.createFixture(fdef).setUserData(this); // On crée l'objet dans le monde
        
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true; // On indique que l'objet doit être détruit
    }
}