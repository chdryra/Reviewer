package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;

/**
 * Encapsulates add, edit, view configs for a given
 * {@link GvDataType}.
 */
public class LaunchableConfigsHolder<T extends GvData> {
    private static final int DATA_ADD = RequestCodeGenerator.getCode("DataAdd");
    private static final int DATA_EDIT = RequestCodeGenerator.getCode("DataEdit");
    private static final int DATA_VIEW = RequestCodeGenerator.getCode("DataView");

    private final GvDataType<T> mDataType;
    private final LaunchableConfig<T> mAddConfig;
    private final LaunchableConfig<T> mEditConfig;
    private final LaunchableConfig<T> mViewConfig;

    public LaunchableConfigsHolder(GvDataType<T> dataType,
                                   Class<? extends LaunchableUi> addClass,
                                   Class<? extends LaunchableUi> editClass,
                                   Class<? extends LaunchableUi> viewClass) {
        mDataType = dataType;
        mAddConfig = new LaunchableConfigImpl<>(mDataType, addClass,
                DATA_ADD, mDataType.getDatumName().toUpperCase() + "_ADD_TAG");
        mEditConfig = new LaunchableConfigImpl<>(mDataType, editClass,
                DATA_EDIT, mDataType.getDatumName().toUpperCase() + "_EDIT_TAG");
        mViewConfig = new LaunchableConfigImpl<>(mDataType, viewClass,
                DATA_VIEW, mDataType.getDatumName().toUpperCase() + "_VIEW_TAG");
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    public LaunchableConfig<T> getAdderConfig() {
        return mAddConfig;
    }

    public LaunchableConfig<T> getEditorConfig() {
        return mEditConfig;
    }

    public LaunchableConfig<T> getViewerConfig() {
        return mViewConfig;
    }
}
