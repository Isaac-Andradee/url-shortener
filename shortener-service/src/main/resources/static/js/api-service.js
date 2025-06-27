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