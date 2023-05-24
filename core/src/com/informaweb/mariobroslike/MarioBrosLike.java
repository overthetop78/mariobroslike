package com.informaweb.mariobroslike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public class MarioBrosLike extends Game { // Game est une classe de libGDX qui permet de gérer les écrans
	public static final int V_WIDTH = 400; // Largeur de l'écran
	public static final int V_HEIGHT = 208; // Hauteur de l'écran
	public static final float PPM = 100; // Pixels Par Mètre (pour convertir les pixels en mètres)

	public static final short GROUND_BIT = 1; // On déclare une variable GROUND_BIT de type short pour pouvoir utiliser le sol
	public static final short MARIO_BIT = 2; // On déclare une variable MARIO_BIT de type short pour pouvoir utiliser le personnage
	public static final short BRICK_BIT = 4; // On déclare une variable BRICK_BIT de type short pour pouvoir utiliser les briques
	public static final short COIN_BIT = 8; // On déclare une variable COIN_BIT de type short pour pouvoir utiliser les pièces
	public static final short DESTROYED_BIT = 16; // On déclare une variable DESTROYED_BIT de type short pour pouvoir utiliser les objets détruits
	public static final short OBJECT_BIT = 32; // On déclare une variable OBJECT_BIT de type short pour pouvoir utiliser les objets
	public static final short ENEMY_BIT = 64; // On déclare une variable ENEMY_BIT de type short pour pouvoir utiliser les ennemis
	public static final short ENEMY_HEAD_BIT = 128; // On déclare une variable ENEMY_HEAD_BIT de type short pour pouvoir utiliser la tête des ennemis

	public SpriteBatch batch; // SpriteBatch est une classe de libGDX qui permet de dessiner des images
	

	public static AssetManager manager; // AssetManager est une classe de libGDX qui permet de gérer les ressources (images, sons, etc.)


	@Override
	public void create () {
		batch = new SpriteBatch(); // On initialise le SpriteBatch pour pouvoir dessiner des images
		manager = new AssetManager(); // On initialise l'AssetManager pour pouvoir gérer les ressources (images, sons, etc.)
		manager.load("audio/music/mario_music.ogg", Music.class); // On charge la musique du stage 1
		manager.load("audio/sounds/coin.wav", Sound.class); // On charge le son de la pièce
		manager.load("audio/sounds/bump.wav", Sound.class); // On charge le son de la brique
		manager.load("audio/sounds/breakblock.wav", Sound.class); // On charge le son de la brique cassée
		manager.finishLoading(); // On attend que les ressources soient chargées

		setScreen(new PlayScreen(this)); // On démarre le jeu en affichant l'écran de jeu (PlayScreen) en lui passant la classe MarioBrosLike (this) pour pouvoir utiliser le SpriteBatch
	}

	@Override
	public void render () {
		super.render(); // On appelle la méthode render() de la classe Game qui va appeler la méthode render() de l'écran actif (PlayScreen) 
		
	}

	public void dispose() {
		super.dispose(); // On appelle la méthode dispose() de la classe Game qui va appeler la méthode dispose() de l'écran actif (PlayScreen)
		manager.dispose(); // On libère la mémoire utilisée par l'AssetManager
	}
	
}
