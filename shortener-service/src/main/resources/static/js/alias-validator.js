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
export class AliasValidator {
    static PATTERN = /^[A-Za-z0-9](?!.*[_-]{2})[A-Za-z0-9_-]{1,18}[A-Za-z0-9]$/;

    static RULES = [
        { id: 'rule-start-end', text: 'Start and end with letter or number' },
        { id: 'rule-length', text: 'Have between 3 and 20 characters' },
        { id: 'rule-chars', text: 'Contain only letters, numbers, dashes (-) and underscores (_)' },
        { id: 'rule-no-double', text: 'Not contain two dashes or underscores in a row' }
    ];

    static validateFormat(alias) {
        if (!alias) return { isValid: true, rules: this.getInitialRulesStatus() };
        
        const rules = {
            'rule-start-end': /^[A-Za-z0-9].*[A-Za-z0-9]$/.test(alias) || alias.length <= 1,
            'rule-length': alias.length >= 3 && alias.length <= 20,
            'rule-chars': /^[A-Za-z0-9_-]*$/.test(alias),
            'rule-no-double': !(/[_-]{2}/).test(alias)
        };
        
        const isValid = this.PATTERN.test(alias);
        
        return { 
            isValid, 
            rules,
            message: isValid ? '' : 'Invalid alias format'
        };
    }
    
    static getInitialRulesStatus() {
        return {
            'rule-start-end': null,
            'rule-length': null,
            'rule-chars': null,
            'rule-no-double': null
        };
    }
} 