/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.FileUtils.FileIncrementor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ImageChooserImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryImageChooser {
    private final Context mContext;
    private final FactoryFileIncrementor mIncrementorFactory;

    public FactoryImageChooser(Context context, FactoryFileIncrementor incrementorFactory) {
        mContext = context;
        mIncrementorFactory = incrementorFactory;
    }

    public ImageChooser newImageChooser(FileIncrementor fileIncrementor) {
        return new ImageChooserImpl(mContext, fileIncrementor);
    }

    public ImageChooser newImageChooser(String fileName) {
        return newImageChooser(mIncrementorFactory.newJpgFileIncrementor(fileName));
    }
}
