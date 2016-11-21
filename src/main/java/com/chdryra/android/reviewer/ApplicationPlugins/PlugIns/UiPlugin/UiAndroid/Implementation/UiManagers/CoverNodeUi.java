/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.app.Activity;
import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverNodeUi extends CoverUi<ReviewItemReference<DataImage>>{
    public CoverNodeUi(View view, final ReviewNode node, Activity activity) {
        super(view, new ValueGetter<ReviewItemReference<DataImage>>() {
            @Override
            public ReviewItemReference<DataImage> getValue() {
                return node.getCover();
            }
        }, activity);
    }

    @Override
    public void update() {
        getValue().dereference(new DataReference.DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(DataValue<DataImage> value) {
                if(value.hasValue()) setCover(value.getData().getBitmap());
            }
        });
    }
}
