/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.View;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HideableViewUi<V extends View, Value> extends ViewUi<View, Value>{
    private ViewUi<V, Value> mViewUi;
    private HideCondition<Value> mCondition;

    public interface HideCondition<Value> {
        boolean hideView(Value value);
    }

    public HideableViewUi(final ViewUi<V, Value> viewUi, View toHide, HideCondition<Value> condition) {
        super(toHide, new ValueGetter<Value>() {
            @Override
            public Value getValue() {
                return viewUi.getValue();
            }
        });

        mViewUi = viewUi;
        mCondition = condition;
    }

    @Override
    public void update() {
        if(mCondition.hideView(mViewUi.getValue())) {
            getView().setVisibility(View.GONE);
        } else {
            mViewUi.update();
        }
    }
}
