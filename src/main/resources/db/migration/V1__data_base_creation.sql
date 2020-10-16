create table users (
    id         bigserial   not null
        constraint user_pk
            primary key,
    username   varchar(32) not null,
    password   text        not null,
    email      varchar(32) not null,
    enabled    bool not null default false
);

create unique index user_email_uindex
    on users (email);

create unique index user_username_uindex
    on users (username);

create table roles (
    id   bigserial   not null
        constraint role_pk
            primary key,
    role varchar(32) not null
);

create unique index role_role_uindex
    on roles (role);

create table user_role (
    user_id bigserial not null
        constraint user_role_users_id_fk
            references users,
    role_id bigserial not null
        constraint user_role_roles_id_fk
            references roles
);

create table posts (
    id          bigserial not null
        constraint posts_pk
            primary key,
    title       text      not null,
    body        text      not null,
    created_at timestamp not null,
    user_id     bigserial not null
        constraint posts_users_id_fk
            references users
);

create table comments (
    id          bigserial not null
        constraint comments_pk
            primary key,
    body        text      not null,
    created_at timestamp not null,
    post_id     bigserial not null
        constraint comments_posts_id_fk
            references posts,
    user_id     bigserial not null
        constraint comments_users_id_fk
            references users
);

create table confirmation_token (
    id          bigserial not null
            constraint confirmation_token_pk
                primary key,
    token       text not null,
    created_at  date not null,
    user_id     bigserial not null
            constraint comments_users_id_fk
                references users
);
