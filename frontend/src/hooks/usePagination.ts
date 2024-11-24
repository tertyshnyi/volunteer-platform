import { useState } from 'react';

/**
 * A custom hook for handling pagination logic.
 * It provides the current page, the total number of pages, and functions for navigating through pages.
 *
 * @param totalItems - The total number of items to paginate.
 * @param itemsPerPage - The number of items to display per page.
 * @returns An object containing the current page, total pages, and functions for pagination.
 */
export function usePagination(totalItems: number, itemsPerPage: number) {
    const [currentPage, setCurrentPage] = useState<number>(1);

    const totalPages = Math.ceil(totalItems / itemsPerPage);

    const goToPage = (page: number) => {
        if (page < 1) {
            setCurrentPage(1);
        } else if (page > totalPages) {
            setCurrentPage(totalPages);
        } else {
            setCurrentPage(page);
        }
    };

    const nextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    const prevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    return { currentPage, totalPages, goToPage, nextPage, prevPage };
}
