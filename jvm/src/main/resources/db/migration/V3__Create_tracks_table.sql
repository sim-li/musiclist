CREATE TABLE "tracks" (
  "id"       BIGSERIAL PRIMARY KEY,
  "url" VARCHAR NOT NULL,
  "title" VARCHAR NOT NULL,
  "description" VARCHAR NOT NULL
);