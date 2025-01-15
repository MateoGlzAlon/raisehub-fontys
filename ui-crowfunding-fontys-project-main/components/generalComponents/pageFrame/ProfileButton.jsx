"use client";

import TokenManager from "@/app/apis/TokenManager";
import { useState, useEffect, useRef } from "react";
import { useRouter } from 'next/navigation';
import getProfilePictureGET from "@/components/fetchComponents/GET/getProfilePictureGET";

export default function ProfileButton() {
    const [isOpen, setIsOpen] = useState(false);
    const [profilePicture, setProfilePicture] = useState("https://avatar.iran.liara.run/public");
    const userId = TokenManager.getClaims().userId;
    const dropdownRef = useRef(null);
    const router = useRouter();

    const toggleDropdown = () => {
        setIsOpen((prev) => !prev);
    };

    const closeDropdown = () => {
        setIsOpen(false);
    };

    // Handle Log Out
    const handleLogout = () => {
        TokenManager.clear(); // Clear the token or claims
        router.push("/login"); // Redirect to login after logging out
    };

    // Fetch Profile Picture
    useEffect(() => {
        const fetchProfilePicture = async () => {
            try {
                const picture = await getProfilePictureGET(userId);
                if (picture) {
                    setProfilePicture(picture);
                }
            } catch (error) {
                console.error("Error fetching profile picture:", error);
            }
        };

        fetchProfilePicture();

        // Close dropdown when clicking outside
        const handleClickOutside = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                closeDropdown();
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [userId]);

    return (
        <div className="relative inline-block" ref={dropdownRef}>
            {/* Profile Avatar */}
            <button
                onClick={toggleDropdown}
                className="flex items-center space-x-2 focus:outline-none"
                aria-expanded={isOpen}
            >
                <img
                    src={profilePicture}
                    alt="User Avatar"
                    id="user-avatar"
                    className="w-10 h-10 rounded-full object-cover border border-gray-300 dark:border-gray-700"
                />
            </button>

            {/* Dropdown Menu */}
            {isOpen && (
                <div className="absolute right-0 mt-2 w-40 rounded-lg shadow-lg bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 z-50">
                    <ul className="">
                        <li>
                            <button
                                className="flex items-center w-full px-4 py-3 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors border-b-2"
                                onClick={() => router.push("/profile")}
                            >
                                My Profile
                            </button>
                        </li>
                        <li>
                            <button
                                className="flex items-center w-full px-4 py-3 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors border-b-2"
                                onClick={() => router.push("/bookmarks")}
                            >
                                Bookmarks
                            </button>
                        </li>
                        <li>
                            <button
                                className="flex items-center w-full px-4 py-3 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
                                onClick={handleLogout}
                            >
                                Log out
                            </button>
                        </li>
                    </ul>
                </div>
            )}
        </div>
    );
}
