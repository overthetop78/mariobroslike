package com.informaweb.mariobroslike.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Sprites.Enemy;
import com.informaweb.mariobroslike.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA(); // On récupère le premier objet en contact
        Fixture fixB = contact.getFixtureB(); // On récupère le deuxième objet en contact

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits; // On récupère la catégorie des deux objets en contact

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){ // Si un des deux objets est la tête du personnage
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB; // On récupère l'objet qui a la tête en contact avec un autre objet
            Fixture object = head == fixA ? fixB : fixA; // On récupère l'autre objet

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){ // Si l'autre objet est un objet interactif
                ((InteractiveTileObject) object.getUserData()).onHeadHit(); // On appelle la méthode onHeadHit() de l'objet interactif
            }
        }

        switch(cDef) {
            case MarioBrosLike.ENEMY_HEAD_BIT | MarioBrosLike.MARIO_BIT: // Si le personnage touche la tête d'une brique
                if(fixA.getFilterData().categoryBits == MarioBrosLike.ENEMY_HEAD_BIT) {
                    ((Enemy)fixA.getUserData()).hitOnHead(); // On appelle la méthode hitOnHead() de l'ennemi
                } else if(fixB.getFilterData().categoryBits == MarioBrosLike.ENEMY_HEAD_BIT) {
                    ((Enemy)fixB.getUserData()).hitOnHead(); // On appelle la méthode hitOnHead() de l'ennemi
                }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }
    
}