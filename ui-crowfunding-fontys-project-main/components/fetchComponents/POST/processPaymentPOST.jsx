import axios from "axios";

export default async function () {

    const response = await axios.post(`${DATA.origin}/projects/payments`);

}
