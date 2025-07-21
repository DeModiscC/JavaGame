package com.Modisc.javagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerTexture;
    float x, y;
    float speed = 100; // Pixel pro Sekunde

    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;


    @Override
    public void create() {
        batch = new SpriteBatch();
        playerTexture = new Texture("char_walk_right.gif");
        x = 100;
        y = 100;

        // Kamera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Map laden
        map = new TmxMapLoader().load("TestKarte.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        //spielerbewegung
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    y += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  y -= speed * delta;

        //Kamera zentriert auf den Spieler
        camera.position.set(x + playerTexture.getWidth() / 2f, y + playerTexture.getHeight() / 2f, 0);
        camera.update();

        // Bildschirm leeren
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //Map zeichnen
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Kamera auf SpriteBatch anwenden
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(playerTexture, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
    }
}
