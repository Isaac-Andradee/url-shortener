package com.isaacandrade.urlshortenerservice.urlshort.annotations;

import com.isaacandrade.common.url.model.dto.ShortenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(summary = "Shorting a URL", description = "Give a long URL and choose your alias if you want",
        tags = {"Shortener Service"},
        responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(schema = @Schema(implementation = ShortenRequest.class))
                ),
                @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                @ApiResponse(description = "Unavailable Service", responseCode = "503", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content)
        }
)
public @interface ShortenUrlOperation {
}
