import SparklesText from "@/components/magicui/sparkles-text";
import DotPattern from "@/components/magicui/dot-pattern";
import { cn } from "@/lib/utils";

import { DATA } from "@/app/data";


function DotPatternAndTitle() {
    return (
        <div className="relative py-2">
            <div className="relative flex h-[400px] flex-col items-center justify-center overflow-visible">
                <div className="z-10 whitespace-pre-wrap text-center text-5xl font-bold tracking-normal text-black dark:text-white bg-white rounded-full px-12 py-10">
                    <SparklesText
                        text={DATA.heroTitle}
                        colors={{ first: '#2a44f8', second: '#ffff00' }}
                        sparklesCount={8}
                    />
                </div>

                {/* Ensure DotPattern is contained within the height of its parent */}
                <DotPattern
                    width={40}
                    height={40}
                    cr={2}
                    className={cn(
                        "[mask-image:radial-gradient(700px_circle_at_center,white,transparent)] absolute inset-0 z-0"
                    )}
                />
            </div>
        </div>
    );
}

export default DotPatternAndTitle;
