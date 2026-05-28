CREATE TABLE transactions (

    id UUID PRIMARY KEY,

    account_id UUID NOT NULL,

    type VARCHAR(30) NOT NULL,

    amount NUMERIC(19,2) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
);