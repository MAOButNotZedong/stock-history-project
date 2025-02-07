ALTER TABLE stocks
    ADD CONSTRAINT stocks_ticker_id_and_date UNIQUE (ticker_id, date)