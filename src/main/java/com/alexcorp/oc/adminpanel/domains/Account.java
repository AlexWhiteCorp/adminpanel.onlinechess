package com.alexcorp.oc.adminpanel.domains;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    private int goblets;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
    private Statistics statistics;

    @OneToOne(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinColumn(name = "club_id")
    private ClubMember clubMember;

    public Account() {
    }

    public Account(User user) {
        this.user = user;
        this.userId = user.getId();
        this.statistics = new Statistics(this);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public int getGoblets() {
        return goblets;
    }

    public void setGoblets(int goblets) {
        this.goblets = goblets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ClubMember getClubMember() {
        return clubMember;
    }

    public void setClubMember(Club club, com.alexcorp.oc.adminpanel.domains.ClubMember.Role role) {
        this.clubMember = new ClubMember(club, role);
    }

    @Entity
    @Table(name = "CLUB_MEMBER")
    @AssociationOverrides({
            @AssociationOverride(name = "primaryKey.club", joinColumns = @JoinColumn(name = "club_id"))})
    public static class ClubMember{

        @Id
        @Column(name = "club_id")
        private Long clubId;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "club_id", updatable = false, insertable = false)
        private Club club;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        private com.alexcorp.oc.adminpanel.domains.ClubMember.Role role;

        public ClubMember() {
        }

        public ClubMember(Club club, com.alexcorp.oc.adminpanel.domains.ClubMember.Role role) {
            this.clubId = club.getId();
            this.club = club;
            this.role = role;
        }

        public Long getClubId() {
            return clubId;
        }

        public void setClubId(Long clubId) {
            this.clubId = clubId;
        }

        public Club getClub() {
            return club;
        }

        public void setClub(Club club) {
            this.club = club;
        }

        public com.alexcorp.oc.adminpanel.domains.ClubMember.Role getRole() {
            return role;
        }

        public void setRole(com.alexcorp.oc.adminpanel.domains.ClubMember.Role role) {
            this.role = role;
        }
    }
}
