import "./globals.css";
import { DATA } from "@/app/data";
import { Toaster } from "@/components/ui/toaster";
import { WebSocketProvider } from "@/components/generalComponents/WebSocketContext";

export const metadata = {
  title: DATA.projectName,
  description: "Crowdfunding page",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">

      <body>
        <WebSocketProvider>
          {children}
          <Toaster />
        </WebSocketProvider>

      </body>
    </html>
  );
}
