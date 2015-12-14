package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private PresenterContext mPresenterContext;

    public ApplicationContextImpl(PresenterContext presenterContext) {
        mPresenterContext = presenterContext;
    }

    @Override
    public PresenterContext getContext() {
        return mPresenterContext;
    }
}
