DROP TABLE IF EXISTS `t_example`;
CREATE TABLE `t_example`
(
    id         INT(10) PRIMARY KEY AUTO_INCREMENT
        COMMENT '主键',
    name       VARCHAR(20),
    name2       VARCHAR(20),
    created_at TIMESTAMP NOT NULL
        COMMENT '创建时间'
);

