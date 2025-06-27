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