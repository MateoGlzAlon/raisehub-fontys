import React from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";

function PageFrame({ children }) {
    return (
        <div className="min-h-screen flex flex-col">
            {/* Navbar */}
            <Navbar />

            {/* Main Content */}
            <main className="flex-grow">
                <div className="mx-6 pt-16">{children}</div>
            </main>

            {/* Footer */}
            <Footer />
        </div>
    );
}

export default PageFrame;
