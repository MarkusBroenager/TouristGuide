DROP VIEW IF EXISTS TouristAttractionView;
DROP TABLE IF EXISTS AttractionTags;
DROP TABLE IF EXISTS TouristAttractions;
DROP TABLE IF EXISTS Tags;
DROP TABLE IF EXISTS Cities;

CREATE TABLE Cities (
                        Name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE Tags (
                      Name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE TouristAttractions (
                                    ID INT AUTO_INCREMENT PRIMARY KEY,
                                    Name VARCHAR(255),
                                    Description VARCHAR(1024),
                                    CityName VARCHAR(255),
                                    FOREIGN KEY (CityName) REFERENCES Cities(Name)
);

CREATE TABLE AttractionTags (
                                AttractionID INT,
                                TagName VARCHAR(255),
                                FOREIGN KEY (AttractionID) REFERENCES TouristAttractions(ID),
                                FOREIGN KEY (TagName) REFERENCES Tags(Name)
);


CREATE VIEW TouristAttractionView AS
SELECT ta.ID AS id,
       ta.Name AS name,
       ta.Description AS description,
       c.Name AS city,
       STRING_AGG(t.Name, ', ') AS tags
FROM TouristAttractions ta
         JOIN Cities c ON ta.CityName = c.Name
         LEFT JOIN AttractionTags at ON ta.ID = at.AttractionID
LEFT JOIN Tags t ON at.TagName = t.Name
GROUP BY ta.ID, ta.Name, ta.Description, c.Name;


