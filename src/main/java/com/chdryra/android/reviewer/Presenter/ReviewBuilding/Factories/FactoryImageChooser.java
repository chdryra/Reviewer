package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ImageChooserImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryImageChooser {
    private Context mContext;

    public FactoryImageChooser(Context context) {
        mContext = context;
    }

    public ImageChooser newImageChooser(FileIncrementor fileIncrementor) {
        return new ImageChooserImpl(mContext, fileIncrementor);
    }
}
