package me.boyakabrodyaka.cuisine.grill.content.progress;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.grill.content.GrillContent;
import me.boyakabrodyaka.cuisine.grill.content.state.GrillContentStage;
import me.boyakabrodyaka.cuisine.grill.content.state.GrillContentTiming;

@RequiredArgsConstructor
public class GrillContentProgress {

    private static final char FILLED_CHAR = '|';
    private static final char EMPTY_CHAR = '.';
    private static final String COLOR_RAW = "§6";
    private static final String COLOR_DEFAULT = "§a";

    private final GrillContentTiming timing;

    public double getPercent(GrillContent content) {
        if (content == null) return 0.0D;
        if (content.isEmpty()) return 0.0D;

        long duration = this.timing.getDuration(content.getStage());
        if (duration <= 0L) return 1.0D;

        long elapsed = System.currentTimeMillis() - content.getStageStartedAt();

        return clampPercent((double) elapsed / (double) duration);
    }

    public String renderBar(GrillContent content, int length) {
        if (content == null) return "";
        if (content.getStage() == GrillContentStage.BURNT) return "";
        if (length <= 0) return "";

        double percent = getPercent(content);
        int filled = (int) (percent * length);

        StringBuilder builder = new StringBuilder(length + 2);
        builder.append(colorOf(content.getStage()));

        for (int i = 0; i < length; i++) {
            builder.append(i < filled ? FILLED_CHAR : EMPTY_CHAR);
        }

        return builder.toString();
    }

    private double clampPercent(double percent) {
        if (percent < 0.0D) return 0.0D;
        if (percent > 1.0D) return 1.0D;

        return percent;
    }

    private String colorOf(GrillContentStage stage) {
        if (stage == GrillContentStage.RAW) return COLOR_RAW;
        return COLOR_DEFAULT;
    }
}