package com.platformer.scenes;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.gameframework.CoreGFW;
import com.gameframework.SceneGFW;
import com.platformer.R;

public class GameScene extends SceneGFW {

    enum GameState {
        READY, RUNNING, PAUSE, GAMEOVER
    }

    GameState gameState;
    long score = 0;
    SharedPreferences sharedPref = coreGFW.getPreferences(MODE_PRIVATE);
    long previousScore = sharedPref.getLong("score", 0);
    SharedPreferences.Editor editor = sharedPref.edit();

    public GameScene(CoreGFW coreGFW) {
        super(coreGFW);
        gameState = GameState.READY;
    }

    //метод выбора обновления игровой сцены
    @Override
    public void update() {
        if (gameState == GameState.READY) {
            updateStateReady();
        }
        if (gameState == GameState.RUNNING) {
            updateStateRunning();
        }
        if (gameState == GameState.PAUSE) {
            updateStatePause();
        }
        if (gameState == GameState.GAMEOVER) {
            updateStateGameOver();
        }
        saveScore();
    }

    //метод выбора отрисовки игровой сцены
    @Override
    public void drawing() {
        graphicsGFW.clearScene(Color.rgb(55, 170, 60));

        if (gameState == GameState.READY) {
            drawingStateReady();
        }
        if (gameState == GameState.RUNNING) {
            drawingStateRunning();
        }
        if (gameState == GameState.PAUSE) {
            drawingStatePause();
        }
        if (gameState == GameState.GAMEOVER) {
            drawingStateGameOver();
        }

    }

    //методы обновления и отрисовки подготовки к игре
    private void updateStateReady() {
        if (coreGFW.getTouchListenerGFW().getTouchUp(0, sceneHeight, sceneWidth, sceneHeight)) {
            gameState = GameState.RUNNING;
        }
    }

    private void drawingStateReady() {
        graphicsGFW.clearScene(ContextCompat.getColor(coreGFW, R.color.gameBackground));
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_gameBegin), 300, 300, ContextCompat.getColor(coreGFW, R.color.gameText), 60, null);
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_gameBeginConfirm), 135, 550, ContextCompat.getColor(coreGFW, R.color.gameText), 30, null);
    }

    //методы обновления и отрисовки игры
    private void updateStateRunning() {
        score++;
        if (score > Long.MAX_VALUE - 1) {
            score = Long.MAX_VALUE - 1;
        }
        if (coreGFW.getTouchListenerGFW().getTouchUp(725, 585, 85, 30)) {
            coreGFW.setScene(new MainMenuScene(coreGFW));
        }
    }

    private void drawingStateRunning() {
        graphicsGFW.clearScene(ContextCompat.getColor(coreGFW, R.color.gameBackground));
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_gameScore)+" "+ String.valueOf(score), 10, 25, ContextCompat.getColor(coreGFW, R.color.gameScore), 20, null);
        graphicsGFW.drawText("Игра", 330, 300, ContextCompat.getColor(coreGFW, R.color.gameText), 60, null);
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_gameBack), 725, 585, ContextCompat.getColor(coreGFW, R.color.gameText), 20, null);
    }

    private void updateStatePause() {

    }

    private void drawingStatePause() {

    }

    private void updateStateGameOver() {

    }

    private void drawingStateGameOver() {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //метод сохранения игры
    private void saveScore() {
        if (previousScore < score) {
            editor.putLong("score", score);
            editor.apply();
        }
    }
}
