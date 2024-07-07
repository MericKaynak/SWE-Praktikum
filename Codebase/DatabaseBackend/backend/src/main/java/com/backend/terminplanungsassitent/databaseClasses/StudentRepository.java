package com.backend.terminplanungsassitent.databaseClasses;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s.id FROM Student s WHERE s.email = :email")
    Optional<Integer> findStudentIdByEmail(@Param("email") String email);

}
