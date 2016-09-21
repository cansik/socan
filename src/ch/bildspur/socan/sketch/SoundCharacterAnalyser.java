package ch.bildspur.socan.sketch;

import ch.bildspur.socan.audio.LoopRingBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioSource;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

import static ddf.minim.analysis.BeatDetect.SOUND_ENERGY;

/**
 * Created by cansik on 20/09/16.
 */
public class SoundCharacterAnalyser {
    public final int BUFFER_SIZE = 2048;
    public final int RING_BUFFER_SIZE = BUFFER_SIZE * 10;

    PApplet sketch;

    Minim minim;
    AudioSource source;

    LoopRingBuffer track;

    FFT fft;

    BeatDetect beatDetect;

    public SoundCharacterAnalyser(PApplet sketch)
    {
        this.sketch = sketch;
        minim = new Minim(sketch);
    }

    public void init()
    {
        this.init(minim.getLineIn(Minim.MONO, BUFFER_SIZE));
    }

    public void init(AudioSource source)
    {
        this.source = source;
        track = new LoopRingBuffer(RING_BUFFER_SIZE);

        fft = new FFT(source.bufferSize(), source.sampleRate());
        beatDetect = new BeatDetect(source.bufferSize(), source.sampleRate());
    }

    public void listen()
    {
        track.put(source.mix.toArray());

        fft.forward(source.mix);
        beatDetect.detect(source.mix);
    }

    public Minim getMinim() {
        return minim;
    }

    public AudioSource getSource() {
        return source;
    }

    public LoopRingBuffer getTrack() {
        return track;
    }

    public FFT getFFT() {
        return fft;
    }

    public BeatDetect getBeatDetect() {
        return beatDetect;
    }
}
