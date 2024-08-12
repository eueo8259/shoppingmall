package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.CommentDto;
import com.example.shoppingMall.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.IntStream;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM Comment WHERE board_code = :boardCode", nativeQuery = true)
    Comment findByComment(@Param("boardCode") Long boardCode);
}
