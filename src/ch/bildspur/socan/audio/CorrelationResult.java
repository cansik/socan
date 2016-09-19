package ch.bildspur.socan.audio;

/**
 * Created by cansik on 20/09/16.
 */
public class CorrelationResult {
    private final float delay;
    private final float confidence;

    public CorrelationResult(float delay, float confidence)
    {
        this.delay = delay;
        this.confidence = confidence;
    }

    public float getDelay() {
        return delay;
    }

    public float getConfidence() {
        return confidence;
    }
}
