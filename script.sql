CREATE UNLOGGED TABLE payment (
                         correlation_id UUID PRIMARY KEY,
                         amount NUMERIC(10, 2) NOT NULL,
                         status integer NOT NULL,
                         processor integer,
                         requested_at TIMESTAMP WITH TIME ZONE NOT NULL
);


ALTER TABLE payment SET (fillfactor = 70);
