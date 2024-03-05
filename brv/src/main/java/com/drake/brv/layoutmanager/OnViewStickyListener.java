package com.drake.brv.layoutmanager;

import android.view.View;

public interface OnViewStickyListener {

    /**
     * Adjusts any necessary properties of the `holder` that is being used as a sticky header.
     *
     * [.teardownStickyHeaderView] will be called sometime after this method
     * and before any other calls to this method go through.
     */
    void setupStickyHeaderView(View stickyHeader);

    /**
     * Reverts any properties changed in [.setupStickyHeaderView].
     *
     * Called after [.setupStickyHeaderView].
     */
    void teardownStickyHeaderView(View stickyHeader);

}
