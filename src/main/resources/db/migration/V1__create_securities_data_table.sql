CREATE TABLE historical (
  id UUID PRIMARY KEY NOT NULL,
  code VARCHAR(3) NOT NULL,
  brand VARCHAR NOT NULL,
  xnumber VARCHAR NOT NULL,
  indexreturn DOUBLE PRECISION NOT NULL,
  nav DOUBLE PRECISION NOT NULL,
  asofdate TIMESTAMP NOT NULL,
  exdividend DOUBLE PRECISION NOT NULL,
  createdat TIMESTAMP NOT NULL
);
CREATE INDEX ON historical (code);
CREATE INDEX ON historical (brand);
CREATE INDEX ON historical (xnumber);
CREATE INDEX ON historical (indexreturn);
CREATE INDEX ON historical (nav);
CREATE INDEX ON historical (asofdate);
CREATE INDEX ON historical (exdividend);
CREATE INDEX ON historical (createdat);
