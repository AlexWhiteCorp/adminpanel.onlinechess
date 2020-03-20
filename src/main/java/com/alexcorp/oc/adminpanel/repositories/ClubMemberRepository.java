package com.alexcorp.oc.adminpanel.repositories;

import com.alexcorp.oc.adminpanel.domains.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

}
