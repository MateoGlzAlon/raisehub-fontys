"use client";
import React from "react";
import HighlightedProjectsList from "@/components/pageComponents/landingPage/HighlightedProjectsList";
import DotPatternAndTitle from "@/components/pageComponents/landingPage/DotPattern-title";
import NewProjectsList from "@/components/pageComponents/landingPage/NewProjectsList";
import { useRouter } from 'next/navigation';

import PageFrame from "@/components/generalComponents/pageFrame/PageFrame";
import ProjectFilterTable from "@/components/pageComponents/landingPage/ProjectsFilterTable";


export default function Home() {

  return (
    <main>

      <PageFrame>

        <DotPatternAndTitle />

        <HighlightedProjectsList />

        <NewProjectsList />

        <ProjectFilterTable />

      </PageFrame>

    </main>
  );
}
