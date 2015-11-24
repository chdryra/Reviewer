package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Dialogs.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.Dialogs.Interfaces.DialogLayout;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link ClassesAddEditViewDefault}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class ConfigDataUiImpl implements ConfigDataUi {
    private final Map<GvDataType<? extends GvData>, LaunchableConfigsHolder<? extends GvData>> mConfigsMap;
    private final Map<GvDataType<? extends GvData>, ClassesDialogLayoutHolder<? extends GvData>> mLayoutsMap;

    public ConfigDataUiImpl(Iterable<? extends LaunchableConfigsHolder<?>> configs,
                            Iterable<? extends ClassesDialogLayoutHolder<?>> layouts) {
        mConfigsMap = new HashMap<>();
        for (LaunchableConfigsHolder<?> config : configs) {
            mConfigsMap.put(config.getGvDataType(), config);
        }

        mLayoutsMap = new HashMap<>();
        for (ClassesDialogLayoutHolder<?> layout : layouts) {
            mLayoutsMap.put(layout.getGvDataType(), layout);
        }
    }

    @Override
    public <T extends GvData> LaunchableConfig<T> getViewerConfig(GvDataType<T> dataType) {
        return getConfigs(dataType).getViewerConfig();
    }

    @Override
    public <T extends GvData> LaunchableConfig<T> getEditorConfig(GvDataType<T> dataType) {
        return getConfigs(dataType).getEditorConfig();
    }

    @Override
    public <T extends GvData> LaunchableConfig<T> getAdderConfig(GvDataType<T> dataType) {
        return getConfigs(dataType).getAdderConfig();
    }

    @Override
    public <T extends GvData> Class<? extends DialogLayout<T>> getViewLayout(GvDataType<T> dataType) {
        return getLayouts(dataType).getViewLayoutClass();
    }

    @Override
    public <T extends GvData> Class<? extends AddEditLayout<T>> getAddEditLayout(GvDataType<T> dataType) {
        return getLayouts(dataType).getAddEditLayoutClass();
    }


    private <T extends GvData> LaunchableConfigsHolder<T> getConfigs(GvDataType<T> dataType) {
        //TODO make type safe
        return (LaunchableConfigsHolder<T>) mConfigsMap.get(dataType);
    }

    private <T extends GvData> ClassesDialogLayoutHolder<T> getLayouts(GvDataType<T> dataType) {
        //TODO make type safe
        return (ClassesDialogLayoutHolder<T>) mLayoutsMap.get(dataType);
    }
}
