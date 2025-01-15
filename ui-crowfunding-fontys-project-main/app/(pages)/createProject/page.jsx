"use client";
import React, { useState } from 'react';
import axios from 'axios';
import { DATA } from '@/app/data';
import createProjectPOST from '@/components/fetchComponents/POST/createProjectPOST';
import PageFrame from '@/components/generalComponents/pageFrame/PageFrame';
import { useRouter } from 'next/navigation'
import TokenManager from '@/app/apis/TokenManager';

function CreateProjectPage() {
    const router = useRouter();

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        location: '',
        type: '',
        dateCreated: '',
        fundingGoal: '',
        userId: '',
    });

    const [images, setImages] = useState([]);
    const [imagePreviews, setImagePreviews] = useState([]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
    };

    const handleImageChange = (e) => {
        const files = Array.from(e.target.files);
        if (files.length + images.length > 3) {
            alert('You can upload up to 3 images only.');
            return;
        }
        const newImages = [...images, ...files];
        const previews = newImages.map((file) => URL.createObjectURL(file));
        setImages(newImages);
        setImagePreviews(previews);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const updatedFormData = { ...formData, dateCreated: new Date().toISOString(), userId: TokenManager.getClaims().userId };

        try {
            const res = await createProjectPOST(updatedFormData, images);
            router.push(`/projects/${res}`);
        } catch (error) {
            console.error('Error creating project with data:', updatedFormData, error);
        }
    };

    return (
        <PageFrame>
            <div className="min-h-screen flex justify-center items-center p-6">
                <div className="w-full max-w-4xl bg-white rounded-lg  border-4  p-8">
                    <h1 className="text-3xl font-bold text-center mb-6  ">New Project</h1>

                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                            <div>
                                <label className="block text-gray-700 font-medium">Project Name</label>
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleChange}
                                    className="w-full px-4 py-3 border rounded-lg mt-2 focus:outline-none focus:ring-2 focus:ring-indigo-600"
                                    placeholder="Enter project name"
                                    required
                                />
                            </div>

                            <div>
                                <label className="block text-gray-700 font-medium">Location</label>
                                <input
                                    type="text"
                                    name="location"
                                    value={formData.location}
                                    onChange={handleChange}
                                    className="w-full px-4 py-3 border rounded-lg mt-2 focus:outline-none focus:ring-2 focus:ring-indigo-600"
                                    placeholder="Enter project location"
                                    required
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-gray-700 font-medium">Description</label>
                            <textarea
                                name="description"
                                value={formData.description}
                                onChange={handleChange}
                                className="w-full px-4 py-3 border rounded-lg mt-2 focus:outline-none focus:ring-2 focus:ring-indigo-600"
                                placeholder="Describe your project"
                                rows="4"
                                required
                            />
                        </div>

                        <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                            <div>
                                <label className="block text-gray-700 font-medium">Category</label>
                                <select
                                    name="type"
                                    value={formData.type}
                                    onChange={handleChange}
                                    className="w-full px-4 py-3 border rounded-lg mt-2 focus:outline-none focus:ring-2 focus:ring-indigo-600"
                                    required
                                >
                                    <option value="">Select a category</option>
                                    {DATA.projectTypes.map((item) => (
                                        <option value={item} key={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div>
                                <label className="block text-gray-700 font-medium">Funding Goal</label>
                                <input
                                    type="number"
                                    name="fundingGoal"
                                    value={formData.fundingGoal}
                                    onChange={handleChange}
                                    className="w-full px-4 py-3 border rounded-lg mt-2 focus:outline-none focus:ring-2 focus:ring-indigo-600"
                                    placeholder="Enter fundraising goal"
                                    required
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-gray-700 font-medium">Project Images (Up to 3)</label>
                            <input
                                type="file"
                                name="images"
                                onChange={handleImageChange}
                                className="w-full px-4 py-3 border rounded-lg mt-2 focus:outline-none"
                                accept="image/*"
                                multiple
                                required
                            />
                            <div className="flex mt-4 gap-4">
                                {imagePreviews.map((preview, index) => (
                                    <img
                                        key={index}
                                        src={preview}
                                        alt={`preview-${index}`}
                                        className="w-24 h-24 object-cover rounded-lg border"
                                    />
                                ))}
                            </div>
                        </div>

                        <div className="text-center mt-6">
                            <button
                                type="submit"
                                className="px-8 py-4 bg-indigo-600 text-white font-semibold rounded-lg hover:bg-indigo-700 transition duration-300"
                            >
                                Create Project
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </PageFrame>
    );
}

export default CreateProjectPage;
