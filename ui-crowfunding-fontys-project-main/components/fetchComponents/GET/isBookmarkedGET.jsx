import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";

export default async function isProjectBookmarked(projectId) {

    const userId = TokenManager.getClaims().userId;

    const response = await axios.get(`${DATA.origin}/projects/bookmark/${userId}/${projectId}`,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        }
    );

    return response.data;
}
