package com.deeshop.util;

import android.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhiPeng.S on 2017/3/22.
 */

public class GsonUtils {
    private static class ObservableFieldSerializerDeserializer<T> implements JsonSerializer<T>, JsonDeserializer {

        @Override
        public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
            final Type type = ((ParameterizedType) typeOfSrc).getRawType();
            if(type.equals(new TypeToken<ObservableField>(){}.getType()))
                return context.serialize(((ObservableField)src).get());
            else
                return context.serialize(src);
        }

        @Override
        public ObservableField<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
        JsonParseException {
            final Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            return new ObservableField((T) GsonUtils.gson().fromJson(json, type));
        }


    }

    private static GsonBuilder createGsonBuilder() {
        final GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        gsonBuilder.registerTypeAdapter(new TypeToken<ObservableField<String>>(){}.getType(), new ObservableFieldSerializerDeserializer<String>());
        gsonBuilder.registerTypeAdapter(new TypeToken<ObservableField<Integer>>(){}.getType(), new ObservableFieldSerializerDeserializer<Integer>());

        // register more types which are wrapped by ObservableFields
        return gsonBuilder;
    }

    private static final Gson sGson = createGson();

    private static Gson createGson() {
        return createGsonBuilder().create();
    }

    // this is used by the deserializer
    public static Gson gson() {
        return sGson;
    }

}
