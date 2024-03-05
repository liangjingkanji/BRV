package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class OpenGridLayoutManager extends GridLayoutManager{

    public OpenGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public OpenGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public OpenGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible, boolean acceptPartiallyVisible) {
        return super.findOneVisibleChild(fromIndex, toIndex, completelyVisible, acceptPartiallyVisible);
    }
}
