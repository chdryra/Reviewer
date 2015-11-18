package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEdit<T extends GvData> extends SubjectActionNone {
    private GvDataType<T> mDataType;
    private ReviewDataEditor<T> mEditor;

    public SubjectEdit(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    public ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    //Overridden
    @Override
    public void onKeyboardDone(CharSequence s) {
        mEditor.setSubject();
    }

    @Override
    public void onAttachReviewView() {
        mEditor = (ReviewDataEditor<T>) getReviewView();
    }
}
