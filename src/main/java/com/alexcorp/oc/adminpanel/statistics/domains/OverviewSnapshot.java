package com.alexcorp.oc.adminpanel.statistics.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SNAPSHOTS")
public class OverviewSnapshot implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private Date date;
    private int playersOnline;
    private float cpuUsage;
    private float ramUsage;

    public OverviewSnapshot(Date date, int playersOnline, float cpuUsage, float ramUsage) {
        this.date = date;
        this.playersOnline = playersOnline;
        this.cpuUsage = cpuUsage;
        this.ramUsage = ramUsage;
    }
}