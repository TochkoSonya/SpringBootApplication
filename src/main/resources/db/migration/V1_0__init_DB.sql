create table authors (author_id bigint not null, first_name varchar(255), last_name varchar(255), primary key (author_id));
create table book_author (book_id bigint not null, author_id bigint not null);
create table books (book_id bigint not null, description varchar(255), title varchar(255), primary key (book_id));
create table comments (comment_id bigint not null, text varchar(255), book_id bigint, primary key (comment_id));

create sequence hibernate_sequence start with 1 increment by 1;

alter table COMMENTS add constraint books_fk_comments foreign key (book_id) references BOOKS(book_id);
alter table BOOK_AUTHOR add constraint books_fk_book_author foreign key (BOOK_ID) references BOOKS(book_id);
alter table BOOK_AUTHOR add constraint authors_fk_book_author foreign key (AUTHOR_ID) references AUTHORS(author_id);
