package com.fintrack.fintrack.repository;


import com.fintrack.fintrack.entity.Category;
import com.fintrack.fintrack.entity.Transaction;
import com.fintrack.fintrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

   List<Transaction> findAllByUser(User user);
   Optional<Transaction> findByIdAndUser(Long id ,User user);

    List<Transaction> findAllByUserAndCategory(User user, Category category);
}
