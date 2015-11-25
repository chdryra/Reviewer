package com.chdryra.android.reviewer.View.Implementation.Configs;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link DefaultLaunchables}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class ConfigDataUiImpl implements ConfigDataUi {
    private final Map<String, LaunchableConfigsHolder<? extends GvData>> mConfigsMap;
    private LaunchableConfig mBuildReviewConfig;
    private LaunchableConfig mEditOnMapConfig;

    public ConfigDataUiImpl(Iterable<? extends LaunchableConfigsHolder<?>> configs,
                            LaunchableConfig buildReviewConfig,
                            LaunchableConfig editOnMapConfig) {
        mConfigsMap = new HashMap<>();
        for (LaunchableConfigsHolder<?> config : configs) {
            mConfigsMap.put(config.getGvDataType().getDatumName(), config);
        }
        mBuildReviewConfig = buildReviewConfig;
        mEditOnMapConfig = editOnMapConfig;
    }

    @Override
    public LaunchableConfig getViewerConfig(String datumName) {
        return getConfigs(datumName).getViewerConfig();
    }

    @Override
    public LaunchableConfig getEditorConfig(String datumName) {
        return getConfigs(datumName).getEditorConfig();
    }

    @Override
    public LaunchableConfig getAdderConfig(String datumName) {
        return getConfigs(datumName).getAdderConfig();
    }

    @Override
    public LaunchableConfig getBuildReviewConfig() {
        return mBuildReviewConfig;
    }

    @Override
    public LaunchableConfig getMapEditorConfig() {
        return mEditOnMapConfig;
    }

    private LaunchableConfigsHolder<?> getConfigs(String datumName) {
        return mConfigsMap.get(datumName);
    }
}
