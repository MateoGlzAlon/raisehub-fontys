import axios from "axios";
import { DATA } from "@/app/data";
import TokenManager from "@/app/apis/TokenManager";

export default async function addBookmark(bookmarkData) {


    const response = await axios.post(`${DATA.origin}/projects/bookmark`,
        bookmarkData,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        }
    );

    return response;
}
