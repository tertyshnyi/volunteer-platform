import { useState, useEffect } from 'react';

/**
 * A custom hook that fetches data from a provided URL.
 * It tracks the loading state, error state, and the response data.
 *
 * @param url - The URL from which the data should be fetched.
 * @returns An object containing the data, loading state, and error state.
 */
export function useFetch<T>(url: string) {
    const [data, setData] = useState<T | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchData = () => {
            fetch(url)
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`Error fetching data: ${response.statusText}`);
                    }
                    return response.json();
                })
                .then((result) => setData(result))
                .catch((err) => setError(err instanceof Error ? err.message : 'An error occurred'))
                .finally(() => setLoading(false));
        };

        fetchData();
    }, [url]);

    return { data, loading, error };
}