package com.gameframework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class GraphicsGFW {
    private AssetManager assetManagerGame;
    private Bitmap frameBufferGame;
    private Canvas canvasGame;
    private Paint paintGame;
    private Bitmap textureGame;

    public GraphicsGFW(AssetManager assetManagerGame, Bitmap frameBufferGame) {
        this.assetManagerGame = assetManagerGame;
        this.frameBufferGame = frameBufferGame;
        this.canvasGame = new Canvas(frameBufferGame);
        this.paintGame = new Paint();
    }

    public void clearScene(int color) {
        canvasGame.drawColor(color);
    }

    public void drawPixel(int x, int y, int color) {
        paintGame.setColor(color);
        canvasGame.drawPoint(x, y, paintGame);
    }

    public void drawLine(int startX, int startY, int stopX, int stopY, int color) {
        paintGame.setColor(color);
        canvasGame.drawLine(startX,startY,stopX,stopY,paintGame);
    }

    public void drawText(String text, int x, int y, int color, int sizeText, Typeface font){
        paintGame.setColor(color);
        paintGame.setTextSize(sizeText);
        paintGame.setTypeface(font);
        canvasGame.drawText(text,x,y,paintGame);
    }

    public int getWidthFrameBuffer(){
        return frameBufferGame.getWidth();
    }

    public int getHeightFrameBuffer(){
        return frameBufferGame.getHeight();
    }
}
