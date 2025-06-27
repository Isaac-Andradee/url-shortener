import { config } from './config.js';
import { initUIElements } from './ui-elements.js';
import { UIService } from './ui-service.js';
import { ApiService } from './api-service.js';
import { App } from './app.js';

document.addEventListener('DOMContentLoaded', () => {
    const elements = initUIElements();
    const uiService = new UIService(elements);
    const apiService = new ApiService(config);
    
    const app = new App(uiService, apiService);
    app.init();
}); 