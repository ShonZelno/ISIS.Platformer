package com.gameframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoopGFW extends SurfaceView implements Runnable {
    //константы задающие количество и время отрисовки кадров
    private final float FPS = 60;
    private final float SECOND = 1000000000;
    private final float UPDATE_TIME = SECOND / FPS;

    private boolean running = false;

    //поток
    Thread gameThread = null;
    //создание экземпларя класса ядра
    CoreGFW coreGFW;
    //переменные для отрисовки
    Bitmap frameBuffer;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Rect rect;

    //конструктор
    public LoopGFW(CoreGFW coreGFW, Bitmap frameBuffer) {
        super(coreGFW);
        this.frameBuffer = frameBuffer;
        this.coreGFW = coreGFW;
        surfaceHolder = getHolder();
        rect = new Rect();
        canvas = new Canvas();
    }

    //поддержание частоты кадров
    @Override
    public void run() {
        float lastTime = System.nanoTime();

        while (running) {
            float nowTime = System.nanoTime();
            float elapsedTime = nowTime - lastTime;
            lastTime = nowTime;
            if (elapsedTime > UPDATE_TIME) {
                updateGame();
                drawingGame();
            }
        }
    }

    //метод запуска игры
    public void startGame() {
        if (running) {
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //метод остановки игры
    public void stopGame() {
        if (!running) {
            return;
        }
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //метод обновления экрана
    private void updateGame() {
        coreGFW.getCurrentScene().update();
    }

    //метод отрисовки нового экрана
    private void drawingGame() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.getClipBounds(rect);
            canvas.drawBitmap(frameBuffer, null, rect, null);
            coreGFW.getCurrentScene().drawing();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
