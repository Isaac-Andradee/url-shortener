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
