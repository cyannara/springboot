insert into tbl_board values ( seq_board.nextval, 'title', 'content',  'writer',  sysdate,  sysdate );
insert into tbl_board values ( seq_board.nextval, 'title1', 'content',  'writer',  sysdate,  sysdate );
insert into tbl_board values ( seq_board.nextval, 'title2', 'content',  'writer',  sysdate,  sysdate );
insert into tbl_board values ( seq_board.nextval, 'title3', 'content',  'writer',  sysdate,  sysdate );

create table tbl_reply (seq_reply.nextval, 1, 'reply', 'replyer',  sysdate,  sysdate);