# stompchat

# Stomp를 이용한 채팅 시스템 구현

# 사용된 sql table
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
