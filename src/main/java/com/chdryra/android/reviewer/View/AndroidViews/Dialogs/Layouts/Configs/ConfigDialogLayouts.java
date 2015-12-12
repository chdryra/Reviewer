package com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Configs;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.DialogLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ConfigDialogLayouts {
    private final Map<GvDataType<?>, ConfigLayout<?>> mMap = new HashMap<>();

    public <T extends GvData> Class<? extends AddEditLayout<T>> getAddEditLayoutClass(GvDataType<T> dataType) {
        return getLayouts(dataType).getAddEditLayoutClass();
    }

    public <T extends GvData> Class<? extends DialogLayout<T>> getViewLayoutClass(GvDataType<T> dataType) {
        return getLayouts(dataType).getViewLayoutClass();
    }

    private <T extends GvData> ConfigLayout<T> getLayouts(GvDataType<T> datatype) {
        //TODO make type safe
        return (ConfigLayout<T>) mMap.get(datatype);
    }

    protected <T extends GvData> void add(GvDataType<T> dataType,
                                        Class<? extends AddEditLayout<T>> addEditClass,
                                        Class<? extends DialogLayout<T>> viewClass) {
        mMap.put(dataType, new ConfigLayout<>(dataType, addEditClass, viewClass));
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/11/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private static class ConfigLayout<T extends GvData> {
        private GvDataType<T> mDataType;
        private Class<? extends AddEditLayout<T>> mAddEditLayoutClass;
        private Class<? extends DialogLayout<T>> mViewLayoutClass;

        private ConfigLayout(GvDataType<T> dataType, Class<? extends AddEditLayout<T>>
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
}
