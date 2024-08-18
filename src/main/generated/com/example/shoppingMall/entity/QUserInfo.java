package com.example.shoppingMall.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = -1189125917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final StringPath birthDate = createString("birthDate");

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final NumberPath<Integer> currentPoint = createNumber("currentPoint", Integer.class);

    public final ListPath<Delivery, QDelivery> deliveryList = this.<Delivery, QDelivery>createList("deliveryList", Delivery.class, QDelivery.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath grade = createString("grade");

    public final StringPath isActive = createString("isActive");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath RRN = createString("RRN");

    public final QUsers user;

    public final NumberPath<Long> userInfoCode = createNumber("userInfoCode", Long.class);

    public final StringPath userName = createString("userName");

    public QUserInfo(String variable) {
        this(UserInfo.class, forVariable(variable), INITS);
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserInfo(PathMetadata metadata, PathInits inits) {
        this(UserInfo.class, metadata, inits);
    }

    public QUserInfo(Class<? extends UserInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user")) : null;
    }

}

