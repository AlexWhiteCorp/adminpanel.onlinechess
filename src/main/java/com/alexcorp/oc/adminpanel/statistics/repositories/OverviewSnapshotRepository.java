package com.alexcorp.oc.adminpanel.statistics.repositories;

import com.alexcorp.oc.adminpanel.statistics.domains.OverviewSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OverviewSnapshotRepository extends JpaRepository<OverviewSnapshot, Long> {

    List<OverviewSnapshot> findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(Date startRangeDate, Date endRangeDate);
}
