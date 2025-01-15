import axios from 'axios';

import { DATA } from '@/app/data';

async function fetchProjectListData(endpoint) {
    try {
        const response = await axios.get(`${DATA.origin}/projects/${endpoint}`);
        return (response.data);
    } catch (error) {
        console.error(`Error fetching projects in endpoint: (${endpoint}):`, error);
        return null;
    }
}

export default fetchProjectListData;