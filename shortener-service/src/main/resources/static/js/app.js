import { UrlValidator } from './url-validator.js';
import { AliasValidator } from './alias-validator.js';

export class App {
    constructor(uiService, apiService) {
        this.uiService = uiService;
        this.apiService = apiService;
        this.initialized = false;
    }

    init() {
        if (this.initialized) return;
        const elements = this.uiService.elements;
        if (!elements.shortenerForm) {
            console.error('Form Not Found. Unable to initialize URL shortener application.');
            return;
        }
        elements.shortenerForm.addEventListener('submit', this.handleFormSubmit.bind(this));
        console.info('URL Shortener Application Initialized');
        this.initialized = true;
    }

    async handleFormSubmit(event) {
        event.preventDefault();
        this.uiService.clearResults();
        this.uiService.setLoadingState(true);

        try {
            const { longUrl, alias } = this.uiService.getFormValues();            
            if (this.validateInputs(longUrl, alias)) {
                const result = await this.apiService.shortenUrl(longUrl, alias || null);
                this.handleApiSuccess(result);
            }
        } catch (error) {
            this.handleApiError(error);
        } finally {
            this.uiService.setLoadingState(false);
        }
    }
    
    validateInputs(longUrl, alias) {
        if (!longUrl) {
            this.uiService.setMessage('Please enter a long URL to shorten.', 'error');
            this.uiService.setLoadingState(false);
            return false;
        }

        if (!UrlValidator.isValidUrl(longUrl)) {
            this.uiService.setMessage('The long URL provided appears to be invalid.', 'error');
            this.uiService.setLoadingState(false);
            return false;
        }

        if (alias && !AliasValidator.validateFormat(alias).isValid) {
            this.uiService.setMessage('The alias format is invalid. Please check the rules.', 'error');
            this.uiService.setLoadingState(false);
            return false;
        }
        
        return true;
    }
    
    handleApiSuccess(result) {
        const completeShortenedUrl = UrlValidator.validateApiResponse(result);

        if (completeShortenedUrl) {
            this.uiService.setMessage('URL shortened successfully!', 'success');
            this.uiService.displayShortenedUrl(completeShortenedUrl);
            this.uiService.clearFormInputs();
        } else {
            console.error('Successful API response, but no valid shortened URL:', result);
            this.uiService.setMessage('Unexpected response from server. Unrecognized shortened URL format.', 'error');
        }
    }
    
    handleApiError(error) {
        console.error('Failed to shorten URL:', error);        
        const errorMapping = {
            'InvalidAliasFormat': 'Invalid alias format. Please check the rules.',
            'NotAvailable': 'This alias is already in use. Please choose another one.'
        };
        let errorMessage = 'An unknown error has occurred.';
        if (error.response?.message) {
            const message = error.response.message;
            if (message.includes('Invalid Alias Format')) {
                errorMessage = errorMapping.InvalidAliasFormat;
            } else if (message.includes('not available')) {
                errorMessage = errorMapping.NotAvailable;
            } else {
                errorMessage = message;
            }
        } else if (error.message) {
            errorMessage = error.message;
        }
        this.uiService.setMessage(`Erro: ${errorMessage}`, 'error');
    }
} 