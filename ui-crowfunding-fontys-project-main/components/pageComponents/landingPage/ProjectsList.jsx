import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import fetchProjectListData from '@/components/fetchComponents/GET/fetchProjectListData';

const ProjectsList = ({ title, endpoint }) => {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true); // Track loading state
    const router = useRouter();

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true); // Set loading to true
            const data = await fetchProjectListData(endpoint);
            setProjects(data);
            setLoading(false); // Set loading to false after data is fetched
        };

        fetchData();
    }, [endpoint]);

    function handleProjectClick(id) {
        router.push(`/projects/${id}`);
    }

    return (
        <div className="mb-10 bg-white">
            <div className="container">
                <h2 className="text-3xl font-bold mb-6">{title}</h2>

                <div className="flex flex-wrap justify-start">
                    {loading ? (
                        // Skeleton Loader
                        Array.from({ length: 5 }).map((_, index) => (
                            <div
                                key={index}
                                className="block bg-transparent p-3 w-full sm:w-full md:w-1/3 lg:w-1/5 rounded-lg animate-pulse"
                            >
                                <div className="w-full h-48 bg-gray-300 rounded-lg mb-4"></div>
                                <div className="h-4 bg-gray-300 rounded mb-2"></div>
                                <div className="h-4 bg-gray-300 rounded w-2/3"></div>
                            </div>
                        ))
                    ) : (
                        // Project Cards
                        Array.isArray(projects) &&
                        projects.map((project) => (
                            <div
                                key={project.id}
                                onClick={() => handleProjectClick(project.id)}
                                id="project-card"
                                className="block cursor-pointer bg-transparent p-3 w-full sm:w-full md:w-1/3 lg:w-1/5 transform transition hover:scale-105 hover:bg-slate-100 rounded-lg "
                            >
                                <div className="w-full h-48 overflow-hidden rounded-lg bg-gray-200">
                                    <img
                                        src={project.imageCover}
                                        alt={project.name}
                                        className="w-full h-full object-cover"
                                        loading="lazy"
                                        onError={(e) => (e.target.src = '/default-placeholder.png')}
                                    />
                                </div>
                                <div className="w-full overflow-hidden bg-gray-300 rounded-full h-2.5 mt-4 ">
                                    <div
                                        className="bg-green-500 h-2.5 rounded-full"
                                        style={{
                                            width: `${project.moneyRaised >= project.fundingGoal
                                                ? '100%'
                                                : `${(project.moneyRaised / project.fundingGoal) * 100}%`
                                                }`,
                                        }}
                                    ></div>
                                </div>
                                <h3 className="text-base font-bold mt-4">{project.name}</h3>
                                <p className="text-sm text-gray-700 mt-2">
                                    ${project.moneyRaised} raised of ${project.fundingGoal}
                                </p>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProjectsList;
