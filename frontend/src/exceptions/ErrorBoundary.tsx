import React, { Component, ReactNode } from 'react';

/**
 * Props interface for the ErrorBoundary component.
 * It accepts children components that will be wrapped by the error boundary.
 */
interface ErrorBoundaryProps {
    children: ReactNode;
}

/**
 * State interface for the ErrorBoundary component.
 * Tracks whether an error has occurred and stores the error details.
 */
interface ErrorBoundaryState {
    hasError: boolean;
    error: Error | null;
}

/**
 * A React Error Boundary component for catching and handling errors
 * in the component tree below it. This prevents the entire app from crashing
 * when a descendant component throws an error.
 */
class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {

    /**
     * Initializes the error boundary with default state values.
     * @param props - Props passed to the ErrorBoundary component.
     */
    constructor(props: ErrorBoundaryProps) {
        super(props);
        this.state = { hasError: false, error: null };
    }

    /**
     * Invoked when a descendant component throws an error.
     * Updates the state to indicate an error has occurred and captures the error details.
     * @param error - The error object that was thrown.
     * @returns Updated state with error information.
     */
    static getDerivedStateFromError(error: Error) {
        return { hasError: true, error };
    }

    /**
     * Invoked after an error has been thrown by a descendant component.
     * This method is used to log error details or perform side effects.
     * @param error - The error object that was thrown.
     * @param errorInfo - Additional information about the error, including the component stack.
     */
    componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
        console.error('Error caught by ErrorBoundary:', error, errorInfo);
    }

    /**
     * Renders the fallback UI if an error has occurred, otherwise renders child components.
     * @returns The fallback UI or the wrapped children.
     */
    render() {
        if (this.state.hasError) {
            return (
                <div>
                    <h1>Something went wrong.</h1>
                    <p>{this.state.error?.message}</p>
                </div>
            );
        }

        return this.props.children;
    }
}

export default ErrorBoundary;
