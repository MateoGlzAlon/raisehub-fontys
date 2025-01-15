"use client";
import { useState } from "react";
import createPaymentPOST from "@/components/fetchComponents/POST/createPaymentPOST.jsx";
import TokenManager from "@/app/apis/TokenManager";
import { useRouter } from "next/navigation";
import { useWebSocket } from "@/components/generalComponents/WebSocketContext"



export default function PaymentButton({ projectId }) {
    const [moneyAmount, setMoneyAmount] = useState("");
    const [modalVisible, setModalVisible] = useState(false);
    const [modalContent, setModalContent] = useState(null);
    const [isProcessing, setIsProcessing] = useState(false);
    const router = useRouter();
    const { sendMessage } = useWebSocket();


    const handlePayment = () => {
        const claims = TokenManager.getClaims();

        if (!claims) {
            setModalContent({
                message: "You need to be logged in to make a payment.",
                buttons: [
                    {
                        text: "Go to Login",
                        action: () => {
                            setModalVisible(false);
                            router.push("/login");
                        },
                    },
                    { text: "Close", action: () => setModalVisible(false) },
                ],
            });
            setModalVisible(true);
            return;
        }

        if (!moneyAmount || parseFloat(moneyAmount) <= 0) {
            setModalContent({
                message: "Please enter a valid amount.",
                buttons: [{ text: "Close", action: () => setModalVisible(false) }],
            });
            setModalVisible(true);
            return;
        }

        setModalContent({
            message: `Are you sure you want to pay ${moneyAmount} € to this project?`,
            buttons: [
                { text: "Confirm", action: confirmPayment },
                { text: "Cancel", action: () => setModalVisible(false) },
            ],
        });
        setModalVisible(true);
    };

    const confirmPayment = async () => {
        setIsProcessing(true);

        const claims = TokenManager.getClaims();

        if (!claims) {
            setModalContent({
                message: "Session expired. Please log in again.",
                buttons: [
                    {
                        text: "Go to Login",
                        action: () => {
                            setModalVisible(false);
                            router.push("/login");
                        },
                    },
                ],
            });
            setModalVisible(true);
            setIsProcessing(false);
            return;
        }

        const paymentData = {
            projectId,
            backerId: claims.userId,
            amountFunded: moneyAmount,
            paymentDate: new Date(),
        };

        try {
            const response = await createPaymentPOST(paymentData);

            if (response.status === 200) {
                setModalContent({
                    message: "Payment successful! Thank you for your support.",
                    buttons: [
                        {
                            text: "Close",
                            action: () => {
                                setModalVisible(false);
                                //TO-DO
                                //window.location.reload();
                            },
                        },
                    ],
                });

                const notificationInfo = {
                    to: projectId,
                    title: "💸 - Payment received",
                    description: `A backer has contributed ${moneyAmount}€ to one of your projects`
                }

                sendMessage(notificationInfo);

                setMoneyAmount("");
            } else {
                setModalContent({
                    message: "Payment failed. Please try again.",
                    buttons: [{ text: "Close", action: () => setModalVisible(false) }],
                });
            }
        } catch (error) {
            console.error("Error processing payment:", error);
            setModalContent({
                message: "An error occurred while processing your payment.",
                buttons: [{ text: "Close", action: () => setModalVisible(false) }],
            });
        } finally {
            setIsProcessing(false);
        }
    };

    return (
        <div className="flex flex-col items-center space-y-4 w-full">
            <div className="flex items-center space-x-2 w-full pb-3">
                <button
                    className="w-1/2 px-4 py-2 text-white bg-orange-500 rounded-lg hover:bg-orange-600 focus:outline-none h-full"
                    onClick={handlePayment}
                >
                    Pay
                </button>
                <input
                    id="inputMoneyAmount"
                    type="number"
                    value={moneyAmount}
                    onChange={(e) => setMoneyAmount(e.target.value)}
                    placeholder="Enter amount"
                    step="0.1"
                    min="0"
                    className="w-1/2 h-full px-4 py-2 text-gray-700 bg-gray-100 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                />
            </div>

            {modalVisible && (
                <div className=" fixed inset-0 flex items-center justify-center bg-black bg-opacity-60 z-[9999] h-screen">
                    <div className="bg-white rounded-lg shadow-lg p-6 w-96 text-center">
                        <p className="text-lg font-medium mb-6">{modalContent?.message}</p>
                        <div className="flex justify-center space-x-4">
                            {modalContent?.buttons.map((button, index) => (
                                <button
                                    key={index}
                                    onClick={button.action}
                                    className={`px-4 py-2 rounded-lg ${button.text === "Confirm"
                                        ? "bg-green-500 text-white hover:bg-green-600"
                                        : button.text === "Cancel"
                                            ? "bg-red-500 text-white hover:bg-red-600"
                                            : "bg-blue-500 text-white hover:bg-blue-600"
                                        }`}
                                    disabled={isProcessing && button.text === "Confirm"}
                                >
                                    {isProcessing && button.text === "Confirm"
                                        ? "Processing..."
                                        : button.text}
                                </button>
                            ))}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
