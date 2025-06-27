
import { AliasValidator } from './alias-validator.js';

export class UIService {
    constructor(elements) {
        this.elements = elements;
        this.setupAliasValidation();
    }

    setupAliasValidation() {
        if (!this.elements.aliasInput || !this.elements.aliasRulesList) return;
        AliasValidator.RULES.forEach(rule => {
            const li = document.createElement('li');
            li.dataset.ruleId = rule.id;
            
            const icon = document.createElement('span');
            icon.className = 'rule-icon rule-pending';
            icon.textContent = '?';
            
            const text = document.createElement('span');
            text.textContent = rule.text;
            
            li.appendChild(icon);
            li.appendChild(text);
            this.elements.aliasRulesList.appendChild(li);
        });
        this.elements.aliasInput.addEventListener('focus', () => 
            this.elements.aliasRules?.classList.add('active')
        ); 
        this.elements.aliasInput.addEventListener('input', () => 
            this.validateAliasFormat(this.elements.aliasInput.value.trim())
        );
        this.elements.aliasInput.addEventListener('blur', () => {
            if (!this.elements.aliasInput.value.trim()) {
                this.elements.aliasRules?.classList.remove('active');
            }
        });
    }
    
    validateAliasFormat(alias) {
        if (!this.elements.aliasRulesList) return true;
        const validation = AliasValidator.validateFormat(alias);
        Object.entries(validation.rules).forEach(([ruleId, isValid]) => {
            const ruleItem = this.elements.aliasRulesList.querySelector(`[data-rule-id="${ruleId}"]`);
            const icon = ruleItem?.querySelector('.rule-icon');
            if (!icon) return;
            let className = 'rule-icon';
            let iconText = '';
            if (isValid === null) {
                className += ' rule-pending';
                iconText = '?';
            } else if (isValid) {
                className += ' rule-valid';
                iconText = '✓';
            } else {
                className += ' rule-invalid';
                iconText = '✗';
            }
            icon.className = className;
            icon.textContent = iconText;
        });
        return validation.isValid;
    }

    setMessage(text, type = 'info') {
        this.elements.messageElement.textContent = text;
        this.elements.messageElement.className = `message-${type}`;
        switch (type) {
            case 'success':
                this.elements.resultArea.style.backgroundColor = '#d4edda';
                this.elements.messageElement.style.color = '#155724';
                break;
            case 'error':
                this.elements.resultArea.style.backgroundColor = '#f8d7da';
                this.elements.messageElement.style.color = '#721c24';
                break;
            default:
                this.elements.resultArea.style.backgroundColor = '#e9ecef';
                this.elements.messageElement.style.color = '#333';
                break;
        }
    }

    displayShortenedUrl(finalUrlFromApi) {
        this.elements.shortenedUrlElement.textContent = finalUrlFromApi;
        this.elements.shortenedUrlElement.href = finalUrlFromApi;
    }

    clearResults() {
        this.elements.messageElement.textContent = '';
        this.elements.shortenedUrlElement.textContent = '';
        this.elements.shortenedUrlElement.href = '#';
        this.elements.resultArea.style.backgroundColor = '#e9ecef';
    }

    clearFormInputs() {
        this.elements.longUrlInput.value = '';
        this.elements.aliasInput.value = '';
    }

    getFormValues() {
        return {
            longUrl: this.elements.longUrlInput.value.trim(),
            alias: this.elements.aliasInput.value.trim()
        };
    }

    setLoadingState(isLoading) {
        this.elements.submitButton.disabled = isLoading;
        this.elements.submitButton.textContent = isLoading ? 'Shortening...' : 'Shorten';
    }
}