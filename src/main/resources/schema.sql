DROP SCHEMA IF EXISTS Touristguide;
CREATE SCHEMA IF NOT EXISTS Touristguide;
USE Touristguide;

CREATE TABLE IF NOT EXISTS Cities (
    Name VARCHAR(128) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Tags (
	Name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS TouristAttractions (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    CityName VARCHAR(128),
    Name VARCHAR(128),
    Description TEXT,
    FOREIGN KEY (CityName) REFERENCES Cities(Name) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS AttractionTags (
	TagName VARCHAR(64),
    AttractionID INT,
    PRIMARY KEY (TagName, AttractionID),
    FOREIGN KEY (TagName) REFERENCES Tags(Name) ON DELETE CASCADE,
    FOREIGN KEY (AttractionID) REFERENCES TouristAttractions(ID) ON DELETE CASCADE
);

CREATE VIEW TouristAttractionView AS
SELECT ta.Name AS name,
       ta.Description AS description,
       c.Name AS city,
       GROUP_CONCAT(t.Name SEPARATOR ', ') AS tags
FROM TouristAttractions ta
JOIN Cities c ON ta.CityName = c.Name
LEFT JOIN 
    AttractionTags at ON ta.ID = at.AttractionID
LEFT JOIN Tags t ON at.TagName = t.Name
GROUP BY ta.ID;
