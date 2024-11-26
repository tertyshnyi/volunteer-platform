import { ApiError } from './ApiError';

/**
 * A utility function to handle and log errors in a consistent way across the application.
 * This function differentiates between API errors, general errors, and unknown errors.
 * Depending on the type of error, it provides appropriate logging and user-friendly messages.
 *
 * @param error - The error to be handled. This can be an instance of `ApiError`, a general JavaScript `Error`, or an unknown error.
 */
export function handleError(error: unknown) {
    if (error instanceof ApiError) {
        console.error(`API Error: ${error.statusCode} - ${error.message}`);
        if (error.details) {
            console.error('Details:', error.details);
        }
        alert('Something went wrong with the server. Please try again later.');
    } else if (error instanceof Error) {
        console.error('General Error:', error.message);
        alert('An unexpected error occurred. Please try again.');
    } else {
        console.error('Unknown error:', error);
        alert('An unknown error occurred. Please contact support.');
    }
}