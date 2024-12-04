package com.app.Memora.store.repositories;

import com.app.Memora.enums.Status;
import com.app.Memora.store.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}
