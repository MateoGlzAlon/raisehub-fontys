-- Insert into USERS
INSERT INTO users (id, email, name, password, profile_picture, role) VALUES (1, 'user@example.com', 'Matthew Stone', '$2a$10$ZRBvWF4BwgJmLnWC4n/6POeMbWGa69OgKLk.Wp1r81ObPjYFGOIxS', 'https://avatar.iran.liara.run/public', 'user');
INSERT INTO users (id, email, name, password, profile_picture, role) VALUES (2, 'emilyjohnson@example.com', 'Emily Johnson', '$2a$10$WPTxtcRvZTAC8aghbt3FT.toOnNI6DLT5iXJFoGQnjpNxkJ6A58OC', 'https://avatar.iran.liara.run/public', 'user');
INSERT INTO users (id, email, name, password, profile_picture, role) VALUES (3, 'michaelbrown@example.com', 'Michael Brown', '$2a$10$O8lg9et43FeaQEZXDP0mquUYBGv2uCUGDeV5q3UV/QnooeTzrXSie', 'https://avatar.iran.liara.run/public', 'user');
INSERT INTO users (id, email, name, password, profile_picture, role) VALUES (4, 'sophiadavis@example.com', 'Sophia Davis', '$2a$10$xd.mTht3m4KYKbiyHpbHI.8Sjyz8h6li4eIVgvCcvO5pwAeVvc3im', 'https://avatar.iran.liara.run/public', 'user');
INSERT INTO users (id, email, name, password, profile_picture, role) VALUES (5, 'jameswilson@example.com', 'James Wilson', '$2a$10$6ymyhl7Qrv9DjWgf20fTAuab0Otwtqz0PEuxg5VcgKjTeQuMxAkbe', 'https://avatar.iran.liara.run/public', 'user');
INSERT INTO users (id, email, name, password, profile_picture, role) VALUES (6, 'admin@example.com', 'The Admin', '$2a$10$QgMs6MACwOtRCCPsaud2heiOW/YIoerwwfsBkesRcNT91iBGBrtEe', 'https://avatar.iran.liara.run/public', 'admin');

-- Insert into PROJECTS
INSERT INTO projects (id, date_created, description, funding_goal, location, money_raised, name, type, user_id) VALUES (1, '2024-09-15 12:00:00', 'This initiative focuses on establishing a sustainable community garden...', 12000, 'Downtown Area', 6543, 'Community Garden Project', 'Environment', 1);
INSERT INTO projects (id, date_created, description, funding_goal, location, money_raised, name, type, user_id) VALUES (2, '2024-09-20 13:30:00', 'This fundraiser is dedicated to transforming the school library...', 5000, 'Springfield High School', 0, 'School Library Fundraiser', 'Education', 2);

-- Insert into PAYMENTS
INSERT INTO payments (id, amount, payment_date, project_id, user_id) VALUES (1,  50, '2024-09-25 16:15:00', 1, 2);
INSERT INTO payments (id, amount, payment_date, project_id, user_id) VALUES  (2, 75.5, '2024-09-26 12:30:00', 1, 2);

-- Insert into PROJECT_IMAGES
INSERT INTO project_images (id, image_order, image_url, project_id) VALUES (1, 1, 'https://placehold.co/600x400?text=p1.1', 1);
INSERT INTO project_images (id, image_order, image_url, project_id) VALUES (2, 2, 'https://placehold.co/600x400?text=p1.2', 1);
