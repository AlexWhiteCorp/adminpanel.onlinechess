package com.alexcorp.oc.adminpanel.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ClubMember.ClubMemberId.class)
@Table(name = "CLUB_MEMBER")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.account", joinColumns = @JoinColumn(name = "account_id"))})
public class ClubMember implements Serializable {

    @Id
    @Column(name = "account_id")
    private long accountId;

    @Id
    @Column(name = "club_id")
    private long clubId;

    @OneToOne
    @JoinColumn(name = "account_id", updatable = false, insertable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "club_id", updatable = false, insertable = false)
    private Club club;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public ClubMember() {
    }

    public ClubMember(Account account, Club club, Role role) {
        this.accountId = account.getUserId();
        this.account = account;

        this.clubId = club.getId();
        this.club = club;

        this.role = role;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role{
        KING, PAWN
    }

    public static class ClubMemberId implements Serializable{

        private long accountId;
        private long clubId;

        public ClubMemberId() {
        }

        public ClubMemberId(long accountId, long clubId) {
            this.accountId = accountId;
            this.clubId = clubId;
        }

        public long getAccountId() {
            return accountId;
        }

        public void setAccountId(long accountId) {
            this.accountId = accountId;
        }

        public long getClubId() {
            return clubId;
        }

        public void setClubId(long clubId) {
            this.clubId = clubId;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
}
