package com.nice.qin.fire.widget;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.jaydenxiao.common.commonwidget.NormalTitleBar;

/**
 * Created by Qin on 2016-11-13-0013.
 */

public class ScrollAwareToolBarBehavior extends CoordinatorLayout.Behavior<NormalTitleBar> {
    public ScrollAwareToolBarBehavior() {

    }

//    @Override
//    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, NormalTitleBar child, View directTargetChild, View target, int nestedScrollAxes) {
//        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
//                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, NormalTitleBar child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
       /* if (dyConsumed<0&& child.getVisibility()==View.VISIBLE){
            child.setVisibility(View.INVISIBLE);
        }else if(dyConsumed>0 && child.getVisibility()!=View.VISIBLE){
            child.setVisibility(View.VISIBLE);
        }*/
    }

}
