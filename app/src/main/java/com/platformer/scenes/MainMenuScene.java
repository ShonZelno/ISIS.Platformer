package com.platformer.scenes;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;

import com.gameframework.CoreGFW;
import com.gameframework.SceneGFW;
import com.platformer.R;

public class MainMenuScene extends SceneGFW {

    long score;
    SharedPreferences sharedPref = coreGFW.getPreferences(MODE_PRIVATE);

    public MainMenuScene(CoreGFW coreGFW) {
        super(coreGFW);
    }

    @Override
    public void update() {
        if (coreGFW.getTouchListenerGFW().getTouchUp(120, 300, 200, 50)) {
            coreGFW.setScene(new GameScene(coreGFW));
        }
        if (coreGFW.getTouchListenerGFW().getTouchUp(120, 400, 180, 40)) {
            coreGFW.finishAndRemoveTask();
        }
        score = sharedPref.getLong("score", 0);

    }

    @Override
    public void drawing() {
        int colorT = ContextCompat.getColor(coreGFW, R.color.mainMenuText);
        int colorN = ContextCompat.getColor(coreGFW, R.color.mainMenuName);

        graphicsGFW.clearScene(ContextCompat.getColor(coreGFW, R.color.mainMenuBackground));
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_MainMenu_name), 100, 100, colorN, 60, null);
        graphicsGFW.drawLine(0, 150, 800, 150, colorT);
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_MainMenu_begin), 120, 300, colorT, 40, null);
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_MainMenu_result) + " " + score, 120, 350, colorT, 30, null);
        graphicsGFW.drawText(coreGFW.getString(R.string.txt_MainMenu_exit), 120, 400, colorT, 30, null);

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
}
