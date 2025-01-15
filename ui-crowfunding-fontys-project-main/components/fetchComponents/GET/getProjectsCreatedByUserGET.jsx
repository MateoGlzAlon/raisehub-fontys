import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";

export default async function getProjectsCreatedByUserGET() {

    const userId = TokenManager.getClaims().userId;

    const response = await axios.get(`${DATA.origin}/projects/users/${userId}`,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        }
    );

    return response.data;
}
