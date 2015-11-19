package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Configs.Factories.FactoryLaunchableConfigs;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ClassesAddEditView;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataTypesList;

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

    public ConfigDataUiImpl(ClassesAddEditView dataClasses,
                            FactoryLaunchableConfigs configsFactory) {
        mConfigsMap = new HashMap<>();
        for (GvDataType<? extends GvData> type : GvDataTypesList.ALL_TYPES) {
            mConfigsMap.put(type, configsFactory.newConfig(type, dataClasses.getUiClasses(type)));
        }
    }

    private <T extends GvData> LaunchableConfigsHolder<T> getConfigs(GvDataType<T> dataType) {
        //TODO make type safe
        return (LaunchableConfigsHolder<T>) mConfigsMap.get(dataType);
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
}
