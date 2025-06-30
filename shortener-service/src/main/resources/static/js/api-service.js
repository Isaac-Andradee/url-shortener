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
export class ApiService {
    constructor(config) {
        this.config = config;
    }

    async shortenUrl(longUrl, alias = null) {
        const endpoint = `${this.config.apiBaseUrl}${this.config.endpoints.shortener}`;
        const payload = { longUrl };
        if (alias) {
            payload.alias = alias;
        }

        try {
            const response = await fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
            });

            const data = await response.json();

            if (!response.ok) {
                const error = new Error(data.message || `HTTP Error: ${response.status}`);
                error.response = data;
                error.status = response.status;
                throw error;
            }
            return data;

        } catch (error) {
            if (error instanceof SyntaxError) {
                console.error("Error parsing API response (not JSON?):", error);
                throw new Error('Invalid response from server.');
            }
            if (error instanceof Error) {
                throw error;
            }
            throw new Error(error.message || 'Error communicating with the API.');
        }
    }
} 