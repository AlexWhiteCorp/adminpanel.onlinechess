package com.alexcorp.oc.adminpanel.domains;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLUBS")
public class Club implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String name;
    private int iconId;
    private int rating;
    private String description;
    private int maxMembers;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @JoinTable( name = "CLUB_MEMBER",
                joinColumns = @JoinColumn(
                        name = "club_id",
                        referencedColumnName = "id"
                ),
                inverseJoinColumns = @JoinColumn(
                        name = "account_id",
                        referencedColumnName = "account_id"
                ))
    @OneToMany(fetch = FetchType.EAGER/*, cascade = CascadeType.ALL*/)
    private List<Club.ClubMember> clubMembers = new ArrayList<>();

    public Club() {
    }

    public Club(String name, String description, int maxMembers) {
        this.name = name;
        this.iconId = 1;
        this.description = description;
        this.maxMembers = maxMembers;
        this.entryType = EntryType.FREE;
    }

    public Club(String name, int iconId, String description, int maxMembers, EntryType entryType) {
        this.name = name;
        this.iconId = iconId;
        this.description = description;
        this.maxMembers = maxMembers;
        this.entryType = entryType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public List<ClubMember> getClubMembers() {
        return clubMembers;
    }

    public void setClubMembers(List<ClubMember> clubMembers) {
        this.clubMembers = clubMembers;
    }

    @Entity
    @Table(name = "CLUB_MEMBER")
    @Transactional
    @AssociationOverrides({
            @AssociationOverride(name = "primaryKey.account", joinColumns = @JoinColumn(name = "account_id"))})
    public static class ClubMember implements Serializable {

        @Id
        @Column(name = "account_id")
        private Long accountId;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "account_id", updatable = false, insertable = false)
        private Account account;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        private com.alexcorp.oc.adminpanel.domains.ClubMember.Role role;

        public ClubMember() {
        }

        public ClubMember(Account account, com.alexcorp.oc.adminpanel.domains.ClubMember.Role role) {
            this.accountId = account.getUserId();
            this.account = account;
            this.role = role;
        }

        public Long getAccountId() {
            return accountId;
        }

        public void setAccountId(Long accountId) {
            this.accountId = accountId;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public com.alexcorp.oc.adminpanel.domains.ClubMember.Role getRole() {
            return role;
        }

        public void setRole(com.alexcorp.oc.adminpanel.domains.ClubMember.Role role) {
            this.role = role;
        }
    }

    public enum EntryType{
        FREE, CLOSE, BY_PASSWORD;
    }
}
