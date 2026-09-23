package me.boyakabrodyaka.cuisine.grill.content;

import lombok.Getter;
import lombok.Setter;
import me.boyakabrodyaka.cuisine.grill.content.item.GrillContentItem;
import me.boyakabrodyaka.cuisine.grill.content.label.GrillContentLabel;

@Getter
@Setter
public class GrillContentHolder {

    private GrillContentItem contentItem;
    private GrillContentLabel label;

    public boolean hasContentItem() { return this.contentItem != null; }
    public boolean hasLabel() {
        return this.label != null && this.label.isValid();
    }
    public void clearItem() {
        this.contentItem = null;
    }
    public void clearLabel() {
        this.label = null;
    }
    public void clear() {
        this.contentItem = null;
        this.label = null;
    }
}