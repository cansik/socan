package ch.bildspur.socan.sketch;

import ddf.minim.AudioPlayer;
import ddf.minim.analysis.FFT;
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

    AudioPlayer song;

    float onSetRadius = 0;

    public void settings(){
        size(OUTPUT_WIDTH, OUTPUT_HEIGHT, P2D);
        PJOGL.profile = 1;
    }

    public void setup()
    {
        surface.setTitle("Sound Character Analyser");
        frameRate(FRAME_RATE);

        socan = new SoundCharacterAnalyser(this);
        socan.init();

        //song = socan.getMinim().loadFile("justice-minimix.mp3", 2048);
        song = socan.getMinim().loadFile("techhouse-minimix.mp3", 2048);
        song.play();

        socan.init(song);
    }

    public void draw(){
        background(50);
        socan.listen();

        drawUI();
    }

    public void keyPressed()
    {
        switch (key) {
            case 'h':

                break;
        }
    }

    void drawUI()
    {
        // live input
        fill(255, 238, 173);
        textAlign(LEFT, CENTER);
        text("live input:", 40, 50);

        drawLevel(130, 10, 20, 100, socan.getSource().mix.level());
        drawBuffer(180, 10, 400, 100, socan.getSource().mix.toArray());

        // analysis
        fill(255, 238, 173);
        textAlign(LEFT, CENTER);
        text("analysis:", 40, 250);

        drawFFT(130, 200, 300, 100, socan.getFFT());
        drawOnset(530, 250, 100, socan.getBeatDetect().isKick());
    }

    void drawLevel(float x, float y, float w, float h, float value)
    {
        float mappedValue = map(value, 0, 1, 0, h);

        stroke(255);
        noFill();
        rect(x, y, w, h);

        noStroke();
        fill(255, 111, 105);
        rect(x+1, y + h - mappedValue, w-1, h - (h - mappedValue));

        textAlign(CENTER, CENTER);
        text("level", x + (w / 2), y + h + 10);
    }


    void drawBuffer(float x, float y, float w, float h, float[] data)
    {
        // draw border
        stroke(255);
        noFill();
        rect(x, y, w, h);

        // draw data
        float step = w / data.length;

        // draw lines
        stroke(150, 206, 180);
        noFill();

        for(int i = 0; i < data.length - 1; i += 2)
        {
            float y1 = map(data[i], -1, 1, 0, h - 1);
            float y2 = map(data[i+1], -1, 1, 0, h - 1);

            float x1 = step * (float)i;
            float x2 = step * (float)i+1;

            line(x + x1, y + y1, x + x2, y + y2);
        }

        fill(150, 206, 180);
        textAlign(CENTER, CENTER);
        text("buffer", x + (w / 2), y + h + 10);
    }

    void drawFFT(float x, float y, float w, float h, FFT fft)
    {
        // draw border
        stroke(255);
        noFill();
        rect(x, y, w, h);

        float step = w / fft.specSize();

        // draw lines
        stroke(255, 204, 92);
        noFill();

        float max = 1;
        for(int i = 0; i < fft.specSize(); i++)
        {
            float value = fft.getBand(i);
            if(value > max)
                max = value;
        }

        for(int i = 0; i < fft.specSize(); i++)
        {
            float value = map(fft.getBand(i), 0, max, 0, h);
            float ix = step * (float)i;

            // draw the line for frequency band i, scaling it up a bit so we can see it
            line(x + ix, y + h, x + ix, y + h - value);
        }

        textAlign(CENTER, CENTER);
        text("fft", x + (w / 2), y + h + 10);
    }

    void drawOnset(float x, float y, float r, boolean isOnset)
    {
        float a = map(onSetRadius, 20, r, 60, 255);
        stroke(255);
        fill(170, 216, 176, a);

        if (isOnset)
            onSetRadius = r;

        ellipse(x, y, onSetRadius, onSetRadius);
        onSetRadius *= 0.95;
        if (onSetRadius < 20)
            onSetRadius = 20;
    }
}

