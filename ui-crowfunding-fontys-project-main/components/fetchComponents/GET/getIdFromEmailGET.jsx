import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";


export default async function getIdFromEmailGET(userEmail) {
    const response = await axios.get(`${DATA.origin}/users/email/${userEmail}`,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        }
    )

    return response.data;
}