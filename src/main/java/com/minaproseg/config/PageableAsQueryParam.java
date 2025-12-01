package com.minaproseg.config;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        in = ParameterIn.QUERY,
        description = "Page number (0-indexed)",
        name = "page",
        schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
        in = ParameterIn.QUERY,
        description = "Number of items per page",
        name = "size",
        schema = @Schema(type = "integer", defaultValue = "10")
)
@Parameter(
        in = ParameterIn.QUERY,
        description = "Sorting criteria in format: property,(asc|desc). Example: id,desc or nom,asc",
        name = "sort",
        array = @ArraySchema(schema = @Schema(type = "string"))
)
public @interface PageableAsQueryParam {
}