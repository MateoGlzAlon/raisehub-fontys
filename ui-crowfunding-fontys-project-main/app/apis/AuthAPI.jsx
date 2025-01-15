import axios from "axios";
import TokenManager from "./TokenManager";
import { DATA } from "@/app/data";

const AuthAPI = {
    login: (username, password) => axios.post(`${DATA.origin}/tokens`, { username, password })
        .then(response => response.data.accessToken)
        .then(accessToken => TokenManager.setAccessToken(accessToken))
}

export default AuthAPI;