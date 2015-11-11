package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;
import com.chdryra.android.reviewer.View.Utils.FactoryImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilderAdapter {
    private FactoryConfiguredGridUi mGridUiFactory;

    public FactoryReviewBuilderAdapter(FactoryConfiguredGridUi gridUiFactory) {
        mGridUiFactory = gridUiFactory;
    }

    public ReviewBuilderAdapter newAdapter(Context context, ReviewBuilder builder,
                                           DataValidator validator,
                                           FactoryFileIncrementor incrementorFactory,
                                           FactoryImageChooser imageChooserFactory) {

        return new ReviewBuilderAdapter(context,
                builder,
                mGridUiFactory.newReviewBuilderAdapterGridUi(),
                validator,
                incrementorFactory,
                imageChooserFactory);
    }
}
