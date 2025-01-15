import React, { useState, useEffect } from "react";
import getAllProjectsGET from "@/components/fetchComponents/GET/getAllProjectsGET";
import { useRouter } from "next/navigation";
import { DATA } from "@/app/data";

export default function ProjectFilterTable() {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true); // Add loading state
    const [type, setType] = useState(null);
    const [sortBy, setSortBy] = useState("percentageFundedDesc");
    const [minPercentageFunded, setMinPercentageFunded] = useState(0);
    const [maxPercentageFunded, setMaxPercentageFunded] = useState(100);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    const router = useRouter();

    // Fetch projects whenever filters or pagination changes
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true); // Set loading to true before fetching data
            const data = await getAllProjectsGET({
                type,
                sortBy,
                minPercentageFunded,
                maxPercentageFunded,
                page: currentPage - 1, // Convert to zero-based index for the API
                size: 6, // Number of projects per page
            });

            if (data) {
                setProjects(data.content || data);
                setTotalPages(data.totalPages || 1);
            }
            setLoading(false);
        };

        fetchData();
    }, [type, sortBy, minPercentageFunded, maxPercentageFunded, currentPage]);

    // Helper function to truncate text
    const truncateText = (text, maxLength) => {
        if (text && text.length > maxLength) {
            return `${text.substring(0, maxLength)}...`;
        }
        return text;
    };

    // Handle filter updates and reset pagination
    const handleFilterChange = (setter, value) => {
        setter(value);
        setCurrentPage(1); // Reset to first page whenever a filter changes
    };

    const handleNextPage = () => {
        if (currentPage < totalPages) setCurrentPage((prev) => prev + 1);
    };

    const handlePrevPage = () => {
        if (currentPage > 1) setCurrentPage((prev) => prev - 1);
    };

    return (
        <div className="mb-1 py-8">
            <div className="container mx-auto">
                <h2 className="text-4xl font-bold mb-8 text-center">Project Listings</h2>

                {/* Filter and Sort Options */}
                <div className="flex justify-end gap-6 mb-8 flex-wrap text-right">
                    {/* Filter for Sort By */}
                    <div className="flex flex-col w-56">
                        <label className="mb-2 text-sm font-medium text-gray-700 ">Sort by</label>
                        <select
                            className="p-3 bg-white border border-gray-300 rounded-md shadow-sm text-sm hover:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            value={sortBy}
                            onChange={(e) => handleFilterChange(setSortBy, e.target.value)}
                        >
                            <option value="dateCreatedDesc">Date Created (Newest first)</option>
                            <option value="dateCreatedAsc">Date Created (Oldest first)</option>
                            <option value="percentageFundedDesc">Percentage Funded (Highest first)</option>
                            <option value="percentageFundedAsc">Percentage Funded (Lowest first)</option>
                        </select>
                    </div>

                    {/* Filter for Percentage Funded */}
                    <div className="flex flex-col w-56">
                        <label className="mb-2 text-sm font-medium text-gray-700">Percentage Funded</label>
                        <select
                            className="p-3 bg-white border border-gray-300 rounded-md shadow-sm text-sm hover:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            onChange={(e) => {
                                const range = e.target.value.split("-");
                                handleFilterChange(setMinPercentageFunded, Number(range[0]));
                                handleFilterChange(setMaxPercentageFunded, Number(range[1]));
                            }}
                        >
                            <option value="0-100">Any</option>
                            <option value="0-25">0-25%</option>
                            <option value="25-50">25-50%</option>
                            <option value="50-75">50-75%</option>
                            <option value="75-100">75-100%</option>
                            <option value="100-99999999">Fully Funded</option>
                        </select>
                    </div>

                    {/* Filter for Project Type */}
                    <div className="flex flex-col w-56">
                        <label className="mb-2 text-sm font-medium text-gray-700">Type</label>
                        <select
                            className="p-3 bg-white border border-gray-300 rounded-md shadow-sm text-sm hover:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            onChange={(e) => handleFilterChange(setType, e.target.value || null)}
                        >
                            <option value="">Any</option>
                            {(DATA.projectTypes || []).map((type) => (
                                <option key={type} value={type}>
                                    {type}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>

                {/* Projects Grid */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                    {loading
                        ? // Skeleton Loader
                        Array.from({ length: 6 }).map((_, index) => (
                            <div
                                key={index}
                                className="flex flex-col rounded-lg p-6 bg-gray-200 animate-pulse min-h-[350px]"
                            >
                                <div className="w-full h-64 bg-gray-300 rounded-md mb-4"></div>
                                <div className="h-6 bg-gray-300 rounded mb-2"></div>
                                <div className="h-4 bg-gray-300 rounded w-3/4 mb-2"></div>
                                <div className="h-4 bg-gray-300 rounded w-1/2"></div>
                            </div>
                        ))
                        : projects.map((project) => (
                            <div
                                key={project.id}
                                className="flex flex-col rounded-lg p-6 bg-white 
                                        hover:shadow-xl transition-transform duration-300 ease-in-out
                                        hover:scale-105 hover:bg-slate-100 min-h-[350px] cursor-pointer"
                                onClick={() => router.push(`/projects/${project.id}`)}
                            >
                                <div className="w-full mb-4 rounded-md overflow-hidden">
                                    <img
                                        src={project.imageCover || "/placeholder-image.png"}
                                        alt={project.name}
                                        className="w-full h-64 object-cover"
                                    />
                                </div>
                                <div className="flex-1">
                                    <h3 className="text-xl font-semibold text-gray-800 mb-2">
                                        {project.name}
                                    </h3>
                                    <p className="text-sm text-gray-600 mb-4">
                                        {truncateText(project.description, 100)}
                                    </p>
                                </div>

                                <div className="w-full overflow-hidden bg-gray-300 rounded-full h-2.5 mb-[1rem]">
                                    <div
                                        className="bg-green-500 h-2.5 rounded-full"
                                        style={{
                                            width: `${project.moneyRaised >= project.fundingGoal
                                                ? "100%"
                                                : `${(project.moneyRaised /
                                                    project.fundingGoal) *
                                                100
                                                }%`
                                                }`,
                                        }}
                                    ></div>
                                </div>

                                <div className="flex justify-between items-center mt-auto">
                                    <p className="text-lg font-bold text-blue-600">
                                        {project.moneyRaised} € raised
                                    </p>
                                    <p className="text-sm text-gray-500">
                                        of {project.fundingGoal} €
                                    </p>
                                </div>
                            </div>
                        ))}
                </div>

                {/* Pagination */}
                <div className="flex justify-between items-center mt-8">
                    <button
                        onClick={handlePrevPage}
                        disabled={currentPage === 1}
                        className={`px-4 py-2 rounded-md ${currentPage === 1
                            ? "bg-gray-300 text-gray-500 cursor-not-allowed"
                            : "bg-blue-600 text-white hover:bg-blue-700"
                            }`}
                    >
                        Prev
                    </button>
                    <span>
                        Page {currentPage} of {totalPages}
                    </span>
                    <button
                        onClick={handleNextPage}
                        disabled={currentPage === totalPages}
                        className={`px-4 py-2 rounded-md ${currentPage === totalPages
                            ? "bg-gray-300 text-gray-500 cursor-not-allowed"
                            : "bg-blue-600 text-white hover:bg-blue-700"
                            }`}
                    >
                        Next
                    </button>
                </div>
            </div>
        </div>
    );
}
