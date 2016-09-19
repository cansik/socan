package ch.bildspur.socan.sketch;

import ch.bildspur.socan.audio.LoopRingBuffer;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import processing.core.PApplet;

/**
 * Created by cansik on 20/09/16.
 */
public class SoundCharacterAnalyser {
    public final int BUFFER_SIZE = 1024;
    public final int RING_BUFFER_SIZE = BUFFER_SIZE * 10;

    PApplet sketch;

    Minim minim;
    AudioInput in;

    LoopRingBuffer track;

    public SoundCharacterAnalyser(PApplet sketch)
    {
        this.sketch = sketch;
    }

    public void init()
    {
        minim = new Minim(sketch);
        in = minim.getLineIn(Minim.MONO, BUFFER_SIZE);
        track = new LoopRingBuffer(RING_BUFFER_SIZE);
    }

    public void listen()
    {
        track.put(in.mix.toArray());
    }

    public Minim getMinim() {
        return minim;
    }

    public AudioInput getIn() {
        return in;
    }

    public LoopRingBuffer getTrack() {
        return track;
    }
}
