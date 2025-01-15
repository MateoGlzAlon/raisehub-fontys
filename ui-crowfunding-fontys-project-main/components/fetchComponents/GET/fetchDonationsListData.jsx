import axios from 'axios';

import { DATA } from '@/app/data';

async function fetchDonationsListData(projectId) {
    try {
        const response = await axios.get(`${DATA.origin}/payments/projects/notifications/${projectId}`);
        return (response.data);
    } catch (error) {
        console.error("Failed to fetch project donations:", error);
        return null;
    }
}

export default fetchDonationsListData;