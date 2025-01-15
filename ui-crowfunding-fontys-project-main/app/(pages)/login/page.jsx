"use client"

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import Image from "next/image";
import AuthAPI from "@/app/apis/AuthAPI";
import { useWebSocket } from "@/components/generalComponents/WebSocketContext";
import getProjectIdsOwnedByUserGET from "@/components/fetchComponents/GET/getProjectIdsOwnedByUserGET";
import { DATA } from "@/app/data";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");  // To store the error message
    const [usernameError, setUsernameError] = useState(false); // To highlight the username input on error
    const [passwordError, setPasswordError] = useState(false); // To highlight the password input on error
    const { setupStompClient } = useWebSocket();
    const router = useRouter();

    const handleLogIn = async (username, password) => {
        try {
            const response = await AuthAPI.login(username, password);
            return response;
        } catch (error) {
            console.error("Login failed:", error.message);
            return null;
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage("");
        setUsernameError(false);
        setPasswordError(false);

        try {
            const loginResponse = await handleLogIn(username, password);

            if (loginResponse) {
                // Fetch the user's project IDs
                const projectIds = await getProjectIdsOwnedByUserGET(username);

                // Set up the WebSocket connection
                setupStompClient(projectIds);

                // Navigate to the homepage
                router.push("/");
            } else {
                setErrorMessage("Invalid username or password. Please try again.");
                setUsernameError(true);
                setPasswordError(true);
            }
        } catch (error) {
            console.error("Error during login submission:", error.message);
        }
    };

    return (
        <div className="min-h-screen flex bg-white">
            {/* Left Section with Image and Text */}
            <div className="flex flex-col justify-center items-center w-1/2 bg-gray-100 p-8">
                <Image
                    src="/logoColor.svg" // Replace with your image path
                    alt={`${DATA.projectName} Logo`}
                    width={400}
                    height={400}
                    onClick={() => router.push("/")}
                    className="mb-6 cursor-pointer"
                />
                <h1 className="text-4xl font-extrabold text-gray-900" onClick={() => router.push("/")}>
                    {DATA.projectName}
                </h1>
                <p className="mt-4 text-lg text-gray-600">
                    Join the community and bring your favorite projects to life!
                </p>
            </div>

            {/* Right Section with Login Form */}
            <div className="flex flex-col justify-center w-1/2 p-12 sm:px-6 lg:px-8">
                <div className="sm:mx-auto sm:w-full sm:max-w-md">
                    <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
                        Log in to your account
                    </h2>
                </div>

                <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
                    <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
                        <form className="space-y-6" onSubmit={handleSubmit}>
                            {/* Username */}
                            <div>
                                <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                                    Email address
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="username"
                                        name="username"
                                        type="email"
                                        autoComplete="email"
                                        required
                                        value={username}
                                        onChange={(e) => setUsername(e.target.value)}
                                        className={`w-full px-3 py-2 border rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm ${usernameError ? 'border-red-500' : 'border-gray-300'}`}
                                    />
                                </div>
                            </div>

                            {/* Password */}
                            <div>
                                <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                                    Password
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="password"
                                        name="password"
                                        type="password"
                                        autoComplete="current-password"
                                        required
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        className={`w-full px-3 py-2 border rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm ${passwordError ? 'border-red-500' : 'border-gray-300'}`}
                                    />
                                </div>
                            </div>

                            {/* Error message */}
                            {errorMessage && <div className="text-red-500 text-sm mt-2">{errorMessage}</div>}

                            {/* Submit Button */}
                            <div>
                                <Button type="submit" variant="default" className="w-full">
                                    Log In
                                </Button>
                            </div>
                        </form>

                        <div className="mt-6">
                            <div className="relative">
                                <div className="absolute inset-0 flex items-center">
                                    <div className="w-full border-t border-gray-300" />
                                </div>
                                <div className="relative flex justify-center text-sm">
                                    <span className="px-2 bg-white text-gray-500">Or</span>
                                </div>
                            </div>

                            <div className="mt-6">
                                <Button
                                    variant="outline"
                                    className="w-full"
                                    onClick={() => router.push("/register")}
                                >
                                    Create a new account
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
