"use client";

import React, { createContext, useContext, useState } from 'react';
import { Client } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import { useToast } from "@/hooks/use-toast"
import { DATA } from "@/app/data"


const WebSocketContext = createContext();

export const WebSocketProvider = ({ children }) => {
    const [stompClient, setStompClient] = useState(null);

    const { toast } = useToast()

    const setupStompClient = (projectIds) => {

        const client = new Client({
            brokerURL: `ws://${DATA.shortOrigin}/ws`,
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        client.onConnect = () => {
            projectIds.forEach((projectId) => {
                client.subscribe(`/projectws/${projectId}`, (data) => {
                    onPaymentReceived(data);
                });
            })
        };

        client.activate();
        setStompClient(client);
    };

    const sendMessage = (newMessage) => {
        if (!stompClient) {
            console.error("WebSocket client not initialized");
            return;
        }

        const payload = {
            id: uuidv4(),
            to: newMessage.to,
            title: newMessage.title,
            description: newMessage.description
        };

        stompClient.publish({
            destination: `/projectws/${payload.to}`,
            body: JSON.stringify(payload),
        });
    };

    const onPaymentReceived = (data) => {
        const toastMsg = JSON.parse(data.body);

        toast({
            title: `${toastMsg.title}`,
            description: `${toastMsg.description}`,
        })
    };

    return (
        <WebSocketContext.Provider value={{ setupStompClient, sendMessage }}>
            {children}
        </WebSocketContext.Provider>
    );
};

export const useWebSocket = () => {
    return useContext(WebSocketContext);
};
