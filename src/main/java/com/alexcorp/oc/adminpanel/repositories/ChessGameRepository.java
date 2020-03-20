package com.alexcorp.oc.adminpanel.repositories;

import com.alexcorp.oc.adminpanel.domains.ChessGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessGameRepository extends JpaRepository<ChessGame, Long> {

    public ChessGame findById(long id);
}
