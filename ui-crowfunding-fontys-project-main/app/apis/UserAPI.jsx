import axios from "axios";
import TokenManager from "./TokenManager";
import { DATA } from "@/app/data";

const UsersAPI = {
    getUserToken: (studentId) => axios.get(`${DATA.origin}/users/${studentId}`,
        {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
        })
        .then(response => response.data)

}

export default UsersAPI;