import { useState } from 'react';

/**
 * A custom hook for reading from and writing to local storage.
 * It provides a stateful value that persists in the browser's local storage.
 *
 * @param key - The key used to store and retrieve the value from local storage.
 * @param initialValue - The initial value if the key doesn't exist in local storage.
 * @returns An array containing the current value and a setter function to update the value.
 */
export function useLocalStorage<T>(key: string, initialValue: T) {
    // Get the initial value from local storage (if available)
    const storedValue = localStorage.getItem(key);
    const parsedValue = storedValue ? JSON.parse(storedValue) : initialValue;

    const [value, setValue] = useState<T>(parsedValue);

    const setStoredValue = (newValue: T) => {
        setValue(newValue);
        localStorage.setItem(key, JSON.stringify(newValue));
    };

    return [value, setStoredValue] as const;
}
