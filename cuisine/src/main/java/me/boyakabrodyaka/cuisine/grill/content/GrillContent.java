package me.boyakabrodyaka.cuisine.grill.content;

import lombok.Getter;
import me.boyakabrodyaka.cuisine.grill.content.state.GrillContentStage;

@Getter
public class GrillContent {

    private final String key;

    private String rawId;
    private String cookedId;
    private String burntId;

    private String currentId;
    private GrillContentStage stage;
    private long stageStartedAt;

    public GrillContent(String key) {
        this.key = key;
        this.currentId = null;
        this.stage = GrillContentStage.RAW;
        this.stageStartedAt = 0L;
    }

    public boolean isEmpty() { return this.currentId == null; }

    public void set(String rawId, String cookedId, String burntId) {
        this.rawId = rawId;
        this.cookedId = cookedId;
        this.burntId = burntId;
        this.currentId = rawId;
        this.stage = GrillContentStage.RAW;
        this.stageStartedAt = System.currentTimeMillis();
    }

    public void changeStage(GrillContentStage newStage) {
        if (newStage == null) return;

        this.stage = newStage;
        this.stageStartedAt = System.currentTimeMillis();
        this.currentId = idFor(newStage);
    }

    public void removeAll() {
        this.rawId = null;
        this.cookedId = null;
        this.burntId = null;
        this.currentId = null;
        this.stage = GrillContentStage.RAW;
        this.stageStartedAt = 0L;
    }

    private String idFor(GrillContentStage stage) {
        switch (stage) {
            case RAW: return this.rawId;
            case COOKED: return this.cookedId;
            case BURNT: return this.burntId;
            default: return null;
        }
    }
}