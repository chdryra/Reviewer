package com.chdryra.android.reviewer.View.Utils;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.FileIncrementor;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryImageChooser {
    public ImageChooser newImageChooser(Context context, FileIncrementor fileIncrementor) {
        return new ImageChooser(context, fileIncrementor);
    }
}
