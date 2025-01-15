package com.fontys.crowdfund.testBusiness;

import com.fontys.crowdfund.business.impl.ProjectServiceImpl;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOBookmark;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOBookmark;
import com.fontys.crowdfund.persistence.entity.BookmarkEntity;
import com.fontys.crowdfund.persistence.specialdto.ProjectDetailsDTO;
import com.fontys.crowdfund.repository.ProjectBookmarksRepository;
import com.fontys.crowdfund.repository.ProjectRepository;
import com.fontys.crowdfund.repository.ProjectImagesRepository;
import com.fontys.crowdfund.repository.UserRepository;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOProject;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOProjectImage;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOProject;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOProjectImage;
import com.fontys.crowdfund.persistence.entity.ProjectEntity;
import com.fontys.crowdfund.persistence.entity.ProjectImageEntity;
import com.fontys.crowdfund.persistence.entity.UserEntity;
import com.fontys.crowdfund.persistence.specialdto.ProjectOnlyCoverLandingPage;

import org.springframework.data.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectImagesRepository projectImageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectBookmarksRepository projectBookmarksRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private static ProjectEntity project1;
    private static ProjectEntity project2;
    private static ProjectEntity project3;
    private static ProjectEntity project4;
    private static ProjectImageEntity projectImage;
    private static UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = UserEntity.builder()
                .id(1)
                .email("user@example.com")
                .name("User Name")
                .password("password")
                .build();

        project1 = ProjectEntity.builder()
                .id(1)
                .name("Project One")
                .description("Description of project one")
                .location("Location One")
                .type("Type One")
                .dateCreated(new Date())
                .user(user)
                .fundingGoal(1000f)
                .moneyRaised(100f)
                .build();

        project2 = ProjectEntity.builder()
                .id(2)
                .name("Project Two")
                .description("Description of project two")
                .location("Location Two")
                .type("Type Two")
                .dateCreated(new Date())
                .user(user)
                .fundingGoal(2000f)
                .moneyRaised(500f)
                .build();

        project3 = ProjectEntity.builder()
                .id(3)
                .name("Project Three")
                .description("Description of project three")
                .location("Location Three")
                .type("Type 3")
                .dateCreated(new Date())
                .user(user)
                .fundingGoal(1000f)
                .moneyRaised(500f) // Within percentage funded range
                .build();

        project4 = ProjectEntity.builder()
                .id(4)
                .name("Project Four")
                .description("Description of project four")
                .location("Location Four")
                .type("Type 4")
                .dateCreated(new Date())
                .user(user)
                .fundingGoal(1000f)
                .moneyRaised(200f) // Within percentage funded range
                .build();

        projectImage = ProjectImageEntity.builder()
                .id(1)
                .project(project1)
                .imageOrder(1)
                .imageUrl("imageUrl")
                .build();
    }

    @Test
    @DisplayName("Should get all projects")
    void get_all_projects() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(List.of(project1, project2));

        // Act
        List<OutputDTOProject> projects = projectService.getAllProjects();

        // Assert
        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should add project and return output DTO")
    void add_project() {
        // Arrange
        InputDTOProject inputProject = new InputDTOProject();
        inputProject.setName("New Project");
        inputProject.setDescription("New project description");
        inputProject.setLocation("New Location");
        inputProject.setType("New Type");
        inputProject.setFundingGoal(3000f);

        ProjectEntity savedProject = ProjectEntity.builder()
                .id(3)
                .name("New Project")
                .description("New project description")
                .location("New Location")
                .type("New Type")
                .dateCreated(new Date())
                .user(user)
                .fundingGoal(3000f)
                .moneyRaised(0f)
                .build();

        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(savedProject);

        // Act
        OutputDTOProject result = projectService.createProject(inputProject);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("New Project", result.getName());
        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
    }

    @Test
    @DisplayName("Should get project by ID")
    void get_project_by_id() {
        // Arrange
        when(projectRepository.findById(1)).thenReturn(Optional.of(project1));

        // Act
        OutputDTOProject project = projectService.getProjectById(1);

        // Assert
        assertNotNull(project);
        assertEquals(1, project.getId());
        assertEquals("Project One", project.getName());
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should delete project by ID")
    void delete_project_by_id() {
        // Arrange
        when(projectRepository.findById(1)).thenReturn(Optional.of(project1));
        doNothing().when(projectRepository).deleteById(1);

        // Act
        projectService.deleteProject(1);

        // Assert
        verify(projectRepository, times(1)).deleteById(1);
    }


    @Test
    @DisplayName("Should get all projects close to funding goal")
    void get_close_to_funding_all_projects() {
        when(projectRepository.getCloseToFundingProjects()).thenReturn(List.of(project3));

        // Act
        List<ProjectOnlyCoverLandingPage> projects = projectService.getCloseToFundingAllProjects();

        // Assert
        assertEquals(1, projects.size());
        verify(projectRepository, times(1)).getCloseToFundingProjects();
    }


    @Test
    @DisplayName("Should get all projects close to funding goal")
    void get_new_all_projects() {
        // Arrange
        when(projectRepository.getNewProjects()).thenReturn(List.of(project3));

        // Act
        List<ProjectOnlyCoverLandingPage> projects = projectService.getNewProjects();

        // Assert
        assertEquals(1, projects.size());
        verify(projectRepository, times(1)).getNewProjects();
    }

    @Test
    @DisplayName("Should delete project by ID")
    void delete_project_image_by_id() {
        // Arrange
        when(projectImageRepository.findById(1)).thenReturn(Optional.ofNullable(projectImage));
        doNothing().when(projectImageRepository).deleteById(1);

        // Act
        projectService.deleteProjectImage(1);

        // Assert
        verify(projectImageRepository, times(1)).deleteById(1);
    }



    @Test
    @DisplayName("Should get project image by ID")
    void get_project_image_by_id() {
        // Arrange
        when(projectImageRepository.findById(1)).thenReturn(Optional.ofNullable(projectImage));

        // Act
        OutputDTOProjectImage outProjectImage = projectService.getProjectImageById(1);

        // Assert
        assertNotNull(outProjectImage);
        assertEquals(1, outProjectImage.getId());
        verify(projectImageRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should return all projects owned by a user")
    void get_projects_from_user_id() {

        List<ProjectEntity> projectRepositoryList = new ArrayList<>();
        projectRepositoryList.add(project1);

        // Arrange
        when(projectRepository.findProjectsByUserId(1)).thenReturn(projectRepositoryList);

        // Act
        List<OutputDTOProject> projectList = projectService.getProjectsFromUserId(1);

        // Assert
        assertEquals(1, projectList.size() );
    }

    @Test
    @DisplayName("Should return all project images")
    void get_all_users() {

        List<ProjectImageEntity> projectImageRepositoryList = new ArrayList<>();
        projectImageRepositoryList.add(projectImage);

        // Arrange
        when(projectImageRepository.findAll()).thenReturn(projectImageRepositoryList);

        // Act
        List<OutputDTOProjectImage> projectImagesList = projectService.getAllProjectImages();

        // Assert
        assertEquals(1, projectImagesList.size() );
    }


    @Test
    @DisplayName("Should add projectImage and return output DTO")
    void add_project_image() {
        // Arrange
        InputDTOProjectImage inputImage = InputDTOProjectImage.builder()
                .imageURL("imageURL")
                .imageOrder(1)
                .projectId(1)
                .build();

        ProjectImageEntity savedProjectImage = ProjectImageEntity.builder()
                .id(1)
                .imageUrl("imageURL")
                .imageOrder(1)
                .project(project1)
                .build();

        when(projectImageRepository.save(any(ProjectImageEntity.class))).thenReturn(savedProjectImage);

        // Act
        OutputDTOProjectImage result = projectService.createProjectImage(inputImage);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(projectImageRepository, times(1)).save(any(ProjectImageEntity.class));
    }


    @Test
    @DisplayName("Should get paginated and filtered projects for landing page with the sorting dateCreatedDesc")
    void get_all_projects_for_landing_page_dateCreatedDesc() {
        // Arrange
        Double minPercentageFunded = 10.0;
        Double maxPercentageFunded = 90.0;
        String sortBy = "dateCreatedDesc";
        int page = 0;
        int size = 2;
        String name = "";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated"));

        Page<ProjectEntity> mockPage = new PageImpl<>(List.of(project3, project4), pageable, 2);

        when(projectRepository.getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded, name, pageable ))
                .thenReturn(mockPage);

        when(projectImageRepository.getImagesFromProjectId(3)).thenReturn(List.of("imageURL1"));
        when(projectImageRepository.getImagesFromProjectId(4)).thenReturn(List.of("imageURL2"));

        // Act
        Page<ProjectOnlyCoverLandingPage> result = projectService.getAllProjectsForLandingPage(
                project3.getType(), minPercentageFunded, maxPercentageFunded, sortBy, name,page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        ProjectOnlyCoverLandingPage projectResult1 = result.getContent().get(0);
        assertEquals(3, projectResult1.getId());
        assertEquals("Project Three", projectResult1.getName());
        assertEquals("imageURL1", projectResult1.getImageCover());

        ProjectOnlyCoverLandingPage projectResult2 = result.getContent().get(1);
        assertEquals(4, projectResult2.getId());
        assertEquals("Project Four", projectResult2.getName());
        assertEquals("imageURL2", projectResult2.getImageCover());

        verify(projectRepository, times(1)).getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded,name, pageable);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(3);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(4);
    }


    @Test
    @DisplayName("Should get paginated and filtered projects for landing page with the sorting dateCreatedDesc")
    void get_all_projects_for_landing_page_dateCreatedAsc() {
        // Arrange
        Double minPercentageFunded = 10.0;
        Double maxPercentageFunded = 90.0;
        String sortBy = "dateCreatedAsc";
        int page = 0;
        int size = 2;
        String name = "";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "dateCreated"));

        Page<ProjectEntity> mockPage = new PageImpl<>(List.of(project3, project4), pageable, 2);

        when(projectRepository.getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded, name, pageable))
                .thenReturn(mockPage);

        when(projectImageRepository.getImagesFromProjectId(3)).thenReturn(List.of("imageURL1"));
        when(projectImageRepository.getImagesFromProjectId(4)).thenReturn(List.of("imageURL2"));

        // Act
        Page<ProjectOnlyCoverLandingPage> result = projectService.getAllProjectsForLandingPage(
                project3.getType(), minPercentageFunded, maxPercentageFunded, sortBy, name,page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        ProjectOnlyCoverLandingPage projectResult1 = result.getContent().get(0);
        assertEquals(3, projectResult1.getId());
        assertEquals("Project Three", projectResult1.getName());
        assertEquals("imageURL1", projectResult1.getImageCover());

        ProjectOnlyCoverLandingPage projectResult2 = result.getContent().get(1);
        assertEquals(4, projectResult2.getId());
        assertEquals("Project Four", projectResult2.getName());
        assertEquals("imageURL2", projectResult2.getImageCover());

        verify(projectRepository, times(1)).getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded, name, pageable);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(3);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(4);
    }


    @Test
    @DisplayName("Should get paginated and filtered projects for landing page with the sorting dateCreatedDesc")
    void get_all_projects_for_landing_page_percentageFundedDesc() {
        // Arrange
        Double minPercentageFunded = 10.0;
        Double maxPercentageFunded = 90.0;
        String sortBy = "percentageFundedDesc";
        int page = 0;
        int size = 2;
        String name = "";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "percentageFunded"));

        Page<ProjectEntity> mockPage = new PageImpl<>(List.of(project3, project4), pageable, 2);

        when(projectRepository.getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded, name, pageable))
                .thenReturn(mockPage);

        when(projectImageRepository.getImagesFromProjectId(3)).thenReturn(List.of("imageURL1"));
        when(projectImageRepository.getImagesFromProjectId(4)).thenReturn(List.of("imageURL2"));

        // Act
        Page<ProjectOnlyCoverLandingPage> result = projectService.getAllProjectsForLandingPage(
                project3.getType(), minPercentageFunded, maxPercentageFunded, sortBy, name, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        ProjectOnlyCoverLandingPage projectResult1 = result.getContent().get(0);
        assertEquals(3, projectResult1.getId());
        assertEquals("Project Three", projectResult1.getName());
        assertEquals("imageURL1", projectResult1.getImageCover());

        ProjectOnlyCoverLandingPage projectResult2 = result.getContent().get(1);
        assertEquals(4, projectResult2.getId());
        assertEquals("Project Four", projectResult2.getName());
        assertEquals("imageURL2", projectResult2.getImageCover());

        verify(projectRepository, times(1)).getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded,name, pageable);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(3);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(4);
    }

    @Test
    @DisplayName("Should get nothing with an error in filter")
    void get_all_projects_for_landing_page_default() {
        // Arrange
        Double minPercentageFunded = 10.0;
        Double maxPercentageFunded = 90.0;
        String sortBy = "random";
        int page = 0;
        int size = 2;
        String name = "";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "percentageFunded"));

        Page<ProjectEntity> mockPage = new PageImpl<>(List.of(project3, project4), pageable, 2);

        when(projectRepository.getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded,name, pageable))
                .thenReturn(null);

        when(projectImageRepository.getImagesFromProjectId(3)).thenReturn(List.of("imageURL1"));
        when(projectImageRepository.getImagesFromProjectId(4)).thenReturn(List.of("imageURL2"));
        when(projectRepository.getAllProjectsWithFiltersAndPagination(any(String.class),any(Double.class) , any(Double.class), any(String.class),any(Pageable.class)))
                .thenReturn(mockPage);
        // Act
        Page<ProjectOnlyCoverLandingPage> result = projectService.getAllProjectsForLandingPage(
                project3.getType(), minPercentageFunded, maxPercentageFunded, sortBy, name,page, size);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should get paginated and filtered projects for landing page with percentageFundedAsc sorting")
    void get_all_projects_for_landing_page_percentageFundedAsc() {
        // Arrange
        Double minPercentageFunded = 10.0;
        Double maxPercentageFunded = 90.0;
        String sortBy = "percentageFundedAsc";
        int page = 0;
        int size = 2;
        String name = "";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "percentageFunded"));

        Page<ProjectEntity> mockPage = new PageImpl<>(List.of(project3, project4), pageable, 2);

        when(projectRepository.getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded, name, pageable))
                .thenReturn(mockPage);

        when(projectImageRepository.getImagesFromProjectId(3)).thenReturn(List.of("imageURL1"));
        when(projectImageRepository.getImagesFromProjectId(4)).thenReturn(List.of("imageURL2"));

        // Act
        Page<ProjectOnlyCoverLandingPage> result = projectService.getAllProjectsForLandingPage(
                project3.getType(), minPercentageFunded, maxPercentageFunded, sortBy, name, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        ProjectOnlyCoverLandingPage projectResult1 = result.getContent().get(0);
        assertEquals(3, projectResult1.getId());
        assertEquals("Project Three", projectResult1.getName());
        assertEquals("imageURL1", projectResult1.getImageCover());

        ProjectOnlyCoverLandingPage projectResult2 = result.getContent().get(1);
        assertEquals(4, projectResult2.getId());
        assertEquals("Project Four", projectResult2.getName());
        assertEquals("imageURL2", projectResult2.getImageCover());

        verify(projectRepository, times(1)).getAllProjectsWithFiltersAndPagination(project3.getType(), minPercentageFunded, maxPercentageFunded, name, pageable);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(3);
        verify(projectImageRepository, times(1)).getImagesFromProjectId(4);
    }


    @Test
    void testGetProjectDetailsById_Success() {
        // Arrange
        int projectId = 1;
        ProjectEntity projectEntity = mock(ProjectEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        List<String> images = Arrays.asList("image1.png", "image2.png");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2025);
        calendar.set(Calendar.MONTH, Calendar.JANUARY); // January is 0 in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();


        // Set up mock values for the project entity and user entity
        when(projectRepository.getProjectDetailsById(projectId)).thenReturn(projectEntity);
        when(projectEntity.getId()).thenReturn(projectId);
        when(projectEntity.getName()).thenReturn("Test Project");
        when(projectEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(100);
        when(userEntity.getName()).thenReturn("Owner Name");
        when(userEntity.getProfilePicture()).thenReturn("profilePic.png");
        when(projectEntity.getFundingGoal()).thenReturn(1000.0F);
        when(projectEntity.getMoneyRaised()).thenReturn(500.0F);
        when(projectImageRepository.getImagesFromProjectId(projectId)).thenReturn(images);
        when(projectEntity.getDescription()).thenReturn("This is a description.");
        when(projectEntity.getLocation()).thenReturn("Test Location");
        when(projectEntity.getType()).thenReturn("Crowdfunding");
        when(projectEntity.getDateCreated()).thenReturn(date);

        // Act
        ProjectDetailsDTO result = projectService.getProjectDetailsById(projectId);

        // Assert
        assertNotNull(result, "The project details should not be null.");
        assertEquals(projectId, result.getId(), "The project ID should match.");
        assertEquals("Test Project", result.getName(), "The project name should match.");
        assertEquals(100, result.getUserId(), "The user ID should match.");
        assertEquals("Owner Name", result.getOwnerName(), "The owner name should match.");
        assertEquals("profilePic.png", result.getOwnerProfilePicture(), "The owner profile picture should match.");
        assertEquals(1000.0, result.getFundingGoal(), "The funding goal should match.");
        assertEquals(500.0, result.getMoneyRaised(), "The money raised should match.");
        assertEquals(images, result.getImages(), "The images list should match.");
        assertEquals("This is a description.", result.getDescription(), "The description should match.");
        assertEquals("Test Location", result.getLocation(), "The location should match.");
        assertEquals("Crowdfunding", result.getType(), "The type should match.");
        assertEquals(date, result.getDateCreated(), "The creation date should match.");
    }

    @Test
    void testGetProjectDetailsById_ProjectNotFound() {
        // Arrange
        int projectId = 1;

        // Mock the repository to return null when project is not found
        when(projectRepository.getProjectDetailsById(projectId)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            projectService.getProjectDetailsById(projectId);  // This should throw an exception
        }, "Expected NullPointerException when the project is not found.");
    }

    @Test
    void testGetProjectIdsFromUserID_WithProjects() {
        // Arrange
        int userId = 1;
        List<Integer> projectIds = Arrays.asList(1, 2, 3);

        // Mock the repository to return project IDs for the user
        when(projectRepository.getProjectIdsFromUserID(userId)).thenReturn(projectIds);

        // Act
        List<Integer> result = projectService.getProjectIdsFromUserID(userId);

        // Assert
        assertNotNull(result, "The project IDs list should not be null.");
        assertEquals(3, result.size(), "The project IDs list size should match.");
        assertTrue(result.contains(1), "The project IDs list should contain project ID 1.");
        assertTrue(result.contains(2), "The project IDs list should contain project ID 2.");
        assertTrue(result.contains(3), "The project IDs list should contain project ID 3.");
    }

    @Test
    void testGetProjectIdsFromUserID_NoProjects() {
        // Arrange
        int userId = 1;
        List<Integer> projectIds = Collections.emptyList();

        // Mock the repository to return an empty list
        when(projectRepository.getProjectIdsFromUserID(userId)).thenReturn(projectIds);

        // Act
        List<Integer> result = projectService.getProjectIdsFromUserID(userId);

        // Assert
        assertNotNull(result, "The project IDs list should not be null.");
        assertTrue(result.isEmpty(), "The project IDs list should be empty.");
    }


    @Test
    @DisplayName("Should get bookmarked projects for a user")
    void get_bookmarked_projects() {
        // Arrange
        int userId = 1;
        BookmarkEntity bookmark1 = mock(BookmarkEntity.class);
        project1 = mock(ProjectEntity.class);

        when(projectBookmarksRepository.getBookmarkedProjects(userId)).thenReturn(List.of(bookmark1));
        when(bookmark1.getProject()).thenReturn(project1);
        when(project1.getId()).thenReturn(1);
        when(projectRepository.findById(1)).thenReturn(Optional.of(project1));
        when(projectImageRepository.getImagesFromProjectId(1)).thenReturn(List.of("imageCover"));
        when(project1.getName()).thenReturn("Project 1");
        when(project1.getMoneyRaised()).thenReturn(1000.0F);
        when(project1.getFundingGoal()).thenReturn(2000.0F);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2025);
        calendar.set(Calendar.MONTH, Calendar.JANUARY); // January is 0 in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        when(project1.getDateCreated()).thenReturn(date);
        when(project1.getDescription()).thenReturn("Project description");

        // Act
        List<ProjectOnlyCoverLandingPage> result = projectService.getBookmarkedProjects(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should check if a project is bookmarked by the user")
    void is_project_bookmarked() {
        // Arrange
        int userId = 1;
        int projectId = 1;
        when(projectBookmarksRepository.isProjectBookmarked(userId, projectId)).thenReturn(true);

        // Act
        Boolean result = projectService.isProjectBookmarked(userId, projectId);

        // Assert
        assertTrue(result);
        verify(projectBookmarksRepository, times(1)).isProjectBookmarked(userId, projectId);
    }

    @Test
    @DisplayName("Should remove bookmark for a project")
    void remove_project_bookmark() {
        // Arrange
        int projectId = 1;
        int userId = 1;
        doNothing().when(projectBookmarksRepository).removeProjectBookmark(projectId, userId);

        // Act
        projectService.removeProjectBookmark(projectId, userId);

        // Assert
        verify(projectBookmarksRepository, times(1)).removeProjectBookmark(projectId, userId);
    }

    @Test
    @DisplayName("Should add bookmark for a project")
    void add_project_bookmark() {
        // Arrange
        InputDTOBookmark bookmarkDTO = new InputDTOBookmark(1, 1);
        ProjectEntity project = mock(ProjectEntity.class);
        user = mock(UserEntity.class);
        BookmarkEntity bookmark = mock(BookmarkEntity.class);

        when(userRepository.findById(bookmarkDTO.getUserId())).thenReturn(user);
        when(projectRepository.findById(bookmarkDTO.getProjectId())).thenReturn(Optional.of(project));
        when(projectBookmarksRepository.save(any(BookmarkEntity.class))).thenReturn(bookmark);
        when(bookmark.getBookmarkId()).thenReturn(1);
        when(bookmark.getUser()).thenReturn(user);
        when(bookmark.getProject()).thenReturn(project);
        when(user.getId()).thenReturn(1);
        when(project.getId()).thenReturn(1);

        // Act
        OutputDTOBookmark result = projectService.addProjectBookmark(bookmarkDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(projectBookmarksRepository, times(1)).save(any(BookmarkEntity.class));
    }


    @Test
    void testGetBookmarkedProjects_withNullProjectEntity() {

        BookmarkEntity bookmark = BookmarkEntity.builder()
                .project(project2)  // Set a placeholder project entity (can be mocked further if needed)
                .build();


        // Arrange:
        when(projectBookmarksRepository.getBookmarkedProjects(1)).thenReturn(Arrays.asList(bookmark));
        when(projectRepository.findById(anyInt())).thenReturn(Optional.empty()); // Simulating the null projectEntity scenario

        // Act:
        List<ProjectOnlyCoverLandingPage> projects = projectService.getBookmarkedProjects(1);

        // Assert:
        assertTrue(projects.isEmpty(), "The projects list should be empty if projectEntity is null");

        // Verify interactions
        verify(projectRepository, times(1)).findById(anyInt()); // Ensure findById was called once
        verify(projectBookmarksRepository, times(1)).getBookmarkedProjects(1); // Ensure getBookmarkedProjects was called once
    }

}
