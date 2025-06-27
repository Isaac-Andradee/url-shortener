export const initUIElements = () => {
    return {
        shortenerForm: document.getElementById('shortenerForm'),
        longUrlInput: document.getElementById('longUrl'),
        aliasInput: document.getElementById('alias'),
        messageElement: document.getElementById('message'),
        shortenedUrlElement: document.getElementById('shortenedUrl'),
        resultArea: document.getElementById('resultArea'),
        submitButton: document.querySelector('#shortenerForm button[type="submit"]'),
        aliasRules: document.getElementById('aliasRules'),
        aliasRulesList: document.getElementById('aliasRulesList')
    };
}; 