package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Dialogs.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.Dialogs.Interfaces.DialogLayout;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ClassesDialogLayoutHolder<T extends GvData> {
    private GvDataType<T> mDataType;
    private Class<? extends AddEditLayout<T>> mAddEditLayoutClass;
    private Class<? extends DialogLayout<T>> mViewLayoutClass;

    public ClassesDialogLayoutHolder(GvDataType<T> dataType, Class<? extends AddEditLayout<T>>
            addEditLayoutClass, Class<? extends DialogLayout<T>> viewLayoutClass) {
        mDataType = dataType;
        mAddEditLayoutClass = addEditLayoutClass;
        mViewLayoutClass = viewLayoutClass;
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    public Class<? extends AddEditLayout<T>> getAddEditLayoutClass() {
        return mAddEditLayoutClass;
    }

    public Class<? extends DialogLayout<T>> getViewLayoutClass() {
        return mViewLayoutClass;
    }
}
