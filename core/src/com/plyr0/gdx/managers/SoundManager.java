package com.plyr0.gdx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Map<String, Sound> sounds = new HashMap<String, Sound>();

    public static void load(String path, String name) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(name, sound);
        //Gdx.app.log("SoundManager", "Loaded " + name + " : " + path);
    }

    public static void loadAll() {
        FileHandle dir = Gdx.files.internal("sounds");
        for (FileHandle f : dir.list()) {
            String name = f.nameWithoutExtension();
            load(f.path(), name);
        }
    }

    public static void play(String name) {
        sounds.get(name).play();
    }

    public static void loop(String name) {
        sounds.get(name).loop();
    }

    public static void stop(String name) {
        sounds.get(name).stop();
    }

    public static void stopAll() {
        for (Sound s : sounds.values()) {
            s.stop();
        }
    }
}
