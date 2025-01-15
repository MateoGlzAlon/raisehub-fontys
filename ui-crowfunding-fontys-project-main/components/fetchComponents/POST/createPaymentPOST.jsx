import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";

export default async function createPaymentPOST(paymentData) {


    const response = await axios.post(`${DATA.origin}/payments`,
        paymentData,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        }
    );

    return response;
}
