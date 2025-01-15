import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";


export default async function getTotalPaymentsGET(userId, timeFilter) {

    const response = await axios.get(`${DATA.origin}/payments/totalPayments/${userId}`,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` },
            params: { time: timeFilter },
        },
    )

    return response.data;
}