package com.informaweb.mariobroslike.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public class Mario extends Sprite{
    public enum State { FALLING, JUMPING, STANDING, RUNNING }; // On déclare une variable State de type enum pour pouvoir gérer les états du personnage
    public State currentState; // On déclare une variable currentState de type State pour pouvoir utiliser l'état actuel du personnage
    public State previousState; // On déclare une variable previousState de type State pour pouvoir utiliser l'état précédent du personnage

    public World world; // On déclare une variable world de type World pour pouvoir utiliser le monde
    public Body b2body; // On déclare une variable b2body de type Body pour pouvoir utiliser le corps de l'objet
    private TextureRegion marioStand; // On déclare une variable marioStand de type TextureRegion pour pouvoir utiliser l'image du personnage
    private Animation marioRun; // On déclare une variable marioRun de type Animation pour pouvoir utiliser l'animation du personnage
    private Animation marioJump; // On déclare une variable marioJump de type Animation pour pouvoir utiliser l'animation du personnage 
    private float stateTimer; // On déclare une variable stateTimer de type float pour pouvoir utiliser le temps écoulé depuis le dernier changement d'état du personnage
    private boolean runningRight; // On déclare une variable runningRight de type boolean pour pouvoir utiliser la direction du personnage

    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario")); // On charge l'image du personnage
        this.world = world; // On récupère le monde
        currentState = State.STANDING; // On initialise l'état actuel du personnage à STANDING
        previousState = State.STANDING; // On initialise l'état précédent du personnage à STANDING
        stateTimer = 0; // On initialise le temps écoulé depuis le dernier changement d'état du personnage à 0
        runningRight = true; // On initialise la direction du personnage à droite

        Array<TextureRegion> frames = new Array<TextureRegion>(); // On crée un tableau pour stocker les images de l'animation
        for(int i = 1; i < 4; i++) // On parcourt les images de l'animation
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16)); // On ajoute l'image à l'animation
        marioRun = new Animation(0.1f, frames); // On crée l'animation en indiquant le temps entre chaque image et le tableau contenant les images
        frames.clear(); // On vide le tableau

        for(int i = 4; i < 6; i++) // On parcourt les images de l'animation
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16)); // On ajoute l'image à l'animation
        marioJump = new Animation(0.1f, frames); // On crée l'animation en indiquant le temps entre chaque image et le tableau contenant les images
        frames.clear(); // On vide le tableau

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16); // On récupère l'image du personnage

        defineMario(); // On crée l'objet dans le monde
        setBounds(0, 0, 16 / MarioBrosLike.PPM, 16 / MarioBrosLike.PPM); // On indique la taille de l'image du personnage
        setRegion(marioStand); // On indique l'image du personnage
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2); // On indique la position du personnage
        setRegion(getFrame(dt)); // On indique l'image du personnage
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState(); // On récupère l'état actuel du personnage

        TextureRegion region; // On déclare une variable region de type TextureRegion pour pouvoir utiliser l'image du personnage
        switch(currentState) {
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer); // On récupère l'image du personnage
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true); // On récupère l'image du personnage
                break;
            case FALLING:
            case STANDING:  
            default:
                region = marioStand; // On récupère l'image du personnage
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false); // On retourne l'image du personnage
            runningRight = false; // On indique que le personnage va à gauche
        } else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false); // On retourne l'image du personnage
            runningRight = true; // On indique que le personnage va à droite
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0; // On incrémente le temps écoulé depuis le dernier changement d'état du personnage si l'état actuel est égal à l'état précédent sinon on remet le temps à 0
        previousState = currentState; // On indique que l'état précédent est égal à l'état actuel
        return region; // On retourne l'image du personnage
    }

    public State getState() {
        if(b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
            return State.JUMPING; // On retourne l'état JUMPING si le personnage est en train de sauter
        if(b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
            return State.FALLING; // On retourne l'état FALLING si le personnage est en train de tomber
        if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING; // On retourne l'état RUNNING si le personnage est en train de courir
        else
            return State.STANDING; // On retourne l'état STANDING si le personnage est à l'arrêt
    }

    public void defineMario() {
        BodyDef bdef = new BodyDef(); // On crée un BodyDef pour pouvoir créer l'objet dans le monde
        bdef.position.set(32 /MarioBrosLike.PPM,32 /MarioBrosLike.PPM); // On indique la position de l'objet
        bdef.type = BodyDef.BodyType.DynamicBody; // On indique que l'objet est dynamique
        b2body = world.createBody(bdef); // On crée l'objet dans le monde

        FixtureDef fdef = new FixtureDef(); // On crée un FixtureDef pour pouvoir créer l'objet dans le monde
        CircleShape shape = new CircleShape(); // On crée un CircleShape pour pouvoir créer l'objet dans le monde
        shape.setRadius(6/MarioBrosLike.PPM); // On indique la taille de l'objet

        fdef.shape = shape; // On indique la forme de l'objet
        b2body.createFixture(fdef); // On crée l'objet dans le monde
    }

}