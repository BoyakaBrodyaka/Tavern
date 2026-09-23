package me.boyakabrodyaka.cuisine.grill.task;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.cuisine.grill.Grill;
import me.boyakabrodyaka.cuisine.grill.content.GrillContent;
import me.boyakabrodyaka.cuisine.grill.content.GrillContentHolder;
import me.boyakabrodyaka.cuisine.grill.content.GrillContentRegistry;
import me.boyakabrodyaka.cuisine.grill.content.item.GrillContentItem;
import me.boyakabrodyaka.cuisine.grill.content.item.GrillContentItemSpawner;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabel;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabelRemover;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabelSpawner;
import me.boyakabrodyaka.cuisine.grill.content.progress.GrillContentProgress;
import me.boyakabrodyaka.cuisine.grill.content.state.GrillContentStage;
import me.boyakabrodyaka.cuisine.grill.content.state.GrillContentTiming;
import me.boyakabrodyaka.cuisine.grill.station.GrillStation;
import me.boyakabrodyaka.cuisine.grill.station.GrillStationCoordinate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class GrillTask extends BukkitRunnable {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 5L;

    private static final int BAR_LENGTH = 8;
    private static final double LABEL_OFFSET_X = 0.5D;
    private static final double LABEL_OFFSET_Y = 1.4D;
    private static final double LABEL_OFFSET_Z = 0.5D;

    private static final int REPLACE_AMOUNT = 1;

    private final JavaPlugin plugin;
    private final Grill grill;
    private final GrillContentRegistry contentRegistry;
    private final GrillContentItemSpawner itemSpawner;
    private final GrillContentTiming timing;
    private final GrillContentProgress progress;
    private final GrillContentLabelSpawner labelSpawner;
    private final GrillContentLabelRemover labelRemover;

    public void start() { runTaskTimer(this.plugin, INITIAL_DELAY, PERIOD); }

    @Override
    public void run() {
        long now = System.currentTimeMillis();

        for (String key : this.contentRegistry.getAllKeys()) process(key, now);
    }

    private void process(String key, long now) {
        GrillContentHolder holder = this.contentRegistry.getHolder(key);
        if (!holder.hasContentItem()) return;

        GrillContentItem contentItem = holder.getContentItem();
        if (contentItem == null) return;

        Item entity = contentItem.getEntity();
        if (entity == null || entity.isDead()) return;

        GrillContent content = this.contentRegistry.get(key);
        if (content == null || content.isEmpty()) return;

        GrillStation station = this.grill.getRegistry().get(key);
        if (station == null) return;

        World world = entity.getWorld();
        Location base = station.toLocation(world);
        if (base == null) return;

        base.add(0, GrillStationCoordinate.LABEL_OFFSET_Y.getValue(), 0);

        updateLabel(holder, base, content);

        if (content.getStage() == GrillContentStage.BURNT) return;

        long elapsed = now - content.getStageStartedAt();
        long duration = this.timing.getDuration(content.getStage());
        if (elapsed < duration) return;

        GrillContentStage nextStage = content.getStage().next();
        String nextId = pickId(content, nextStage);
        if (nextId == null) return;

        this.itemSpawner.replace(contentItem, base, nextId, REPLACE_AMOUNT);
        content.changeStage(nextStage);
    }

    private void updateLabel(GrillContentHolder holder, Location base, GrillContent content) {
        String bar = this.progress.renderBar(content, BAR_LENGTH);
        String stageLabel = getStageLabel(content.getStage());

        String text = bar.isEmpty() ? stageLabel : stageLabel + " §8» " + bar;
        Location labelLocation = base.clone().add(LABEL_OFFSET_X, LABEL_OFFSET_Y, LABEL_OFFSET_Z);

        if (holder.hasLabel()) {
            GrillContentLabel label = holder.getLabel();
            label.getStand().setCustomName(text);
            return;
        }

        GrillContentLabel label = this.labelSpawner.spawn(labelLocation, text);
        if (label == null) return;

        holder.setLabel(label);
    }

    private String getStageLabel(GrillContentStage stage) {
        switch (stage) {
            case RAW: return "§6Сырой";
            case COOKED: return "§aГотовый";
            default: return "§cСгоревший";
        }
    }

    private String pickId(GrillContent content, GrillContentStage stage) {
        switch (stage) {
            case RAW: return content.getRawId();
            case COOKED: return content.getCookedId();
            default: return content.getBurntId();
        }
    }
}