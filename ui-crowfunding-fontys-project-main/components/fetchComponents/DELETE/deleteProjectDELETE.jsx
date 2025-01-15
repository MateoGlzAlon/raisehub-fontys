import axios from 'axios';

import { DATA } from '@/app/data';
import TokenManager from '@/app/apis/TokenManager';

export default async function deleteProject(projectId) {
    if (projectId) {
        try {
            await axios.delete(`${DATA.origin}/projects/${projectId}`,
                {
                    headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
                }
            );
        } catch (error) {
            console.error("Failed to delete project: ", projectId);
        }
    }
}
