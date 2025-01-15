import axios from 'axios';

import { DATA } from '@/app/data';
import TokenManager from '@/app/apis/TokenManager';

export default async function deleteUser(userId) {
    if (userId) {
        try {
            await axios.delete(`${DATA.origin}/users/${userId}`,
                {
                    headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
                }
            );
        } catch (error) {
            console.error("Failed to delete user: ", userId);
        }
    }
}
