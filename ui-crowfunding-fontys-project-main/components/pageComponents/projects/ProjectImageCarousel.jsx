import Carousel from 'react-multi-carousel';
import "react-multi-carousel/lib/styles.css";

function ProjectImageCarousel({ projectImages }) {

    const responsive = {
        superLargeDesktop: {
            breakpoint: { max: 4000, min: 3000 },
            items: 1
        },
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 1
        },
        tablet: {
            breakpoint: { max: 1024, min: 464 },
            items: 1
        },
        mobile: {
            breakpoint: { max: 464, min: 0 },
            items: 1
        }
    };

    return (
        <Carousel responsive={responsive} className='align-middle'>
            {projectImages.map((image, id) => (
                <div
                    key={id}
                    className="flex items-center justify-center h-[500px] w-full overflow-hidden"
                >
                    <img
                        src={image}
                        alt={`Project image ${id + 1}`}
                        className='w-full h-full object-contain border-2 rounded-2xl'
                    />
                </div>
            ))}
        </Carousel>
    );
}

export default ProjectImageCarousel;
