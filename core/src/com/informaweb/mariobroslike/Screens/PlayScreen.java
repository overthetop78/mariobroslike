package com.informaweb.mariobroslike.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.informaweb.mariobroslike.MarioBrosLike;
import com.informaweb.mariobroslike.Scenes.Hud;
import com.informaweb.mariobroslike.Sprites.Goomba;
import com.informaweb.mariobroslike.Sprites.Mario;
import com.informaweb.mariobroslike.Tools.B2WorldCrceator;
import com.informaweb.mariobroslike.Tools.WorldContactListener;

public class PlayScreen implements Screen {

    private MarioBrosLike game; // On déclare une variable game de type MarioBrosLike pour pouvoir utiliser le SpriteBatch (dessiner des images)
    private TextureAtlas atlas; // On déclare une variable atlas de type TextureAtlas pour pouvoir utiliser les images

    private OrthographicCamera gameCam; // On déclare une variable gameCam de type OrthographicCamera pour pouvoir gérer la caméra (position, etc.)
    private Viewport gamePort; // On déclare une variable gamePort de type Viewport pour pouvoir gérer la vue de la caméra (zoom, etc.) 
    private Hud hud; // On déclare une variable hud de type Hud pour pouvoir afficher des informations à l'écran (timer, score, etc.)
    private TmxMapLoader mapLoader; // On déclare une variable mapLoader de type TmxMapLoader pour pouvoir charger la carte
    private TiledMap map; // On déclare une variable map de type TiledMap pour pouvoir utiliser la carte
    private OrthogonalTiledMapRenderer renderer; // On déclare une variable renderer de type OrthogonalTiledMapRenderer pour pouvoir afficher la carte
    private World world; // On déclare une variable world de type World pour pouvoir utiliser le monde
    private Box2DDebugRenderer b2dr; // On déclare une variable b2dr de type Box2DDebugRenderer pour pouvoir afficher le monde
    
    private Mario player; // On déclare une variable player de type Mario pour pouvoir utiliser le personnage 
    private Goomba goomba; // On déclare une variable goomba de type Goomba pour pouvoir utiliser l'ennemi Goomba

    private Music music; // On déclare une variable music de type Music pour pouvoir utiliser la musique du stage 1




    public PlayScreen(MarioBrosLike game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack"); // On charge les images

        this.game = game; // On récupère la classe MarioBrosLike pour pouvoir utiliser le SpriteBatch (dessiner des images) 
        
        gameCam = new OrthographicCamera(); // On initialise la caméra 
        // gamePort = new ScreenViewport(800,480,gameCam); // ScreenViewport est une classe de libGDX qui permet de gérer la vue de la caméra (zoom, etc.) mais qui garde les images à l'échelle (pas de déformation)
        // gamePort = new StretchViewport(0, 0, gameCam); // StretchViewport est une classe de libGDX qui permet de gérer la vue de la caméra (zoom, etc.) mais qui déforme les images pour qu'elles prennent tout l'écran (pas de bordures noires mais déformation des images)
        gamePort = new FitViewport(MarioBrosLike.V_WIDTH / MarioBrosLike.PPM ,MarioBrosLike.V_HEIGHT / MarioBrosLike.PPM,gameCam); // FitViewport est une classe de libGDX qui permet de gérer la vue de la caméra (zoom, etc.) mais qui déforme les images pour qu'elles prennent tout l'écran en gardant les proportions (bordures noires mais pas de déformation des images) 
        hud = new Hud(game.batch); // On initialise le Hud pour pouvoir afficher des informations à l'écran (timer, score, etc.)
        mapLoader = new TmxMapLoader(); // On initialise le mapLoader pour pouvoir charger la carte
        map = mapLoader.load("level1.tmx"); // On charge la carte
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBrosLike.PPM); // On initialise le renderer pour pouvoir afficher la carte en indiquant la carte et le ratio (1 / MarioBrosLike.PPM) pour convertir les pixels en mètres
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0); // On centre la caméra sur le milieu de l'écran
        world = new World(new Vector2(0, -10),true); // On initialise le monde
        b2dr = new Box2DDebugRenderer(); // On initialise le b2dr pour pouvoir afficher le monde

        new B2WorldCrceator(this); // On crée les objets dans le monde à partir de la carte (map)

        player = new Mario(this); // On initialise le personnage

        world.setContactListener(new WorldContactListener());

        music = MarioBrosLike.manager.get("audio/music/mario_music.ogg", Music.class); // On récupère la musique du stage 1
        music.setLooping(true); // On indique que la musique doit être jouée en boucle
        music.play(); // On joue la musique

        goomba = new Goomba(this, 0.32f, 0.32f); // On initialise l'ennemi Goomba
        
    }

    public TextureAtlas getAtlas() {
        return atlas; // On retourne les images
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {
      if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) // Si on appuie sur la flèche du haut
        player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true); // On applique une impulsion vers le haut (0, 4f) sur le personnage (player.b2body
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) // Si on appuie sur la flèche de droite et que la vitesse horizontale du personnage est inférieure ou égale à 2 
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true); // On applique une impulsion vers la droite (0.1f, 0) sur le personnage (player.b2body)
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) // Si on appuie sur la flèche de gauche et que la vitesse horizontale du personnage est supérieure ou égale à -2
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true); // On applique une impulsion vers la gauche (-0.1f, 0) sur le personnage (player.b2body)
        
    }

    
    public void update(float dt) {
        handleInput(dt); // On gère les entrées (clavier, etc.)

        world.step(1/60f, 6, 2); // On met à jour le monde 

        player.update(dt); // On met à jour le personnage
        goomba.update(dt); // On met à jour l'ennemi Goomba
        hud.update(dt); // On met à jour le Hud
        
        gameCam.position.x = player.b2body.getPosition().x; // On centre la caméra sur le personnage

        gameCam.update(); // On met à jour la caméra
        renderer.setView(gameCam); // On indique au renderer d'utiliser la caméra pour afficher la carte
    }

    @Override
    public void render(float delta) {
        update(delta); // On met à jour le jeu

        Gdx.gl.glClearColor(1, 0, 0, 1); // On efface l'écran en rouge
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // On efface l'écran GL_COLOR_BUFFER_BIT (couleur) 

        renderer.render(); // On affiche la carte

        b2dr.render(world, gameCam.combined); // On affiche le monde

        game.batch.setProjectionMatrix(gameCam.combined); // On indique au SpriteBatch (game.batch) d'utiliser la caméra (gameCam) pour dessiner les images
        game.batch.begin(); // On commence à dessiner
        player.draw(game.batch); // On dessine le personnage
        goomba.draw(game.batch); // On dessine l'ennemi Goomba
        game.batch.end(); // On arrête de dessiner

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); // On indique au SpriteBatch (game.batch) d'utiliser la caméra du Hud (hud.stage.getCamera()) pour dessiner les images
        hud.stage.draw(); // On dessine le stage du Hud (hud.stage)

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height); // On met à jour la vue de la caméra (gamePort) en fonction de la taille de l'écran
    }

    public TiledMap getMap() {
        return map; // On retourne la carte
    }

    public World getWorld() {
        return world; // On retourne le monde
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        map.dispose(); // On détruit la carte
        renderer.dispose(); // On détruit le renderer
        world.dispose(); // On détruit le monde
        b2dr.dispose(); // On détruit le b2dr
        hud.dispose(); // On détruit le Hud
    }
    
}