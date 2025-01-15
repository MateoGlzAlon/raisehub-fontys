import axios from 'axios';

import { DATA } from '@/app/data';

export default async function fetchProjectData(projectId) {
    if (projectId) {
        try {
            const response = await axios.get(`${DATA.origin}/projects/details/${projectId}`);
            return response.data;
        } catch (error) {
            console.error("Failed to fetch project data:", error);
            return null;
        }
    }
}
