import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";


export default async function getProfilePictureGET(userId) {
    const response = await axios.get(`${DATA.origin}/users/picture/${userId}`,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        }
    )

    return response.data;
}