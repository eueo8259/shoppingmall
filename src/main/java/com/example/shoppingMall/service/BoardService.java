package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.BulletinBoardDto;
import com.example.shoppingMall.dto.CommentDto;
import com.example.shoppingMall.entity.*;
import com.example.shoppingMall.repository.BulletinBoardRepository;
import com.example.shoppingMall.repository.CommentRepository;
import com.example.shoppingMall.repository.ProductRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class BoardService {
    @Autowired
    BulletinBoardRepository bulletinBoardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    public Page<BulletinBoard> findAll(Pageable pageable) {
        return bulletinBoardRepository.findAll(pageable);
    }

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int pageNumber, int totalPage) {
        int startNumber = Math.max(pageNumber - (BAR_LENGTH / 2), 0);

        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPage);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }



    @Transactional
    public void upView(Long boardCode) {
        bulletinBoardRepository.upView(boardCode);
    }

    public BulletinBoardDto findOne(Long boardCode) {
        return bulletinBoardRepository.findById(boardCode)
                .map(x-> BulletinBoardDto.fromBoardEntity(x))
                .orElse(null);
    }

    public List<String> boardTitle() {
        return bulletinBoardRepository.boardTitle();
    }

    public Comment findOneComment(Long boardCode) {
         return commentRepository.findByComment(boardCode);

    }

    @Transactional
    public void commentOK(Long boardCode) {
        bulletinBoardRepository.commentOK(boardCode);
    }

    public void insert(BulletinBoardDto bulletinBoardDto, String user) {
        UserInfo userInfo = userInfoRepository.findByUserId(user);
        Product product = productRepository.findByProductCode(bulletinBoardDto.getProductCode().getProductCode());
        BulletinBoard bulletinBoard = new BulletinBoard();
        bulletinBoard.setBoardTitle(bulletinBoardDto.getBoardTitle());
        bulletinBoard.setContent(bulletinBoardDto.getContent());
        bulletinBoard.setViews(0);
        bulletinBoard.setHasComment(false);
        bulletinBoard.setUserInfo(userInfo);
        bulletinBoard.setProduct(product);
        bulletinBoardRepository.save(bulletinBoard);
    }

    public void AddComment(CommentDto comment, String admin) {
        UserInfo userInfo = userInfoRepository.findByUserId(admin);
        BulletinBoard bulletinBoard = bulletinBoardRepository.findByBoard(comment.getBoardCode().getBoardCode());

        Comment comments = new Comment();
        comments.setCommentText(comment.getCommentText());
        comments.setBoard(bulletinBoard);
        comments.setUserInfo(userInfo);
        comments.setCreationDate(LocalDate.now());
        commentRepository.save(comments);
    }
}
