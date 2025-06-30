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
export class UrlValidator {
    static isValidUrl(url) {
        try {
            new URL(url);
            return true;
        } catch (_) {
            return false;
        }
    }

    static validateApiResponse(result) {
        let completeShortenedUrl = null;
        
        if (result && result.shortUrl && typeof result.shortUrl === 'string') {
            try {
                new URL(result.shortUrl);
                completeShortenedUrl = result.shortUrl;
            } catch (e) {
                console.error("API returned 'shortUrl' but it is not a valid URL:", result.shortUrl, e);
                completeShortenedUrl = null;
            }
        }
        
        return completeShortenedUrl;
    }
} 