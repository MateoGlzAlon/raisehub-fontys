import axios from 'axios';
import { DATA } from '@/app/data';

async function getAllProjectsGET({
    type = null,
    minPercentageFunded = null,
    maxPercentageFunded = null,
    sortBy = "dateCreated",
    page = 0,
    size = 6,
    name = ""
} = {}) {
    try {
        // Build query string dynamically
        const queryParams = new URLSearchParams({
            ...(type && { type }),
            ...(minPercentageFunded && { minPercentageFunded }),
            ...(maxPercentageFunded && { maxPercentageFunded }),
            sortBy,
            page,
            size,
            name
        }).toString();

        const response = await axios.get(`${DATA.origin}/projects/filters/pagination?${queryParams}`);

        return response.data;
    } catch (error) {
        console.error("Failed to get all projects: ", error);
        return null;
    }
}

export default getAllProjectsGET;
