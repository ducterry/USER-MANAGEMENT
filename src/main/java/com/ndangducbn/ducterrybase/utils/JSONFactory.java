
package com.ndangducbn.ducterrybase.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;

public abstract class JSONFactory {

    public static String toString(Object value) {
        return  new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL)
                .disableHtmlEscaping()
                .create()
                .toJson(value);
    }

    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapter(Long.class, new BadLongDeserializer())
                .disableHtmlEscaping().create();
    }

    public static String prettyPrint(Object value) {
        ObjectMapper objectMapper = new ObjectMapper();
        String objectString;
        objectString = "{\n}";
        try {
            objectString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (IOException ignored) {
        }
        return objectString;
    }



    public static String toJson(Object object) {
        return create().toJson(object);
    }

    public static String toJsonJackson(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    public static <T> T fromJSON(String json, Class<T> paramClass) throws JsonSyntaxException {
        return create().fromJson(json, paramClass);
    }
}

class BadLongDeserializer implements JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            String str = element.getAsString();
            str = str.replaceAll(",", "");
            str = str.split("\\.")[0];
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new JsonParseException(e);
        }
    }

}

