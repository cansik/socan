package ch.bildspur.socan.sketch;

import processing.core.PApplet;
import processing.opengl.PJOGL;

/**
 * Created by cansik on 16/08/16.
 */
public class RenderSketch extends PApplet {

    final static int OUTPUT_WIDTH = 640;
    final static int OUTPUT_HEIGHT = 480;

    final static int FRAME_RATE = 30;

    SoundCharacterAnalyser socan;

    public void settings(){
        size(OUTPUT_WIDTH, OUTPUT_HEIGHT, P2D);
        PJOGL.profile = 1;
    }

    public void setup()
    {
        frame.setTitle("SOCAN");
        frameRate(FRAME_RATE);

        socan = new SoundCharacterAnalyser(this);
        socan.init();
    }

    public void draw(){
        background(0);
        socan.listen();

        // draw level
        stroke(255);
        noFill();
        rect(10, 10, 20, 100);

        noStroke();
        fill(255, 0, 0);
        rect(10, 10, 20, map(socan.getIn().mix.level(), 0, 1, 0, 100));
    }

    public void keyPressed()
    {
        switch (key) {
            case 'h':

                break;
        }
    }
}

