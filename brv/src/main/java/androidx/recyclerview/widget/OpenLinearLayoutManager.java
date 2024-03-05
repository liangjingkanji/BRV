package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class OpenLinearLayoutManager extends LinearLayoutManager{

    public OpenLinearLayoutManager(Context context) {
        super(context);
    }

    public OpenLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public OpenLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible, boolean acceptPartiallyVisible) {
        return super.findOneVisibleChild(fromIndex, toIndex, completelyVisible, acceptPartiallyVisible);
    }
}
