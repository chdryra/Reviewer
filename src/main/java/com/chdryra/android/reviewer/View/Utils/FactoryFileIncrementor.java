package com.chdryra.android.reviewer.View.Utils;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFileIncrementor {
    private File mSystemDir;
    private String mDir;
    private String mDefaultStem;
    private DataValidator mValidator;

    public FactoryFileIncrementor(File systemDir, String dir, String defaultStem, DataValidator validator) {
        mSystemDir = systemDir;
        mDir = dir;
        mDefaultStem = defaultStem;
        mValidator = validator;
    }

    public FileIncrementor newJpgFileIncrementor(String fileName) {
        if(!mValidator.validateString(fileName)) fileName = mDefaultStem;
        return new FileIncrementor(mSystemDir, mDir, fileName, "jpg");
    }
}
