package com.dev.taskmanager.mapper;

import org.mapstruct.Condition;
import org.openapitools.jackson.nullable.JsonNullable;

public class JsonNullableMapper {
    public static <T> T unwrap(JsonNullable<T> value) {
        return value.orElse(null);
    }

    @Condition 
    public static <T> boolean presenceCheck(JsonNullable<T> value) {
        return value != null && value.isPresent();
    } 
}
