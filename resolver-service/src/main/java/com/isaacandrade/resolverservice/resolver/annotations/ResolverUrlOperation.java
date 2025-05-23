package com.isaacandrade.resolverservice.resolver.annotations;

import com.isaacandrade.common.url.model.dto.ShortenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(summary = "Resolving a URL", description = "Resolve your shorted URL here.",
        tags = {"Resolver Service"},
        responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(schema = @Schema(implementation = ShortenResponse.class))
                ),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        }
)
public @interface ResolverUrlOperation {
}
