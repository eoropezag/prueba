DROP TABLE IF EXISTS vehicle;
 
CREATE TABLE vehicle (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  x DOUBLE NOT NULL,
  y DOUBLE NOT NULL,
  licencePlate VARCHAR(50) NOT NULL,
  range INT NOT NULL,
  batteryLevel INT NOT NULL,
  helmets INT NOT NULL,
  model VARCHAR(100) NOT NULL,
  resourceImageId VARCHAR(150) NOT NULL,
  realTimeData BOOLEAN NOT NULL,
  resourceType VARCHAR(50) NOT NULL,
  companyZoneId INT NOT NULL
  );
 
