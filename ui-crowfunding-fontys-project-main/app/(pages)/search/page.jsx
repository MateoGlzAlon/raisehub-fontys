"use client";

import { Suspense } from "react";
import PageFrame from "@/components/generalComponents/pageFrame/PageFrame";
import SearchProjectList from "@/components/pageComponents/search/SearchProjectList";

function SearchProjectPage() {
    return (
        <PageFrame>
            {/* Wrapping SearchProjectList in a Suspense boundary */}
            <Suspense fallback={<div>Loading projects...</div>}>
                <SearchProjectList />
            </Suspense>
        </PageFrame>
    );
}

export default SearchProjectPage;
