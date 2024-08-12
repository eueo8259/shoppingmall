package com.example.shoppingMall.repository.specification;

import com.example.shoppingMall.entity.UserInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserSpecification {
    public static Specification<UserInfo> hasIsActive(String isActive) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), isActive);
    }
    public static Specification<UserInfo> hasUserRole(String userRole) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("userRole"), userRole);
    }
    public static Specification<UserInfo> hasGrade(String grade) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("grade"), grade);
    }
    public static Specification<UserInfo> hasSearchKeyword(String keyword) {
        return (Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String likePattern = "%" + keyword + "%";

            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("userName"), likePattern),
                    criteriaBuilder.like(root.get("user").get("id"), likePattern),
                    criteriaBuilder.like(root.get("RRN"), likePattern),
                    criteriaBuilder.like(root.get("email"), likePattern),
                    criteriaBuilder.like(root.get("phoneNumber"), likePattern),
                    criteriaBuilder.like(root.get("birthDate"), likePattern)
            );

            // 날짜 필드에 대한 조건 추가
            try {
                LocalDate date = LocalDate.parse(keyword);  // 키워드를 날짜로 변환
                Predicate datePredicate = criteriaBuilder.equal(root.get("createdDate"), date);
                predicate = criteriaBuilder.or(predicate, datePredicate);
            } catch (DateTimeParseException e) {
                // 키워드가 날짜 형식이 아닐 때는 무시
            }

            return predicate;
        };
    }
}
