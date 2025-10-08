-- Cities
INSERT INTO Cities (Name) VALUES ('Copenhagen');
INSERT INTO Cities (Name) VALUES ('Glostrup');

-- Tags
INSERT INTO Tags (Name) VALUES ('Active');
INSERT INTO Tags (Name) VALUES ('Adult');
INSERT INTO Tags (Name) VALUES ('Children');
INSERT INTO Tags (Name) VALUES ('Culture');
INSERT INTO Tags (Name) VALUES ('Entertainment');

-- TouristAttractions with explicit IDs
INSERT INTO TouristAttractions (CityName, Name, Description) VALUES
                                                                 ('Glostrup', 'Water Park', 'Fun with water'),
                                                                 ('Copenhagen', 'Amusement Park', 'Fun with roller coasters'),
                                                                 ('Copenhagen', 'Museum', 'Enjoy the culture'),
                                                                 ('Copenhagen', 'Royal Garden', 'Enjoy the beauty');

-- AttractionTags
INSERT INTO AttractionTags (AttractionID, TagName) VALUES
                                                       (1, 'Adult'), (1, 'Children'), (1, 'Entertainment'), (1, 'Active'),
                                                       (2, 'Adult'), (2, 'Children'), (2, 'Entertainment'), (2, 'Active'),
                                                       (3, 'Adult'), (3, 'Culture'),
                                                       (4, 'Adult'), (4, 'Children'), (4, 'Culture');
