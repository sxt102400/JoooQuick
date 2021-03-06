-- ----------------------------
-- 字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  `dict_id`           int(11)           not null auto_increment    comment '字典主键',
  `dict_name`         varchar(100)      DEFAULT ''                 comment '字典名称',
  `dict_type`         varchar(100)      DEFAULT ''                 comment '字典类型',
  `status`            tinyint(4)        DEFAULT 0      comment '状态：-1删除 0正常，1停用 ',
  `remark`            varchar(500)      DEFAULT NULL ,
  `create_time`       bigint(13)        DEFAULT NULL,
  `create_user`       varchar(50)       DEFAULT NULL,
  `update_time`       bigint(13)        DEFAULT NULL,
  `update_user`       varchar(50)       DEFAULT NULL,
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=1000 comment = '字典类型表';


-- ----------------------------
-- 字典数据项表
-- ----------------------------
drop table if exists sys_dict_item;
create table sys_dict_item
(
  `item_id`          int(11)            not null auto_increment    comment '字典编码',
  `dict_type`        varchar(100)       DEFAULT ''                 comment '字典类型',
  `item_label`       varchar(100)       DEFAULT ''                 comment '字典标签',
  `item_value`       varchar(100)       DEFAULT ''                 comment '字典键值',
  `sort`             tinyint(4)         DEFAULT 0                  comment '字典排序',
  `is_default`       tinyint(4)         DEFAULT 0                  comment '是否默认（1是 0否）',
  `status`           tinyint(4)         DEFAULT 0                  comment '状态：-1删除，0正常，1停用',
  `remark`           varchar(500)       DEFAULT NULL ,
  `create_time`      bigint(13)         DEFAULT NULL,
  `create_user`      varchar(50)        DEFAULT NULL,
  `update_time`      bigint(13)         DEFAULT NULL,
  `update_user`      varchar(50)        DEFAULT NULL,
  primary key (item_id)
) engine=innodb auto_increment=1000 comment = '字典数据项表';


-- ----------------------------
-- 资源文件表
-- ----------------------------
DROP TABLE IF EXISTS `tbl_resource`;
CREATE TABLE `tbl_resource` (
  `res_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键ID',
  `name` varchar(100) NOT NULL COMMENT '文件名',
  `ext` varchar(10) NOT NULL COMMENT '扩展名',
  `md5` varchar(50) NOT NULL COMMENT '唯一标识',
  `file_path` varchar(200) NOT NULL COMMENT '保存路径',
  `file_size` int(11) DEFAULT NULL COMMENT '文件大小',
  `upload_user` int(11) NOT NULL COMMENT '上传用户',
  `upload_time` bigint(13) DEFAULT NULL COMMENT '上传时间',
  `cover` varchar(200) DEFAULT NULL COMMENT '封面',
  `pre_file` varchar(200) DEFAULT NULL COMMENT '预览文件',
  `status` char(1) NOT NULL COMMENT '状态：-1删除，0正常',
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB auto_increment=1000  DEFAULT CHARSET=utf8;


-- ----------------------------
-- 部门表
-- ----------------------------
drop table if exists sys_depart;
create table sys_depart (
  depart_id         int(11)           not null auto_increment    comment   '部门ID',
  parent_id         int(11)           default 0                  comment   '父部门ID',
  parent_ids        varchar(100)       default ''                 comment   '祖级列表',
  depart_code       varchar(100)       default ''                 comment   '部门代码',
  depart_name       varchar(50)       not null                   comment   '部门名称',
  manager           varchar(50)       default null               comment   '负责人',
  mobile            varchar(11)       default null               comment   '联系电话',
  email             varchar(100)       default null               comment   '邮箱',
  sort              tinyint(4)            default 0                   comment   '排序',
  status            tinyint(4)            default 0                   comment   '状态：0正常，1停用 -1删除',
  remark            varchar(500)      default null               comment   '备注',
  create_user       varchar (50)      default null ,
  create_time 	    bigint(13)         default null ,
  update_user       varchar (50)      default null ,
  update_time       bigint(13)         default null ,
  primary key (depart_id)
) engine=innodb auto_increment=1000 comment = '部门表';


-- ----------------------------
-- 用户表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           int(11)         not null auto_increment     comment '用户ID',
  depart_id         int(11)         default null               comment '部门ID',
  username          varchar(50)     not null                   comment '登录账号',
  password          varchar(64)     default ''               comment '登录密码',
  nickname          varchar(50)     default ''               comment '昵称',
  user_type         int(4)       default 1                   comment  '用户类型',
  email             varchar(50)     default ''               comment '用户邮箱',
  mobile            varchar(11)     default ''               comment '手机号码',
  sex               tinyint(4)       default 0                   comment   '性别:0未知,1男,2女',
  avatar            varchar(100)    default ''               comment  '头像',
  login_ip          varchar(50)     default ''                comment  '最后登录IP',
  login_date        bigint(13)       default null              comment  '最后登录时间',
  pwd_update_date   bigint(13)       default null               comment    '密码最后更新时间',
  status            int(4)       default 0                      comment     '帐号状态:-1删除,0正常,1停用',
  remark            varchar(500)     default null            ,
  create_user       varchar (50)     default null       ,
  create_time 	    bigint(13)        default null       ,
  update_user       varchar (50)      default null      ,
  update_time       bigint(13)        default null      ,
  primary key (user_id)
) engine=innodb auto_increment=1000 comment = '用户信息表';

-- ----------------------------
-- 角色表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id           int(11)          not null auto_increment      comment '角色ID',
  role_name         varchar(50)      not null                     comment '角色名称',
  role_code         varchar(50)      not null                     comment '角色权限字符串',
  scope             tinyint(4)           default 0                    comment '数据范围',
  status            tinyint(4)           not null                     comment '状态:0正常,1停用,-1删除',
  remark            varchar(500)      default null              ,
  create_user       varchar (50)      default null   ,
  create_time 	    bigint(13)        default null       ,
  update_user       varchar (50)      default null       ,
  update_time       bigint(13)        default null      ,
  primary key (role_id)
) engine=innodb auto_increment=1000 comment = '角色信息表';


-- ----------------------------
-- 菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           int(11)      not null auto_increment            comment '菜单ID',
  parent_id         int(11)             default 0                   comment '父菜单ID',
  menu_name         varchar(50)         not null                    comment '菜单名称',
  menu_type         tinyint(4)          default null                comment '菜单类型',
  menu_code         varchar(100)        default ''                  comment '菜单权限字符串',
  sort              int(4)              default 0                  comment '显示顺序',
  url               varchar(200)        default '#'                 comment '请求地址',
  target            varchar(20)         default ''                 comment '打开目标',
  status            tinyint(4)          default 0                  comment '菜单状态：0正常，1停用 -1删除',
  remark            varchar(500)        default null                 comment '备注',
  create_user       varchar (50)        default null                comment '创建者',
  create_time 	    bigint(13)          default null                comment '创建时间',
  update_user       varchar (50)        default null                comment '更新者',
  update_time       bigint(13)          default null                comment '更新时间',
  primary key (menu_id)
) engine=innodb auto_increment=1000 comment = '菜单权限表';


-- ----------------------------
-- 资源权限表
-- ----------------------------
drop table if exists sys_permission;
create table sys_permission (
  permission_id    int(11)      not null     auto_increment        comment '权限ID',
  permission_name  varchar(50)              not null               comment '权限名称',
  permission_type  char(1)                  default ''             comment '权限类型',
  permission_code  char(1)                  default 0              comment '权限字符串',
  status            tinyint(4)              default 0              comment '状态：0正常，1停用 -1删除',
  remark            varchar(500)            default ''             comment '备注',
  create_user       varchar (50)            default null           comment '创建者',
  create_time 	    bigint(13)              default null           comment '创建时间',
  update_user       varchar (50)            default null           comment '更新者',
  update_time       bigint(13)              default null           comment '更新时间',
  primary key (permission_id)
) engine=innodb auto_increment=1000 comment = '资源权限表';


-- ----------------------------
-- 用户角色关联表
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  id       int(11)      not null   auto_increment    comment 'ID',
  user_id   int(11) not null comment '用户ID',
  role_id   int(11) not null comment '角色ID',
  primary key(id)
) engine=innodb comment = '用户角色关联表';

-- ----------------------------
-- 角色和菜单关联表
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  id        int(11)      not null   auto_increment    comment 'ID',
  role_id   int(11) not null comment '角色ID',
  menu_id   int(11) not null comment '菜单ID',
  primary key(id)
) engine=innodb comment = '角色和菜单关联表';


-- ----------------------------
-- 系统访问记录
-- ----------------------------
drop table if exists sys_login_log;
create table sys_login_log (
  login_id         int(11)     not null auto_increment    comment '访问ID',
  login_name     varchar(50)    default ''                comment '登录账号',
  ip_address     varchar(50)    default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         tinyint(4)  default 0                    comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     bigint(13)                               comment '访问时间',
  primary key (login_id)
) engine=innodb auto_increment=1000 comment = '系统访问记录';

-- ----------------------------
-- 操作日志记录
-- ----------------------------
drop table if exists sys_operate_log;
create table sys_operate_log (
  operate_id           int(11)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     tinyint(4)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(100)    default ''              comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operate_type      tinyint(4)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  operate_user         varchar(50)     default null                comment '操作人员',
  operate_url          varchar(255)    default null                comment '请求URL',
  operate_ip           varchar(50)     default null                 comment '主机地址',
  operate_location     varchar(255)    default null               comment '操作地点',
  request_param        varchar(2000)   default null              comment '请求参数',
  json_result         varchar(2000)   default null                comment '返回参数',
  status              tinyint(4)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg           varchar(2000)   default null               comment '错误消息',
  operate_time        bigint(13)                                 comment '操作时间',
  primary key (operate_id)
) engine=innodb auto_increment=1000 comment = '操作日志记录';


