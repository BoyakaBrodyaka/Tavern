package me.boyakabrodyaka.cuisine.grill.content.state;

public enum GrillContentStage {

    RAW,
    COOKED,
    BURNT;

    public GrillContentStage next() {
        switch (this) {
            case RAW: return COOKED;
            case COOKED: return BURNT;
            default: return BURNT;
        }
    }
}