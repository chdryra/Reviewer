package com.chdryra.android.reviewer.View.Implementation.Configs;

import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Encapsulates add, edit, view configs for a given
 * {@link GvDataType}.
 */
public class LaunchableConfigsHolder<T extends GvData> {
    private static final int DATA_ADD = RequestCodeGenerator.getCode("DataAdd");
    private static final int DATA_EDIT = RequestCodeGenerator.getCode("DataEdit");
    private static final int DATA_VIEW = RequestCodeGenerator.getCode("DataView");

    private final GvDataType<T> mDataType;
    private final LaunchableConfig mAddConfig;
    private final LaunchableConfig mEditConfig;
    private final LaunchableConfig mViewConfig;

    public LaunchableConfigsHolder(AddEditViewClasses<T> launchables) {
        mDataType = launchables.getGvDataType();
        mAddConfig = new LaunchableConfigImpl(launchables.getAddClass(),
                DATA_ADD, mDataType.getDatumName().toUpperCase() + "_ADD_TAG");
        mEditConfig = new LaunchableConfigImpl(launchables.getEditClass(),
                DATA_EDIT, mDataType.getDatumName().toUpperCase() + "_EDIT_TAG");
        mViewConfig = new LaunchableConfigImpl(launchables.getViewClass(),
                DATA_VIEW, mDataType.getDatumName().toUpperCase() + "_VIEW_TAG");
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    public LaunchableConfig getAdderConfig() {
        return mAddConfig;
    }

    public LaunchableConfig getEditorConfig() {
        return mEditConfig;
    }

    public LaunchableConfig getViewerConfig() {
        return mViewConfig;
    }
}
