package com.chdryra.android.reviewer.View.Screens;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
@SuppressWarnings("EmptyMethod")
public class SubjectAction extends ReviewViewAction {
    //public methods
    public String getSubject() {
        return getAdapter().getSubject();
    }

    public void onKeyboardDone(CharSequence s) {

    }
}
