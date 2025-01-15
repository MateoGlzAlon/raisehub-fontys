import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";

export default async function updateProfilePicturePOST(userId, newPicture) {

    try {
        const response = await axios.post(`${DATA.origin}/users/picture/${userId}`,
            { "newPicture": newPicture[0] },
            {
                headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
            }
        );

        if (response.status === 200 && response.data === true) {

        } else {
            console.error("Update failed:", response.data);
        }
    } catch (error) {
        console.error("Error updating profile picture:", error.response?.data || error.message);
    }
};

