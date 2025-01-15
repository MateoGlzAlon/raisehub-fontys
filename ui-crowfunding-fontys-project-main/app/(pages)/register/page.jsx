"use client";
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button'; // Using your existing Button component
import Image from 'next/image';
import { DATA } from '@/app/data';

import registerUserPOST from '@/components/fetchComponents/POST/registerUserPOST';

export default function RegisterPage() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [emailError, setEmailError] = useState(''); // State for email-specific errors
    const router = useRouter();

    function handleRegister(name, email, password) {
        return registerUserPOST(name, email, password, "user", "https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/placeholder_raisehub.png");
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validate passwords match
        if (password !== confirmPassword) {
            setErrorMessage("Passwords do not match!");
            return;
        }

        // Clear error message if validation is successful
        setErrorMessage('');
        setEmailError(''); // Reset email error message

        // Handle registration logic here (e.g., create user)
        const registerResponse = await handleRegister(name, email, password);

        if (registerResponse.status === 409) {
            // Email is already registered
            setEmailError("This email is already registered.");
        } else if (registerResponse.status === 200) {
            // Successful registration, redirect to login
            router.push('/login');
        } else {
            // Handle other errors (e.g., server error)
            setErrorMessage("An error occurred. Please try again.");
        }
    };

    return (
        <div className="min-h-screen flex flex-col lg:flex-row bg-white">
            {/* Left Section with Image and Text */}
            <div className="flex flex-col justify-center items-center lg:w-1/2 w-full bg-gray-100 p-8">
                <Image
                    src="/logoColor.svg" // Replace with your image path
                    alt={`${DATA.projectName} Logo`}
                    onClick={() => router.push('/')}
                    width={400}
                    height={400}
                    className="mb-6 cursor-pointer"
                />
                <h1 className="text-4xl font-extrabold text-gray-900" onClick={() => router.push('/')} >
                    {DATA.projectName}
                </h1>
                <p className="mt-4 text-lg text-gray-600">Join the community and bring your favorite projects to life!</p>
            </div>

            {/* Right Section with Registration Form */}
            <div className="flex min-h-screen flex-col justify-center py-12 sm:px-6 lg:px-8 w-full lg:w-1/2 bg-white">
                <div className="sm:mx-auto sm:w-full sm:max-w-md">
                    <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
                        Create a new account
                    </h2>
                </div>

                <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
                    <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
                        <form className="space-y-6" onSubmit={handleSubmit}>
                            {errorMessage && (
                                <div className="text-red-500 text-sm">
                                    {errorMessage}
                                </div>
                            )}
                            <div>
                                <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                                    Full Name
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="name"
                                        name="name"
                                        type="text"
                                        autoComplete="name"
                                        required
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                    />
                                </div>
                            </div>

                            <div>
                                <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                                    Email address
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="email"
                                        name="email"
                                        type="email"
                                        autoComplete="email"
                                        required
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        className={`w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm 
                                            ${emailError ? 'border-red-500' : 'border-gray-300'}`}
                                    />
                                </div>
                                {/* Display email error message under email input */}
                                {emailError && (
                                    <div className="mt-1 text-sm text-red-500">
                                        {emailError}
                                    </div>
                                )}
                            </div>

                            <div>
                                <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                                    Password
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="password"
                                        name="password"
                                        type="password"
                                        autoComplete="new-password"
                                        required
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                    />
                                </div>
                            </div>

                            <div>
                                <label htmlFor="confirm-password" className="block text-sm font-medium text-gray-700">
                                    Confirm Password
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="confirm-password"
                                        name="confirm-password"
                                        type="password"
                                        autoComplete="new-password"
                                        required
                                        value={confirmPassword}
                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                    />
                                </div>
                            </div>

                            <div>
                                <Button type="submit" variant="default" className="w-full">
                                    Sign Up
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
                                    onClick={() => router.push('/login')}
                                >
                                    Already have an account? Log In
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
