create table content_article_category
(
    id          bigint unsigned auto_increment comment '文章ID'
        primary key,
    hash        varchar(255) not null comment '文章编号',
    parent_hash varchar(255) not null comment '父级分类哈希',
    title       varchar(64)  not null comment '文章标题',
    thumb       varchar(512) null comment '缩略图',
    create_by   varchar(64)  not null comment '创建者',
    create_time timestamp    not null comment '创建时间',
    update_by   bigint       null comment '修改者',
    update_time timestamp    null comment '修改时间',
    constraint content_article_category_hash_uindex
        unique (hash) comment '哈希索引'
)
    comment '平台文章分类';