package com.fintrack.fintrack.repository;

import com.fintrack.fintrack.entity.Category;
import com.fintrack.fintrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    boolean existsByNameAndUser(String name,User user);
    Optional<Category> findByIdAndUser(Long id, User user);
    List<Category> findByUserOrUserIsNull(User user);
    boolean existsByNameAndUserIsNull(String name);
    Optional<Category> findByIdAndUserIsNull(long id);
    Optional<Category> findByIdAndUserOrIdAndUserIsNull(long id1,User user,Long id2);
}
