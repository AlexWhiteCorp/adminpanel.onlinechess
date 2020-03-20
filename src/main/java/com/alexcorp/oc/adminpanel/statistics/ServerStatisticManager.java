package com.alexcorp.oc.adminpanel.statistics;

import com.alexcorp.oc.adminpanel.statistics.domains.OverviewSnapshot;
import com.alexcorp.oc.adminpanel.statistics.repositories.OverviewSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ServerStatisticManager {

    private static final Logger log = LoggerFactory.getLogger(ServerStatisticManager.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private SystemUsageInfo systemUsageInfo;

    @Autowired
    private OverviewSnapshotRepository overviewSnapshotRepository;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void saveServerStateStatisticsSnapshot() {
        OverviewSnapshot overviewSnapshot = new OverviewSnapshot(new Date(), 15, (float)systemUsageInfo.getCPUusage(), (float)systemUsageInfo.getRAMusage());
        overviewSnapshotRepository.save(overviewSnapshot);
    }
}