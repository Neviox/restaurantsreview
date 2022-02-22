package com.prototype.restaurantsreview.model.paging;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {

    private com.prototype.restaurantsreview.model.paging.PageItemType pageItemType;

    private int index;

    private boolean active;

}
