package com.alexcorp.oc.adminpanel.services;

import com.alexcorp.oc.adminpanel.domains.Account;
import com.alexcorp.oc.adminpanel.domains.Club;
import com.alexcorp.oc.adminpanel.domains.ClubMember;
import com.alexcorp.oc.adminpanel.repositories.ClubMemberRepository;
import com.alexcorp.oc.adminpanel.repositories.ClubRepository;
import com.alexcorp.oc.adminpanel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private UserRepository userRepository;

    public void addPlayer(Club club, Account account, ClubMember.Role role){
        ClubMember clubMember = new ClubMember(account, club, role);
        clubMemberRepository.save(clubMember);

        account.setClubMember(club, role);
        userRepository.save(account.getUser());
    }

    public void saveClub(Club club){
        clubRepository.save(club);
    }

    public Club findClubById(long id){
        return clubRepository.findById(id);
    }
}
