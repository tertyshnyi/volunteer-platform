/**
 * A custom error class for handling API-related errors.
 * This class extends the native JavaScript `Error` object and adds additional properties
 * like `statusCode` for HTTP error codes and `details` for more context.
 */
export class ApiError extends Error {
    statusCode: number;
    message: string;
    details?: any;

    /**
     * Constructs an instance of ApiError with a specific HTTP status code, message, and optional details.
     * @param statusCode - The HTTP status code associated with the error (e.g., 404, 500).
     * @param message - A human-readable message describing the error.
     * @param details - Optional additional information about the error (e.g., API response body).
     */
    constructor(statusCode: number, message: string, details?: any) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;

        // This line ensures proper inheritance in TypeScript
        Object.setPrototypeOf(this, ApiError.prototype);
    }
}

/**
 * Throws an ApiError based on a failed API response.
 * This function is designed to be used when handling API calls, making it easier to
 * identify and process HTTP errors consistently across the application.
 * @param response - The Response object returned by the fetch API or any HTTP library.
 * @throws ApiError - Throws an instance of ApiError with the status code and error message from the response.
 */
export function handleApiError(response: Response): never {
    throw new ApiError(response.status, response.statusText);
}