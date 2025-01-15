"use client";

import { useRouter } from 'next/navigation';
import { useState, useEffect } from 'react';
import PageFrame from '@/components/generalComponents/pageFrame/PageFrame';
import { DonorsList } from '@/components/pageComponents/projects/DonorsList';
import ProjectImageCarousel from '@/components/pageComponents/projects/ProjectImageCarousel';
import { ProjectProgressBar } from '@/components/pageComponents/projects/ProjectProgressBar';
import NumberTicker from "@/components/magicui/number-ticker";
import fetchProjectData from '@/components/fetchComponents/GET/fetchProjectData';
import PaymentButton from '@/components/pageComponents/projects/PaymentButton';
import { useWebSocket } from "@/components/generalComponents/WebSocketContext";
import deleteProjectById from '@/components/fetchComponents/DELETE/deleteProjectDELETE';
import deleteUserById from '@/components/fetchComponents/DELETE/deleteUserDELETE';
import TokenManager from '@/app/apis/TokenManager';
import isProjectBookmarked from '@/components/fetchComponents/GET/isBookmarkedGET';
import removeBookmark from '@/components/fetchComponents/DELETE/removeBookmarkDELETE';
import addBookmark from '@/components/fetchComponents/POST/addBookmarkPOST';

// Reusable confirmation modal
const ConfirmationModal = ({ isOpen, onClose, onConfirm, message }) => {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white rounded-lg shadow-lg p-6 w-96">
                <p className="text-xl font-semibold text-center">{message}</p>
                <div className="flex justify-center gap-4 mt-6">
                    <button
                        onClick={onConfirm}
                        className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Confirm
                    </button>
                    <button
                        onClick={onClose}
                        className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    );
};

const ProjectDetails = ({ params }) => {
    const { projectId } = params;
    const router = useRouter();
    const [project, setProject] = useState(null);
    const [bookmarked, setBookmarked] = useState(false);
    const [loading, setLoading] = useState(true);
    const [isDeleteModalOpen, setDeleteModalOpen] = useState(false);
    const [isBanModalOpen, setBanModalOpen] = useState(false);
    const [claims, setClaims] = useState(null);
    const { setupStompClient } = useWebSocket();

    const handleDeleteProject = async () => {
        try {
            await deleteProjectById(projectId);
            alert("Project deleted successfully.");
            router.push("/");
        } catch (error) {
            alert("Failed to delete project.");
        } finally {
            setDeleteModalOpen(false);
        }
    };

    const handleBanUser = async () => {
        try {
            await deleteUserById(project.userId);
            alert("User banned successfully.");
            router.push("/");
        } catch (error) {
            alert("Failed to ban user.");
        } finally {
            setBanModalOpen(false);
        }
    };

    async function toggleBookmark() {

        if (bookmarked) {
            await removeBookmark(projectId, TokenManager.getClaims().userId);

        } else {
            const bookmarkdata = {
                projectId: projectId,
                userId: TokenManager.getClaims().userId
            }

            const bkresponse = await addBookmark(bookmarkdata);
        }

        const isBookmarked = await isProjectBookmarked(projectId);
        setBookmarked(isBookmarked);

    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const data = await fetchProjectData(projectId);
                if (!data) throw new Error("Project not found");
                setProject(data);

                // Fetch claims from TokenManager
                const fetchedClaims = TokenManager.getClaims();
                setClaims(fetchedClaims);  // This sets the 'claims' state

                if (fetchedClaims) {
                    const isBookmarked = await isProjectBookmarked(projectId);
                    setBookmarked(isBookmarked);
                }

            } catch (error) {
                console.error("Error loading project data:", error);
                router.push("/404");
            } finally {
                setLoading(false);
            }
        };

        if (projectId) fetchData();
    }, [projectId]);

    if (loading) {
        return (
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
                    <p className="mt-4 text-xl font-semibold">Loading project data</p>
                </div>
            </div>
        );
    }

    if (!project) return null;

    return (
        <PageFrame>
            <div className="px-6">
                <div className="font-bold text-4xl tracking-wide py-10 px-16">
                    {project.name}
                </div>

                <div className="flex px-16 h-[500px]">
                    <div className="w-2/3 px-6">
                        <ProjectImageCarousel projectImages={project.images} />
                    </div>

                    <div className="flex flex-col items-center justify-center lg:w-1/3 border-2 rounded-3xl px-4">
                        <div className="w-full flex p-4">
                            <div className="w-1/3 flex flex-col items-center justify-center">
                                <ProjectProgressBar
                                    moneyRaised={project.moneyRaised}
                                    fundingGoal={project.fundingGoal}
                                />
                            </div>
                            <div className="w-2/3 text-center flex flex-col items-center justify-center">
                                <p className="text-2xl font-bold">
                                    <NumberTicker value={project.moneyRaised} /> € raised
                                </p>
                                <p className="text-2xl font-thin">
                                    {project.fundingGoal} € goal
                                </p>
                            </div>
                        </div>
                        <PaymentButton projectId={projectId} />
                        <DonorsList projectId={projectId} />
                    </div>
                </div>

                <div className="px-16 flex">
                    <div className="w-2/3">
                        <div className="border-y-4 border-gray-200 mx-6 my-6 py-4 flex items-center gap-4">
                            <div className="w-1/5 flex items-center justify-center">
                                <img
                                    src={project.ownerProfilePicture}
                                    alt="Owner Profile"
                                    className="h-20 w-20 object-cover rounded-3xl"
                                />
                            </div>
                            <div className="w-4/5 flex items-center justify-start">
                                <p className="text-xl">
                                    Project was created by {project.ownerName}
                                </p>
                            </div>
                        </div>

                        {claims && claims.roles[0] === "admin" && (
                            <div className="flex">
                                <button
                                    onClick={() => setDeleteModalOpen(true)}
                                    className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded w-1/2 m-5"
                                >
                                    Delete Project
                                </button>

                                <button
                                    onClick={() => setBanModalOpen(true)}
                                    className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded w-1/2 m-5"
                                >
                                    Ban User
                                </button>
                            </div>
                        )}

                        <p className="text-3xl font-bold tracking-wide py-10">About</p>
                        <p className="text-2xl font-light text-justify">
                            {project.description}
                        </p>
                    </div>

                    <div className="w-1/3 my-6 justify-center">

                        {claims ? (
                            <button
                                onClick={toggleBookmark} // Toggle bookmark state when clicked
                                className={`px-6 py-2 rounded-md text-white font-semibold transition-all duration-300 ease-in-out ${bookmarked ? 'bg-green-500 hover:bg-green-600' : 'bg-gray-500 hover:bg-gray-600'} w-full`}
                            >
                                {bookmarked ? '⭐ Bookmarked' : '❌ Not Bookmarked'}
                            </button>
                        ) : (
                            <></>
                        )}


                    </div>
                </div>
            </div>

            {/* Modals */}
            <ConfirmationModal
                isOpen={isDeleteModalOpen}
                onClose={() => setDeleteModalOpen(false)}
                onConfirm={handleDeleteProject}
                message="Are you sure you want to delete this project?"
            />
            <ConfirmationModal
                isOpen={isBanModalOpen}
                onClose={() => setBanModalOpen(false)}
                onConfirm={handleBanUser}
                message="Are you sure you want to ban this user?"
            />
        </PageFrame >
    );
};

export default ProjectDetails;
