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
public class ConditionalViewUi<V extends View, Value> extends ViewUi<View, Value>{
    private ViewUi<V, Value> mViewUi;
    private ConditionalUpdate<Value> mCondition;
    private int mVisibility;

    public interface ConditionalUpdate<Value> {
        void update(View view, int visibility, ViewUi<?, Value> ui);
    }

    public ConditionalViewUi(final ViewUi<V, Value> viewUi, View view, int visibility,
                             ConditionalUpdate<Value> condition) {
        super(view, new ValueGetter<Value>() {
            @Override
            public Value getValue() {
                return viewUi.getValue();
            }
        });
        mViewUi = viewUi;
        mCondition = condition;
        mVisibility = visibility;
    }

    @Override
    public void update() {
        mCondition.update(getView(), mVisibility, mViewUi);
    }
}
