/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.widget.ImageView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 *
 * This doesn't work very well as binding is lost if cover deleted due to null path.
 * Maybe should dereference instead.
 */
public class CoverNodeBannerUi extends CoverBannerUi<ReviewItemReference<DataImage>> implements ViewUiBinder.BindableViewUi<DataImage>{
    private final ViewUiBinder<DataImage> mBinder;

    public CoverNodeBannerUi(ImageView view, final ReviewNode node, Bitmap placeholder,
                             CellDimensionsCalculator.Dimensions dims) {
        super(view, new ValueGetter<ReviewItemReference<DataImage>>() {
            @Override
            public ReviewItemReference<DataImage> getValue() {
                if(node.isLeaf() && node.getReference() != null) {
                    //More efficient this way.
                    return node.getReference().getCover();
                } else {
                    return node.getCover();
                }
            }
        }, placeholder);
        view.getLayoutParams().width = dims.getCellWidth();
        view.getLayoutParams().height = dims.getCellHeight();
        mBinder = new ViewUiBinder<>(this);
    }

    @Override
    public void update(DataImage value) {
        setCover(value.getBitmap());
    }

    @Override
    public void onInvalidated() {
        setCover(null);
    }

    @Override
    public void update() {
        mBinder.bind();
    }
}
