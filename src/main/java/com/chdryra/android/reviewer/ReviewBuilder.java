/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.FileIncrementorFactory;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilder {
    private static final File FILE_DIR_EXT = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DCIM);

    private ControllerReviewTreeEditable mController;
    private FileIncrementor              mIncrementor;
    private Context                      mContext;

    public ReviewBuilder(Context applicationContext) {
        mController = new ControllerReviewTreeEditable(FactoryReview.createReviewInProgress());
        mContext = applicationContext;
        String dir = mContext.getString(mContext.getApplicationInfo().labelRes);
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir,
                mController.getSubject());
    }

    public ControllerReviewTreeEditable getReview() {
        return mController;
    }

    public ReviewNode publish(PublisherReviewTree publisher) {
        return mController.publishAndTag(publisher);
    }

    public ImageChooser getImageChooser(Activity activity) {
        Context c = activity.getApplicationContext();
        if (c.equals(mContext)) {
            return new ImageChooser(activity, (FileIncrementorFactory.ImageFileIncrementor)
                    mIncrementor);
        } else {
            throw new RuntimeException("Activity should belong to correct application context");
        }
    }
}
