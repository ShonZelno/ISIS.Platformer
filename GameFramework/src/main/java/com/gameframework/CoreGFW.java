package com.gameframework;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CoreGFW extends AppCompatActivity {
    private final float FRAME_BUFFER_WIDTH = 800;
    private final float FRAME_BUFFER_HEIGHT = 600;

    private LoopGFW loopGFW;
    private GraphicsGFW graphicsGFW;
    private TouchListenerGFW touchListenerGFW;
    private Display display;
    private Point sizeDisplay;
    private Bitmap frameBuffer;
    private SceneGFW sceneGFW;
    private float sceneWidth;
    private float sceneHeight;
    private boolean stateOnPause;
    private boolean stateOnResume;

    //действия при создании приложения
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //получение дисплея
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sizeDisplay = new Point();
        display = getWindowManager().getDefaultDisplay();
        display.getRealSize(sizeDisplay);

        //настройка картинки 800х600
        frameBuffer = Bitmap.createBitmap((int) FRAME_BUFFER_WIDTH, (int) FRAME_BUFFER_HEIGHT, Bitmap.Config.ARGB_8888);
        sceneWidth = FRAME_BUFFER_WIDTH / sizeDisplay.x;
        sceneHeight = FRAME_BUFFER_HEIGHT / sizeDisplay.y;

        //создание экземплара цикла, управляющего потоком
        loopGFW = new LoopGFW(this, frameBuffer);
        graphicsGFW = new GraphicsGFW(getAssets(), frameBuffer);

        touchListenerGFW = new TouchListenerGFW(loopGFW, sceneWidth, sceneHeight);

        sceneGFW = getStartScene();
        stateOnPause = false;
        stateOnResume = false;
        setContentView(loopGFW);
    }

    //скрытие панели навигации сматфона при запуске приложения
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public CoreGFW() {

    }

    //метод востановления игры
    public void onResume() {
        super.onResume();
        sceneGFW.resume();
        loopGFW.startGame();
    }

    //метод остановки игры
    public void onPause() {
        super.onPause();
        sceneGFW.pause();
        loopGFW.stopGame();
        stateOnPause = true;
        if (isFinishing()) {
            sceneGFW.dispose();
        }
    }

    //методы возврата графики и сцены
    public GraphicsGFW getGraphicsGFW() {
        return graphicsGFW;
    }

    public void setScene(SceneGFW sceneGFW) {
        if (sceneGFW == null) {
            throw new IllegalArgumentException("Ошибка загрузки сцены");
        }
        this.sceneGFW.pause();
        this.sceneGFW.dispose();
        sceneGFW.resume();
        sceneGFW.update();
        this.sceneGFW = sceneGFW;
    }

    public TouchListenerGFW getTouchListenerGFW() {
        return touchListenerGFW;
    }

    public SceneGFW getCurrentScene() {
        return sceneGFW;
    }

    public SceneGFW getStartScene() {
        return sceneGFW;
    }
}
