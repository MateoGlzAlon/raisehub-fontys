import axios from 'axios';

import { DATA } from '@/app/data';
import TokenManager from '@/app/apis/TokenManager';

export default async function removeBookmark(projectId, userId) {
    if (projectId) {
        try {
            await axios.delete(`${DATA.origin}/projects/bookmark/${projectId}/${userId}`,
                {
                    headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
                }
            );
        } catch (error) {
            console.error("Failed to delete bookmark: ", error);
        }
    }
}
