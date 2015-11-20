package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEdit<T extends GvData> extends ReviewDataEditorActionBasic<T> implements SubjectAction<T> {

    @Override
    public String getSubject() {
        return getAdapter().getSubject();
    }

    @Override
    public void onKeyboardDone(CharSequence s) {
        getEditor().setSubject();
    }
}
