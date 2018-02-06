/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.mygenerallibrary.FileUtils.FileIncrementor;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFileIncrementor {
    private final File mSystemDir;
    private final String mDir;
    private final String mDefaultStem;

    public FactoryFileIncrementor(File systemDir, String dir, String defaultStem) {
        mSystemDir = systemDir;
        mDir = dir;
        mDefaultStem = defaultStem;
    }

    public FileIncrementor newJpgFileIncrementor(String fileName) {
        if(fileName == null || fileName.length() > 0) fileName = mDefaultStem;
        return new FileIncrementor(mSystemDir, mDir, fileName, "jpg");
    }
}
