package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.BulletinBoard;
import com.example.shoppingMall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {

    @Modifying
    @Query(value = "UPDATE bulletin_board SET views = views + 1 WHERE board_code = :boardCode ", nativeQuery = true)
    void upView(@Param("boardCode") Long boardCode);
}
