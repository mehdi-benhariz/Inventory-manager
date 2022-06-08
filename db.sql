CREATE TABLE products (
  ref varchar(12) NOT NULL,
  name varchar(50) NOT NULL,
  quantity int(11) NOT NULL DEFAULT '0',
  price float NOT NULL DEFAULT '0',
  TVA TINYINT NOT NULL,
  marge TINYINT NOT NULL,
  PRIMARY KEY (ref)
);

CREATE TABLE receptions (
  ref varchar(12) NOT NULL,
  date date NOT NULL,
  PRIMARY KEY (ref)
);

CREATE TABLE receptions_products (
  ref_product varchar(12) NOT NULL,
  ref_reception varchar(12) NOT NULL,
  quantity int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (ref_product, ref_reception),
  FOREIGN KEY(ref_product) REFERENCES products(ref),
  FOREIGN KEY(ref_reception) REFERENCES receptions(ref)
  ON UPDATE CASCADE
  ON DELETE CASCADE
);

CREATE TABLE ventes (
  ref varchar(12) NOT NULL,
  date date NOT NULL,
  PRIMARY KEY (ref)
);

CREATE TABLE ventes_products (
  ref_product varchar(12),
  ref_vente varchar(12) ,
  quantity int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (ref_product, ref_vente),
  FOREIGN KEY(ref_product) REFERENCES products(ref),
  FOREIGN KEY(ref_vente) REFERENCES ventes(ref)
  ON UPDATE CASCADE
  ON DELETE CASCADE
);

//insert into products values ('1', 'prod1', '10', '10', '10', '8');