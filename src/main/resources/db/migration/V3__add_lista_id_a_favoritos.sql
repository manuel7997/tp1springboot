-- Nullable a propósito: ya puede haber favoritos creados antes de que existieran las listas.
-- V4 los reasigna y recién ahí exige NOT NULL.
ALTER TABLE favoritos
    ADD COLUMN lista_id BIGINT,
    ADD CONSTRAINT fk_favoritos_lista FOREIGN KEY (lista_id) REFERENCES listas (id);

CREATE INDEX idx_favoritos_lista_id ON favoritos (lista_id);
