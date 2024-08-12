package com.example.shoppingMall.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPoint is a Querydsl query type for UserPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPoint extends EntityPathBase<UserPoint> {

    private static final long serialVersionUID = 1798299643L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPoint userPoint = new QUserPoint("userPoint");

    public final NumberPath<Integer> chargePoint = createNumber("chargePoint", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> occurDate = createDateTime("occurDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> pointId = createNumber("pointId", Long.class);

    public final StringPath remarks = createString("remarks");

    public final NumberPath<Integer> usePoint = createNumber("usePoint", Integer.class);

    public final QUserInfo userInfo;

    public QUserPoint(String variable) {
        this(UserPoint.class, forVariable(variable), INITS);
    }

    public QUserPoint(Path<? extends UserPoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPoint(PathMetadata metadata, PathInits inits) {
        this(UserPoint.class, metadata, inits);
    }

    public QUserPoint(Class<? extends UserPoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userInfo = inits.isInitialized("userInfo") ? new QUserInfo(forProperty("userInfo"), inits.get("userInfo")) : null;
    }

}

