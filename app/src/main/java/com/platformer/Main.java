package com.platformer;

import com.gameframework.CoreGFW;
import com.gameframework.SceneGFW;
import com.platformer.scenes.MainMenuScene;

public class Main extends CoreGFW {
    public SceneGFW getStartScene(){
        return  new MainMenuScene(this);
    }
}