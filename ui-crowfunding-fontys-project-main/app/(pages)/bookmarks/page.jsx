"use client";

import { useEffect, useState, useRef, useCallback } from "react";
import getSpecificUserById from "@/components/fetchComponents/GET/getSpecificUserByIdGET";
import TokenManager from "@/app/apis/TokenManager";
import PageFrame from "@/components/generalComponents/pageFrame/PageFrame";
import { useRouter } from "next/navigation";
import getProjectsBookmarkedByUserGET from "@/components/fetchComponents/GET/getProjectsBookmarkedByUser";

export default function Bookmarks() {
    const [projects, setProjects] = useState([]);
    const [user, setUser] = useState(null);
    const [userId, setUserId] = useState(null);
    const router = useRouter();

    const fetchUserData = useCallback(async () => {
        if (!userId) return;

        try {
            const [userData, userProjects] = await Promise.all([
                getSpecificUserById(userId),
                getProjectsBookmarkedByUserGET(),
            ]);

            setUser(userData || {});
            setProjects(userProjects || []);
        } catch (error) {
            console.error("Error fetching user data:", error);
        }
    }, [userId]);

    useEffect(() => {
        if (typeof window !== "undefined") {
            const claims = TokenManager.getClaims();
            setUserId(claims?.userId || null);
            fetchUserData();
        }
    }, [fetchUserData]);

    if (!user) {
        return <>
            <div className="flex items-center justify-center h-screen">
                <div className="flex flex-col items-center">
                    <svg
                        className="animate-spin h-12 w-12 text-black"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                    >
                        <circle
                            className="opacity-25"
                            cx="12"
                            cy="12"
                            r="10"
                            stroke="currentColor"
                            strokeWidth="4"
                        />
                        <path
                            className="opacity-75"
                            fill="currentColor"
                            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                        />
                    </svg>
                    <p className="mt-4 text-xl font-semibold">Loading bookmarks...</p>
                </div>
            </div>
        </>

    }

    return (
        <PageFrame>
            <div className="max-w-7xl mx-auto p-6 bg-white shadow-lg rounded-lg mt-10">
                {/* Profile Header */}
                <div className="flex items-center border-b pb-6">
                    <div className="flex flex-col items-center w-1/5">
                        <div className="w-24 h-24 rounded-full bg-gray-300 overflow-hidden">
                            <img
                                src={user.profilePicture || "/profilePlaceholder.jpg"} // Fallback image if user.profilePicture is null or undefined
                                alt="Profile"
                                className="object-cover w-full h-full"
                            />
                        </div>
                    </div>
                    <div className="ml-6 w-4/5">
                        <h1 className="text-2xl font-bold text-gray-800">{user.name || "No Name Provided"}</h1>
                        <p className="text-gray-600">Email: {user.email || "No Email Provided"}</p>
                    </div>
                </div>

                <div className="flex">
                    {Array.isArray(projects) && projects.map((project) => (
                        <div
                            key={project.id}
                            onClick={() => router.push(`/projects/${project.id}`)}
                            id="project-card"
                            className="block cursor-pointer bg-transparent p-3 w-full sm:w-full md:w-1/3 lg:w-1/5 transform transition hover:scale-105 hover:bg-slate-100  rounded-lg"
                        >
                            <div className="w-full h-48 overflow-hidden rounded-lg">
                                <img
                                    src={project.imageCover}
                                    alt={project.name}
                                    className="w-full h-full object-cover"
                                />
                            </div>
                            {/*<p>{format(new Date(project.dateCreated), 'dd/MM/yyyy')}</p>*/}
                            <div className="w-full overflow-hidden bg-gray-300 rounded-full h-2.5 mt-4">
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
                    ))}
                </div>
            </div>
        </PageFrame>
    );
}
