INSERT INTO Cities (Name) VALUES
	('Copenhagen'), ('Glostrup');

INSERT INTO Tags (Name) VALUES 
	('Active'), ('Adult'), ('Children'), 
    ('Culture'), ('Entertainment');
    
INSERT INTO TouristAttractions (CityName, Name, Description) VALUES
	('Glostrup', 'Water Park', 'Fun with water'), 
    ('Copenhagen', 'Amusement Park', 'Fun with roller coasters'), 
    ('Copenhagen', 'Museum', 'Enjoy the culture'), 
    ('Copenhagen', 'Royal Garden', 'Enjoy the beauty');
    
INSERT INTO AttractionTags (TagName, AttractionID) VALUES 
	('Adult', 1), ('Children', 1), ('Entertainment', 1), ('Active', 1),
    ('Adult', 2), ('Children', 2), ('Entertainment', 2), ('Active', 2),
    ('Adult', 3), ('Culture', 3), 
    ('Adult', 4), ('Children', 4), ('Culture', 4);
