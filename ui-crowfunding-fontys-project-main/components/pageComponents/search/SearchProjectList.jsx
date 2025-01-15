import React, { useState, useEffect, useCallback } from "react";
import getAllProjectsGET from "@/components/fetchComponents/GET/getAllProjectsGET";
import { useRouter, useSearchParams } from "next/navigation";
import { DATA } from "@/app/data";

export default function SearchProjectList() {
    const [projects, setProjects] = useState([]);
    const [type, setType] = useState(null);
    const [sortBy, setSortBy] = useState("percentageFundedDesc");
    const [minPercentageFunded, setMinPercentageFunded] = useState(0);
    const [maxPercentageFunded, setMaxPercentageFunded] = useState(100);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    const router = useRouter();
    const searchParams = useSearchParams();
    const name = searchParams.get("name") || ""; // Get the 'name' parameter from the URL

    // Fetch projects whenever filters or pagination changes
    useEffect(() => {
        const fetchData = async () => {
            const data = await getAllProjectsGET({
                type,
                sortBy,
                minPercentageFunded,
                maxPercentageFunded,
                page: currentPage - 1, // Convert to zero-based index for the API
                size: 6, // Number of projects per page
                name, // Include the 'name' parameter from the URL
            });

            if (data) {
                setProjects(data.content || data); // Assuming the API returns a "content" key for projects
                setTotalPages(data.totalPages || 1); // Assuming the API returns a "totalPages" key
            }
        };

        fetchData();
    }, [type, sortBy, minPercentageFunded, maxPercentageFunded, currentPage, name]);

    const truncateText = (text, maxLength) => {
        return text?.length > maxLength ? `${text.substring(0, maxLength)}...` : text;
    };

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
                <div className="flex justify-end gap-4 mb-8 w-2/3 mx-auto">
                    <select
                        className="p-2 border border-gray-300 rounded-md shadow-sm"
                        value={sortBy}
                        onChange={(e) => handleFilterChange(setSortBy, e.target.value)}
                    >
                        <option value="dateCreatedDesc">Date Created (new first)</option>
                        <option value="dateCreatedAsc">Date Created (old first)</option>
                        <option value="percentageFundedDesc">Percentage Funded (highest first)</option>
                        <option value="percentageFundedAsc">Percentage Funded (lowest first)</option>
                    </select>
                    <select
                        className="p-2 border border-gray-300 rounded-md shadow-sm"
                        onChange={(e) => {
                            const range = e.target.value.split("-");
                            handleFilterChange(setMinPercentageFunded, Number(range[0]));
                            handleFilterChange(setMaxPercentageFunded, Number(range[1]));
                        }}
                    >
                        <option value="0-100">Filter by Percentage Funded</option>
                        <option value="0-25">0-25%</option>
                        <option value="25-50">25-50%</option>
                        <option value="50-75">50-75%</option>
                        <option value="75-100">75-100%</option>
                        <option value="100-99999999">Fully funded</option>
                    </select>
                    <select
                        className="p-2 border border-gray-300 rounded-md shadow-sm"
                        onChange={(e) => handleFilterChange(setType, e.target.value || null)}
                    >
                        <option value="">Filter by Type</option>
                        {(DATA.projectTypes || []).map((type) => (
                            <option key={type} value={type}>
                                {type}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Projects List */}
                <div className="flex flex-col gap-6 w-2/3 mx-auto">
                    {projects.map((project) => (
                        <div
                            key={project.id}
                            className="flex items-start p-4 border rounded-lg hover:bg-gray-100 hover:scale-[103%] transition-transform cursor-pointer"
                            onClick={() => router.push(`/projects/${project.id}`)}
                        >
                            <div className="w-32 h-32 rounded-md overflow-hidden flex-shrink-0 mr-4">
                                <img
                                    src={project.imageCover || "/placeholder-image.png"}
                                    alt={project.name}
                                    className="w-full h-full object-cover"
                                />
                            </div>
                            <div className="flex flex-col justify-start items-start space-y-4 h-full ">
                                <h3 className="text-xl font-bold text-gray-800">{project.name}</h3>

                                <p className="text-sm text-gray-600">
                                    {truncateText(project.description, 100)}
                                </p>

                                <div className="text-sm font-medium text-gray-800">
                                    {project.moneyRaised} € raised of {project.fundingGoal} €
                                </div>
                                <div className="w-full overflow-hidden bg-gray-300 rounded-full h-2.5 mb-[1rem]">
                                    <div
                                        className="bg-green-500 h-2.5 rounded-full"
                                        style={{
                                            width: `${project.moneyRaised >= project.fundingGoal
                                                ? "100%"
                                                : `${(project.moneyRaised / project.fundingGoal) * 100}%`
                                                }`,
                                        }}
                                    ></div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

                {/* Pagination */}
                <div className="flex justify-between items-center mt-8 w-2/3 mx-auto">
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
