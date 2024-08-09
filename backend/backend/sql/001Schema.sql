CREATE TABLE fruit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    origin VARCHAR(255) NOT NULL,
    importDate DATETIME NOT NULL
);


INSERT INTO fruit (quantity, origin, importDate) VALUES
(100, 'Brazil', '2024-08-01 10:30:00'),
(200, 'Ecuador', '2024-08-02 14:45:00'),
(150, 'Colombia', '2024-08-03 09:15:00'),
(175, 'Peru', '2024-08-04 16:00:00');