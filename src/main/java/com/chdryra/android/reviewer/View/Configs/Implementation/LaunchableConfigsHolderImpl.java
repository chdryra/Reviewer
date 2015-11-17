package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Encapsulates add, edit, view configs for a given
 * {@link GvDataType}.
 */
public class LaunchableConfigsHolderImpl<T extends GvData> implements LaunchableConfigsHolder<T> {
    private static final int DATA_ADD = RequestCodeGenerator.getCode("DataAdd");
    private static final int DATA_EDIT = RequestCodeGenerator.getCode("DataEdit");
    private static final int DATA_VIEW = RequestCodeGenerator.getCode("DataView");

    private final GvDataType<T> mDataType;
    private final LaunchableConfig<T> mAddConfig;
    private final LaunchableConfig<T> mEditConfig;
    private final LaunchableConfig<T> mViewConfig;

    public LaunchableConfigsHolderImpl(GvDataType<T> dataType,
                                       ClassesHolder classes) {
        mDataType = dataType;
        mAddConfig = new LaunchableConfigImpl<>(mDataType, classes.getAddClass(),
                DATA_ADD, mDataType.getDatumName().toUpperCase() + "_ADD_TAG");
        mEditConfig = new LaunchableConfigImpl<>(mDataType, classes.getEditClass(),
                DATA_EDIT, mDataType.getDatumName().toUpperCase() + "_EDIT_TAG");
        mViewConfig = new LaunchableConfigImpl<>(mDataType, classes.getViewClass(),
                DATA_VIEW, mDataType.getDatumName().toUpperCase() + "_VIEW_TAG");
    }

    //public methods
    @Override
    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    @Override
    public LaunchableConfig<T> getAdderConfig() {
        return mAddConfig;
    }

    @Override
    public LaunchableConfig<T> getEditorConfig() {
        return mEditConfig;
    }

    @Override
    public LaunchableConfig<T> getViewerConfig() {
        return mViewConfig;
    }
}
