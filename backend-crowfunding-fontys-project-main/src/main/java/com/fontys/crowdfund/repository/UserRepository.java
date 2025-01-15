package com.fontys.crowdfund.repository;

import com.fontys.crowdfund.persistence.entity.UserEntity;
import com.fontys.crowdfund.persistence.specialdto.UserProjectDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

        // Check if a user exists by email
        boolean existsByEmail(String email);

        // Check if a user exists by ID
        boolean existsById(int userId);

        // Find a user by ID
        UserEntity findById(int userId);

        // Find a user by email
        @Query("SELECT u " +
                "FROM UserEntity u " +
                "WHERE u.email = :userEmail")
        UserEntity findByEmail(@Param("userEmail") String userEmail);

        // Delete a user by ID
        @Modifying
        @Query("DELETE " +
                "FROM UserEntity u " +
                "WHERE u.id = :userId")
        void deleteById(@Param("userId") int userId);


        @Query("SELECT new com.fontys.crowdfund.persistence.specialdto.UserProjectDTO(p.user.name, p.user.profilePicture) " +
                "FROM ProjectEntity p " +
                "WHERE p.id = :projectId")
        UserProjectDTO getUserDataForProject(@Param("projectId") int id);


        @Query("SELECT u.id " +
                "FROM UserEntity u " +
                "WHERE u.email = :userEmail")
        Integer getUserIdFromEmail(@Param("userEmail") String email);


        @Modifying
        @Transactional
        @Query("UPDATE UserEntity u " +
                "SET u.profilePicture = :newPicture " +
                "WHERE u.id = :id")
        int updateProfilePicture(@Param("newPicture") String newPicture, @Param("id") int id);


        @Query("SELECT u.profilePicture " +
                "FROM UserEntity u " +
                "WHERE u.id = :userId")
        String getProfilePicture(@Param("userId")int id);
}