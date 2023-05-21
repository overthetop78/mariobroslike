package com.informaweb.mariobroslike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.informaweb.mariobroslike.Screens.PlayScreen;

public class MarioBrosLike extends Game { // Game est une classe de libGDX qui permet de gérer les écrans
	public static final int V_WIDTH = 400; // Largeur de l'écran
	public static final int V_HEIGHT = 208; // Hauteur de l'écran
	public static final float PPM = 100; // Pixels Par Mètre (pour convertir les pixels en mètres)

	public SpriteBatch batch; // SpriteBatch est une classe de libGDX qui permet de dessiner des images
	
	@Override
	public void create () {
		batch = new SpriteBatch(); // On initialise le SpriteBatch pour pouvoir dessiner des images
		setScreen(new PlayScreen(this)); // On démarre le jeu en affichant l'écran de jeu (PlayScreen) en lui passant la classe MarioBrosLike (this) pour pouvoir utiliser le SpriteBatch
	}

	@Override
	public void render () {
		super.render(); // On appelle la méthode render() de la classe Game qui va appeler la méthode render() de l'écran actif (PlayScreen) 
	}
	
}
