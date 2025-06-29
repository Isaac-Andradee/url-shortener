# URL Shortener

A front-end URL shortening application that communicates with an Nginx API to generate short links.

## Project Structure

The project was organized following SOLID principles, with each component having a single responsibility:

- **config.js**: Contains application configurations, such as API URLs and endpoints
- **ui-elements.js**: Manages user interface elements (DOM)
- **ui-service.js**: Service for manipulating the user interface
- **api-service.js**: Service for communicating with the API
- **url-validator.js**: Service for validating URLs
- **app.js**: Main application logic
- **index.js**: Main file responsible for initializing the application

## How to Use

1. Open the `index.html` file in the browser
2. Enter the long URL you want to shorten
3. Optionally, provide a custom alias
4. Click "Shorten" to generate the short URL

## Requirements

- The Nginx API must be running at the address configured in `config.js`
- Modern web browser with JavaScript ES6 support
