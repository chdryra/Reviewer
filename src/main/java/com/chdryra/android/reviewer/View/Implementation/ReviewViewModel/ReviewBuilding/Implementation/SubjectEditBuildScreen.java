package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEditBuildScreen<T extends GvData> extends ReviewEditorActionBasic<T>
        implements SubjectAction<T>{
    @Override
    public String getSubject() {
        return getEditor().getSubject();
    }

    //Overridden
    @Override
    public void onKeyboardDone(CharSequence s) {
        getEditor().setSubject();
    }
}
