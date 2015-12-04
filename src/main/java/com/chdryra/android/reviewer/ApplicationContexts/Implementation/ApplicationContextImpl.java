package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private ModelContext mModelContext;
    private ViewContext mViewContext;
    private PresenterContext mPresenterContext;

    public ApplicationContextImpl(ModelContext modelContext,
                                  ViewContext viewContext,
                                  PresenterContext presenterContext) {
        mModelContext = modelContext;
        mViewContext = viewContext;
        mPresenterContext = presenterContext;
    }

    @Override
    public ModelContext getModelContext() {
        return mModelContext;
    }

    @Override
    public ViewContext getViewContext() {
        return mViewContext;
    }

    @Override
    public PresenterContext getPresenterContext() {
        return mPresenterContext;
    }
}
