package com.example.shoppingMall.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBulletinBoard is a Querydsl query type for BulletinBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBulletinBoard extends EntityPathBase<BulletinBoard> {

    private static final long serialVersionUID = 1923205621L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBulletinBoard bulletinBoard = new QBulletinBoard("bulletinBoard");

    public final NumberPath<Long> boardCode = createNumber("boardCode", Long.class);

    public final StringPath content = createString("content");

    public final BooleanPath hasComment = createBoolean("hasComment");

    public final QProduct product;

    public final QUserInfo userInfo;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QBulletinBoard(String variable) {
        this(BulletinBoard.class, forVariable(variable), INITS);
    }

    public QBulletinBoard(Path<? extends BulletinBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBulletinBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBulletinBoard(PathMetadata metadata, PathInits inits) {
        this(BulletinBoard.class, metadata, inits);
    }

    public QBulletinBoard(Class<? extends BulletinBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.userInfo = inits.isInitialized("userInfo") ? new QUserInfo(forProperty("userInfo"), inits.get("userInfo")) : null;
    }

}

