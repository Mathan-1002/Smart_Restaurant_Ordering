package com.smartrestaurant.repository;

import com.smartrestaurant.model.BillHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillHistoryRepository extends JpaRepository<BillHistory, Long> {

    /** All bills for a specific table (most-recent-first). */
    List<BillHistory> findByTableNoOrderByBilledAtDesc(int tableNo);

    /** All bills for a specific customer phone number. */
    List<BillHistory> findByPhoneOrderByBilledAtDesc(String phone);

    /** All bills handled by a supervisor. */
    List<BillHistory> findBySupervisorOrderByBilledAtDesc(String supervisor);
}
