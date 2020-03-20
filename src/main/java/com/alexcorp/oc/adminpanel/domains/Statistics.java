package com.alexcorp.oc.adminpanel.domains;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STATISTICS")
public class Statistics implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long accountId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;

    private int games;
    private int victories;
    private int defeats;
    private int stalemates;
    private int draws;
    private int maxRate;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Award> awards = new HashSet<>();

    public Statistics() {
    }

    public Statistics(Account account) {
        this.account = account;
    }

    public Statistics(Account account, int games, int victories, int defeats, int stalemates, int draws, int maxRate) {
        this.account = account;
        this.games = games;
        this.victories = victories;
        this.defeats = defeats;
        this.stalemates = stalemates;
        this.draws = draws;
        this.maxRate = maxRate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getStalemates() {
        return stalemates;
    }

    public void setStalemates(int stalemates) {
        this.stalemates = stalemates;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(int maxRate) {
        this.maxRate = maxRate;
    }
}
