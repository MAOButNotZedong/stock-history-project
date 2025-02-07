CREATE TABLE stocks
(
    close  numeric(38, 2),
    high   numeric(38, 2),
    id     INTEGER NOT NULL,
    low    numeric(38, 2),
    open   numeric(38, 2),
    date   DATE,
    ticker_id INTEGER,
    CONSTRAINT stocks_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS stocks_id_seq START WITH 1 INCREMENT BY 30 OWNED BY stocks.id;

CREATE TABLE user_stock
(
    stock_id INTEGER NOT NULL,
    user_id  INTEGER NOT NULL,
    CONSTRAINT user_stock_pkey PRIMARY KEY (stock_id, user_id)
);

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created    TIMESTAMP,
    last_login DATE,
    email      VARCHAR(255),
    password   VARCHAR(255),
    username   VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE tickers
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    ticker_symbol VARCHAR(30),
    CONSTRAINT pk_tickers PRIMARY KEY (id)
);

ALTER TABLE tickers
    ADD CONSTRAINT uc_tickers_name UNIQUE (ticker_symbol);

ALTER TABLE users
    ADD CONSTRAINT users_email_key UNIQUE (email);

ALTER TABLE user_stock
    ADD CONSTRAINT fk5noj9judjf3se5264rns58r1q FOREIGN KEY (stock_id) REFERENCES stocks (id) ON DELETE NO ACTION;

ALTER TABLE user_stock
    ADD CONSTRAINT fkijt9tauq0cqr1068auuyhrkxd FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;