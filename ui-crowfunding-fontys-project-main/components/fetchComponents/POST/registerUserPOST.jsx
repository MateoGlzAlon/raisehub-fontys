import axios from "axios";
import { DATA } from "@/app/data";

export default async function registerUserPOST(fullName, email, password, role, profilePicture) {
    try {
        const response = await axios.post(`${DATA.origin}/users`, {
            name: fullName,
            email: email,
            password: password,
            role: role,
            profilePicture: profilePicture
        });

        return response;
    } catch (error) {
        if (error.response) {
            console.error("Error status code:", error.response.status);
            return error.response.status;
        } else {
            console.error("Error message:", error.message);
            return error.message;
        }
    }
}
