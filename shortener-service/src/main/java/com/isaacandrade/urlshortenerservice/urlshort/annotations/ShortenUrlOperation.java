/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaacandrade.urlshortenerservice.urlshort.annotations;

import com.isaacandrade.common.url.model.dto.ShortenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

/** * Custom annotation for the Shorten URL operation in the URL Shortener Service.
 * This annotation is used to document the API endpoint for shortening URLs.
 * @author Isaac Andrade
 */
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
