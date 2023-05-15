package cn.org.wangchangjiu.table2entity.model;

import com.google.gson.Gson;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Classname ConfigSettingConverter
 * @Description
 * @Date 2023/5/15 20:34
 * @Created by wangchangjiu
 */
public class ConfigSettingConverter extends Converter<ConfigSetting> {

    @Override
    public @Nullable ConfigSetting fromString(@NotNull String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, ConfigSetting.class);
    }

    @Override
    public @Nullable String toString(@NotNull ConfigSetting value) {
        Gson gson = new Gson();
        return gson.toJson(value, ConfigSetting.class);
    }
}
