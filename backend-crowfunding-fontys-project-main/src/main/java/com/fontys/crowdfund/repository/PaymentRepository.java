package com.fontys.crowdfund.repository;

import com.fontys.crowdfund.persistence.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    // Check if a payment exists by ID
    @Query("SELECT COUNT(p) > 0 " +
            "FROM PaymentEntity p " +
            "WHERE p.id = :paymentId")
    boolean existsById(@Param("paymentId") long paymentId);

    // Find a payment by ID (JpaRepository already provides findById, but this is an example if custom logic is needed)
    @Query("SELECT p " +
            "FROM PaymentEntity p " +
            "WHERE p.id = :paymentId")
    PaymentEntity findPaymentById(@Param("paymentId") long paymentId);

    // Get payments by project ID, returning PaymentEntity directly
    @Query("SELECT p " +
            "FROM PaymentEntity p " +
            "WHERE p.project.id = :projectId " +
            "ORDER BY p.paymentDate DESC")
    List<PaymentEntity> getPaymentsByProjectId(@Param("projectId") int projectId);

    @Query("SELECT p " +
            "FROM PaymentEntity p " +
            "WHERE p.user.id = :userId AND MONTH(p.paymentDate) = MONTH(CURRENT_DATE) " +
            "AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE) " +
            "ORDER BY p.paymentDate DESC")
    Page<PaymentEntity> getPaymentsForThisMonthByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT p " +
            "FROM PaymentEntity p " +
            "WHERE p.user.id = :userId AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE) " +
            "ORDER BY p.paymentDate DESC")
    Page<PaymentEntity> getPaymentsForThisYearByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT p " +
            "FROM PaymentEntity p " +
            "WHERE p.user.id = :userId " +
            "ORDER BY p.paymentDate DESC")
    Page<PaymentEntity> getPaymentsForAllTimeByUser(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT i.imageUrl " +
            "FROM ProjectImageEntity i " +
            "WHERE i.project.id = :projectId AND i.imageOrder = 1")
    String getImageCover(@Param("projectId") int id);


    @Query("SELECT COALESCE(SUM(p.amount), 0) " +
            "FROM PaymentEntity p " +
            "WHERE p.user.id = :userId " +
            "AND (:time IS NULL OR " +
            "( (:time = 'month' AND MONTH(p.paymentDate) = MONTH(CURRENT_DATE) AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE)) " +
            "OR (:time = 'year' AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE)) ))")
    Integer getTotalPaymentsByUserId(@Param("userId") int userId, @Param("time") String time);



}
