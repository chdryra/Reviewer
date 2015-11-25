package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
@SuppressWarnings("EmptyMethod")
public class SubjectActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements SubjectAction<T> {
    //public methods
    @Override
    public String getSubject() {
        return getAdapter().getSubject();
    }

    @Override
    public void onKeyboardDone(CharSequence s) {

    }
}
