package com.informaweb.mariobroslike.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    protected PlayScreen screen; // On déclare une variable screen de type PlayScreen pour pouvoir utiliser l'écran
    protected World world; // On déclare une variable world de type World pour pouvoir utiliser le monde
    public Body b2body; // On déclare une variable b2body de type Body pour pouvoir utiliser le corps de l'objet
    

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld(); // On récupère le monde
        this.screen = screen; // On récupère l'écran
        setPosition(x, y); // On indique la position du personnage   
        defineEnemy(); // On crée l'objet dans le monde 
    }

    protected abstract void defineEnemy(); // On déclare une méthode defineEnemy() pour pouvoir créer l'objet dans le monde
    
    public abstract void hitOnHead(); // On déclare une méthode hitOnHead() pour pouvoir gérer la collision avec la tête du personnage
}