package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
class SubjectEditBuildScreen extends SubjectActionNone {
    private ReviewEditor<?> mEditor;

    //Overridden
    @Override
    public void onKeyboardDone(CharSequence s) {
        mEditor.setSubject();
    }
}
