package com.gameframework;

public abstract class SceneGFW {
    public CoreGFW coreGFW;
    public int sceneWidth;
    public int sceneHeight;
    public GraphicsGFW graphicsGFW;

    public SceneGFW(CoreGFW coreGFW) {
        this.coreGFW = coreGFW;
        sceneWidth = coreGFW.getGraphicsGFW().getWidthFrameBuffer();
        sceneHeight = coreGFW.getGraphicsGFW().getHeightFrameBuffer();
        graphicsGFW = coreGFW.getGraphicsGFW();
    }

    public abstract void update();

    public abstract void drawing();

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
