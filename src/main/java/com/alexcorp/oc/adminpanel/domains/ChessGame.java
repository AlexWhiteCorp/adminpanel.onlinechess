package com.alexcorp.oc.adminpanel.domains;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CHESS_GAME")
public class ChessGame implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @OneToOne(fetch = FetchType.EAGER)
    private Account player_1;
    @OneToOne(fetch = FetchType.EAGER)
    private Account player_2;


    @Enumerated(EnumType.STRING)
    private GameResult gameResult;

    public ChessGame() {
    }

    public ChessGame(GameType gameType, Account player_1, Account player_2, GameResult gameResult) {
        this.date = new Date();
        this.gameType = gameType;
        this.player_1 = player_1;
        this.player_2 = player_2;
        this.gameResult = gameResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Account getPlayer_1() {
        return player_1;
    }

    public void setPlayer_1(Account player_1) {
        this.player_1 = player_1;
    }

    public Account getPlayer_2() {
        return player_2;
    }

    public void setPlayer_2(Account player_2) {
        this.player_2 = player_2;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public enum GameType{
        STANDART;
    }

    public enum GameResult{
        WIN_1, WIN_2, STALE, DRAW;
    }
}
