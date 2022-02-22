package com.prototype.restaurantsreview.model.paging;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paging {

    private static final int PAGINATION_STEP = 3;

    private boolean nextEnabled;
    private boolean prevEnabled;
    private int pageSize;
    private int pageNumber;

    private List<com.prototype.restaurantsreview.model.paging.PageItem> items = new ArrayList<>();

    public void addPageItems(int from, int to, int pageNumber) {
        for (int i = from; i < to; i++) {
            items.add(com.prototype.restaurantsreview.model.paging.PageItem.builder()
                              .active(pageNumber != i)
                              .index(i)
                              .pageItemType(com.prototype.restaurantsreview.model.paging.PageItemType.PAGE)
                              .build());
        }
    }

    public void last(int pageSize) {
        items.add(com.prototype.restaurantsreview.model.paging.PageItem.builder()
                          .active(false)
                          .pageItemType(com.prototype.restaurantsreview.model.paging.PageItemType.DOTS)
                          .build());

        items.add(com.prototype.restaurantsreview.model.paging.PageItem.builder()
                          .active(true)
                          .index(pageSize)
                          .pageItemType(com.prototype.restaurantsreview.model.paging.PageItemType.PAGE)
                          .build());
    }

    public void first(int pageNumber) {
        items.add(com.prototype.restaurantsreview.model.paging.PageItem.builder()
                          .active(pageNumber != 1)
                          .index(1)
                          .pageItemType(com.prototype.restaurantsreview.model.paging.PageItemType.PAGE)
                          .build());

        items.add(com.prototype.restaurantsreview.model.paging.PageItem.builder()
                          .active(false)
                          .pageItemType(com.prototype.restaurantsreview.model.paging.PageItemType.DOTS)
                          .build());
    }

    public static Paging of(int totalPages, int pageNumber, int pageSize) {
        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setNextEnabled(pageNumber != totalPages);
        paging.setPrevEnabled(pageNumber != 1);
        paging.setPageNumber(pageNumber);

        if (totalPages < PAGINATION_STEP * 2 + 6) {
            paging.addPageItems(1, totalPages + 1, pageNumber);

        } else if (pageNumber < PAGINATION_STEP * 2 + 1) {
            paging.addPageItems(1, PAGINATION_STEP * 2 + 4, pageNumber);
            paging.last(totalPages);

        } else if (pageNumber > totalPages - PAGINATION_STEP * 2) {
            paging.first(pageNumber);
            paging.addPageItems(totalPages - PAGINATION_STEP * 2 - 2, totalPages + 1, pageNumber);

        } else {
            paging.first(pageNumber);
            paging.addPageItems(pageNumber - PAGINATION_STEP, pageNumber + PAGINATION_STEP + 1, pageNumber);
            paging.last(totalPages);
        }

        return paging;
    }
}
