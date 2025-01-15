import React, { useEffect, useState } from 'react';
import ProjectsList from './ProjectsList';


// Sample usage of the component
const NewProjectsList = () => {

    return (
        <ProjectsList title="New Projects" endpoint="new" />
    );
};

export default NewProjectsList;

