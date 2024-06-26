create database tripfysub;

use tripfysub;

create table chat(
	messagenum bigint primary key auto_increment,
	room_id varchar(2000),
    roomnum bigint,
    message text,
    em_sysname text,
    userid varchar(300)
);
create table chatroom(
	roomnum bigint primary key auto_increment,
    room_id varchar(2000),
    roomname varchar(1000),
    masterid varchar(300)
);
create table chatroom_user(
	room_id varchar(2000),
    userid varchar(300),
    messagenum bigint
);
drop table chatroom_user;
select * from chatroom_user;
drop table chatroom;
select * from chatroom;
drop table chat;
select * from chat;
insert into chat(room_id, roomnum, message, em_sysname, userid) values('roomid1', '1', '두번째메시지', '없음','apple');
