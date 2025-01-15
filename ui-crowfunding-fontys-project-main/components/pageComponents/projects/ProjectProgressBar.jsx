"use client"

import { useEffect, useState } from "react"

import AnimatedCircularProgressBar from "@/components/magicui/animated-circular-progress-bar"

export function ProjectProgressBar({ moneyRaised, fundingGoal }) {
    const [value, setValue] = useState(0)

    useEffect(() => {

        setValue(moneyRaised / fundingGoal * 100)

    }, [moneyRaised])


    return (
        <AnimatedCircularProgressBar
            max={100}
            min={0}
            value={value}
            gaugePrimaryColor="rgb(50,205,50)"
            gaugeSecondaryColor="rgba(0, 0, 0, 0.1)"
        />
    )
}
