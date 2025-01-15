import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";

export default async function getPaymentsMadeByUserGET(filter, page = 0, size = 5) {
    const userId = TokenManager.getClaims().userId;

    const response = await axios.get(`${DATA.origin}/payments/profile/${userId}`, {
        params: { filter, page, size },
        headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
    });

    return response;
}
