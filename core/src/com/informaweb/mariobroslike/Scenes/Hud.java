package com.informaweb.mariobroslike.Scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.informaweb.mariobroslike.MarioBrosLike;

public class Hud implements Disposable{ // Hud est une classe qui permet d'afficher des informations à l'écran (timer, score, etc.)
    public Stage stage; // On déclare une variable stage de type Stage pour pouvoir organiser les éléments
    private Viewport viewport; // On déclare une variable viewport de type Viewport pour pouvoir gérer la vue de la caméra (zoom, etc.) mais qui déforme les images pour qu'elles prennent tout l'écran en gardant les proportions
    
    private Integer worldTimer; // On déclare une variable worldTimer de type Integer pour pouvoir afficher le timer
    private float timeCount; // On déclare une variable timeCount de type float pour pouvoir afficher le timer
    private Integer score; // On déclare une variable score de type Integer pour pouvoir afficher le score

    Label countdownLabel; // On déclare une variable countdownLabel de type Label pour pouvoir afficher le timer
    Label scoreLabel;  // On déclare une variable scoreLabel de type Label pour pouvoir afficher le score
    Label timeLabel; // On déclare une variable timeLabel de type Label pour pouvoir afficher le temps
    Label levelLabel; // On déclare une variable levelLabel de type Label pour pouvoir afficher le niveau
    Label worldLabel; // On déclare une variable worldLabel de type Label pour pouvoir afficher le monde
    Label marioLabel; // On déclare une variable mariolabel de type Label pour pouvoir afficher le nom du personnage

    public Hud(SpriteBatch sb) {
        worldTimer = 300; // On initialise le timer à 300
        timeCount = 0; // On initialise le compteur de temps à 0
        score = 0; // On initialise le score à 0

        viewport = new FitViewport(MarioBrosLike.V_WIDTH, MarioBrosLike.V_HEIGHT, new OrthographicCamera()); // On crée un viewport pour gérer la vue de la caméra (zoom, etc.) mais qui déforme les images pour qu'elles prennent tout l'écran en gardant les proportions
        stage = new Stage(viewport, sb); // On crée un stage pour organiser les éléments

        Table table = new Table(); // On crée un tableau pour organiser les éléments
        table.top(); // On place le tableau en haut de l'écran
        table.setFillParent(true); // On indique que le tableau doit prendre toute la place disponible

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // On crée un label pour afficher le timer
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // On crée un label pour afficher le score
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // On crée un label pour afficher le temps
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // On crée un label pour afficher le niveau
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // On crée un label pour afficher le monde
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // On crée un label pour afficher le nom du personnage

        table.add(marioLabel).expandX().padTop(10); // On ajoute le label marioLabel au tableau en l'étirant horizontalement et en le plaçant en haut du tableau
        table.add(worldLabel).expandX().padTop(10); // On ajoute le label worldLabel au tableau en l'étirant horizontalement et en le plaçant en haut du tableau
        table.add(timeLabel).expandX().padTop(10); // On ajoute le label timeLabel au tableau en l'étirant horizontalement et en le plaçant en haut du tableau
        table.row(); // On passe à la ligne suivante
        table.add(scoreLabel).expandX(); // On ajoute le label scoreLabel au tableau en l'étirant horizontalement
        table.add(levelLabel).expandX(); // On ajoute le label levelLabel au tableau en l'étirant horizontalement
        table.add(countdownLabel).expandX(); // On ajoute le label countdownLabel au tableau en l'étirant horizontalement

        stage.addActor(table); // On ajoute le tableau au stage pour pouvoir l'afficher 

    }

    @Override
    public void dispose() {
       stage.dispose(); // On détruit le stage pour libérer de la mémoire  
    }
}
