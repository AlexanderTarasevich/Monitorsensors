CREATE TABLE sensor (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(30) NOT NULL,
                        model VARCHAR(15) NOT NULL,
                        range_from INTEGER NOT NULL,
                        range_to INTEGER NOT NULL,
                        type VARCHAR(20) NOT NULL,
                        unit VARCHAR(20) NOT NULL,
                        location VARCHAR(40),
                        description VARCHAR(200)
);
