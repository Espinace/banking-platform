CREATE TABLE transactions (

    id UUID PRIMARY KEY,

    sender_account_id UUID,

    receiver_account_id UUID,

    type VARCHAR(30) NOT NULL,

    amount NUMERIC(19,2) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_transaction_sender
        FOREIGN KEY (sender_account_id)
        REFERENCES accounts(id),

    CONSTRAINT fk_transaction_receiver
        FOREIGN KEY (receiver_account_id)
        REFERENCES accounts(id)
);