create table todo (
  taskid int not null comment 'タスクID'
  , taskname varchar not null comment 'タスク名'
  , status int default 0 not null comment '進行状況'
  , priority int default 0 not null comment '優先度'
  , timelimit date comment '期限'
  , created datetime comment '作成日時'
  , updated datetime comment '更新日時'
  , primary key (taskid)
);

create table sequence (
  seqname varchar not null comment 'シーケンス名'
  , current_val int default 0 not null comment 'シーケンスの現在値'
  , primary key (seqname)
);

