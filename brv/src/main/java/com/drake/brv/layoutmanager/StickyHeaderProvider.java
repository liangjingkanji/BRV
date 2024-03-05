package com.drake.brv.layoutmanager;

import androidx.recyclerview.widget.RecyclerView;

public interface StickyHeaderProvider {

    boolean isStickyHeader(RecyclerView.Adapter<?> adapter, int position);

}
