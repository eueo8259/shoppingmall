package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.BulletinBoardDto;
import com.example.shoppingMall.entity.BulletinBoard;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {

    @Modifying
    @Query(value = "UPDATE bulletin_board SET views = views + 1 WHERE board_code = :boardCode", nativeQuery = true)
    void upView(@Param("boardCode") Long boardCode);

    @Query(value = "SELECT board_title FROM bulletin_board", nativeQuery = true)
    List<String> boardTitle();

    @Modifying
    @Query(value = "UPDATE bulletin_board SET has_comment = TRUE WHERE board_code = :boardCode", nativeQuery = true)
    void commentOK(@Param("boardCode") Long boardCode);


    @Query(value = "SELECT * FROM bulletin_board where board_code = :boardCode", nativeQuery = true)
    BulletinBoard findByBoard(@Param("boardCode") Long boardCode);
}
