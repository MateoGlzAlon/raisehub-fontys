import React, { useState } from "react";
import { useRouter } from "next/navigation";

const SearchBar = () => {
    const [query, setQuery] = useState(""); // State for the search query
    const router = useRouter();

    const handleSearch = (e) => {
        e.preventDefault(); // Prevent default form submission
        if (query.trim()) {
            router.push(`/search?name=${encodeURIComponent(query)}`);
        }
    };

    return (
        <form
            onSubmit={handleSearch}
            className="flex items-center border border-gray-300 rounded-lg p-2 bg-gray-100 h-12 w-full"
        >
            <input
                id="search-input"
                type="text"
                placeholder="Search..."
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                className="w-full px-4 py-2 text-sm text-gray-700 bg-transparent focus:outline-none"
            />
            <button
                id="search-submit"
                type="submit"
                className="ml-2 px-4 py-2 text-sm text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition"
            >
                Search
            </button>
        </form>
    );
};

export default SearchBar;
