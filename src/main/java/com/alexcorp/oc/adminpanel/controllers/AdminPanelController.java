package com.alexcorp.oc.adminpanel.controllers;

import com.alexcorp.oc.adminpanel.controllers.rest.ValueCard;
import com.alexcorp.oc.adminpanel.domains.User;
import com.alexcorp.oc.adminpanel.statistics.SystemUsageInfo;
import com.alexcorp.oc.adminpanel.statistics.domains.OverviewSnapshot;
import com.alexcorp.oc.adminpanel.statistics.repositories.OverviewSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminPanelController {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("DD MM YYYY HH:mm");
    private final static long MILiSEC_IN_HOUR = 60 * 60 * 1000;
    private final static long SECONDS_IN_DAY = 24 * 60 * 60 * 1000;
    private final static long SECONDS_IN_WEEK = 7 * SECONDS_IN_DAY;

    private final static int TODAY = 0;
    private final static int YESTERDAY = 1;
    private final static int LAST_WEEK = 7;
    private final static int LAST_4_WEEK = 28;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private SystemUsageInfo systemUsageInfo;
    @Autowired
    private OverviewSnapshotRepository overviewSnapshotRepository;

    @GetMapping("/")
    String main(HttpSession session, Model model){
        model.addAttribute("isDevMode", profile.equals("dev"));
        model.addAttribute("nickname", session.getAttribute("nickname"));
        model.addAttribute("role", session.getAttribute("role"));
        return "adminpanel";
    }

    @GetMapping("/adminpanel/**")
    String adminpanel(HttpSession session, Model model){
        model.addAttribute("isDevMode", profile.equals("dev"));
        model.addAttribute("nickname", session.getAttribute("nickname"));
        model.addAttribute("role", session.getAttribute("role"));
        return "adminpanel";
    }

    public AdminPanelController() {

    }

    @GetMapping("/rest/overview")
    public @ResponseBody Object overview(){
        Map<String, Map<String, ValueCard>> json = new HashMap<>();

        Map<String, ValueCard> valueCards = new HashMap<>();

        valueCards.put("1", new ValueCard("Players Online", "1", 15, 4.5f));
        valueCards.put("2", new ValueCard("New Accounts", "3", 75, 72.5f));

        double cpLoad = systemUsageInfo.getCPUusage();
        valueCards.put("3", new ValueCard("CPU Usage", cpLoad + "%", cpLoad, 0.0f));

        double ramLoad = systemUsageInfo.getRAMusage();
        valueCards.put("4", new ValueCard("Memory Usage", ramLoad + "%", ramLoad, 7.28f));

        json.put("value-cards", valueCards);

        return json;
    }

    @GetMapping("/rest/snapshot")
    public @ResponseBody Object snapshot(@RequestParam int range){ //range = 0 || 1 || 7 || 28
        long timeNow = System.currentTimeMillis();
        Date rangeDateStart;
        Date rangeDateEnd;
        List<OverviewSnapshot> snapshots = null;

        switch(range){
            case TODAY: {
                rangeDateStart = new Date(timeNow - timeNow % SECONDS_IN_DAY - 2 * MILiSEC_IN_HOUR);
                rangeDateEnd = new Date(rangeDateStart.getTime() + SECONDS_IN_DAY );

                snapshots = overviewSnapshotRepository
                        .findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(rangeDateStart, rangeDateEnd);
                break;
            }
            case YESTERDAY: {
                rangeDateStart = new Date(timeNow - timeNow % SECONDS_IN_DAY - 1 * SECONDS_IN_DAY - 2 * MILiSEC_IN_HOUR);
                rangeDateEnd = new Date(rangeDateStart.getTime() + SECONDS_IN_DAY);
                snapshots = overviewSnapshotRepository
                        .findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(rangeDateStart, rangeDateEnd);
                break;
            }
            case LAST_WEEK: {
                rangeDateStart = new Date(timeNow - timeNow % SECONDS_IN_DAY - 7 * SECONDS_IN_DAY - 2 * MILiSEC_IN_HOUR);
                rangeDateEnd = new Date(rangeDateStart.getTime() + 7 * SECONDS_IN_DAY);
                snapshots = overviewSnapshotRepository
                        .findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(rangeDateStart, rangeDateEnd);
                break;
            }
            case LAST_4_WEEK: {
                rangeDateStart = new Date(timeNow - timeNow % SECONDS_IN_DAY - 28 * SECONDS_IN_DAY - 2 * MILiSEC_IN_HOUR);
                rangeDateEnd = new Date(rangeDateStart.getTime() + 28 * SECONDS_IN_DAY);
                snapshots = overviewSnapshotRepository
                        .findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(rangeDateStart, rangeDateEnd);
                break;
            }
            default:{
                System.out.println("Wrong time range!");
            }
        }

        Map<Integer, OverviewSnapshot> json = new HashMap<>();

        int count = 0;
        long lastDate = 0;

        if(snapshots != null){
            for(OverviewSnapshot snapshot : snapshots){
                if(snapshot.getDate().getTime() != lastDate){
                    json.put(count, snapshot);
                    lastDate = snapshot.getDate().getTime();
                    count++;
                }
            }
            System.out.println(snapshots.size() + " : " + json.size());
        }
        System.out.println(range);
        return json;
    }
}