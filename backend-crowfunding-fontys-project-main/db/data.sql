INSERT INTO `users` (`id`, `email`, `name`, `password`, `profile_picture`, `role`) VALUES
	(1, 'user@example.com', 'Mateo Gonzalez', '$2a$10$h3Zw3WLE/6R0wF2.h.l/ZeubLSjCu1doyLe9Mqkfsuwttm1S5KF12', 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/f92830967c5c0cd3514cb2ef821f1718e3fa79f16682b2ae204dd5e65064f83d', 'user'),
	(2, 'emilyjohnson@example.com', 'Emily Johnson', '$2a$10$wTXVMuYycfB/VWB0p6iLMeYNoOk6qhLHpWbPw61.Wlgx6oEVHv.pi', 'https://avatar.iran.liara.run/public/boy?username=Emily Johnson', 'user'),
	(3, 'albertanderson@example.com', 'Albert Anderson', '$2a$10$554qcpFTmL7So3iv7JWa4.n8U1O2LdGdJAS.ypTX0PDb1p/uXKJKe', 'https://avatar.iran.liara.run/public/boy?username=Albert Anderson', 'user'),
	(4, 'sophiadavis@example.com', 'Sophia Davis', '$2a$10$v8/Pv34ERCb20X3ptnX0XeZZNg2wh1v2uEarLDGswjWVVRmZpsTRe', 'https://avatar.iran.liara.run/public/boy?username=Sophia Davis', 'user'),
	(5, 'jameswilson@example.com', 'James Wilson', '$2a$10$ZrszqlQPBWHLK7mcioj9Fe4yJ5No4E/5maEJBvRt0DD10XvWr6nBO', 'https://avatar.iran.liara.run/public/boy?username=James Wilson', 'user');

INSERT INTO `projects` (`id`, `name`, `date_created`, `description`, `funding_goal`, `location`, `money_raised`, `type`, `user_id`) VALUES
	(1, 'Community Garden Project', '2024-09-15 12:00:00', 'This initiative focuses on establishing a sustainable community garden designed to address multiple issues such as food insecurity, urban environmental challenges, and community engagement. By transforming vacant lots and underutilized urban spaces into vibrant, productive gardens, the project aims to provide fresh, organic produce to local families while promoting environmentally friendly practices. Educational workshops will teach residents about composting, crop rotation, and organic pest control, empowering them to grow their own food and reduce their carbon footprint. Additionally, the garden will serve as a hub for community interaction, fostering stronger social ties and offering opportunities for volunteerism, skill-sharing, and outdoor recreation.', 12000, 'Downtown Area', 12, 'Environment', 1),
	(2, 'School Library Fundraiser', '2024-09-20 13:30:00', 'This fundraiser is dedicated to transforming the school library into a modern learning center that meets the diverse needs of students in the 21st century. With the funds raised, the project will renovate the library\'s physical space, creating dedicated zones for collaborative work, quiet study, and digital learning. A significant portion of the funds will go toward expanding the collection of books, e-books, and audiobooks, ensuring students have access to a wide range of educational and recreational materials. Additionally, new technology, including tablets, computers, and interactive whiteboards, will be introduced to enhance the learning experience. The project also aims to host literacy programs, author visits, and workshops to inspire a lifelong love of reading and learning among students.', 5000, 'Springfield High School', 3100, 'Education', 1),
	(3, 'Clean Water Initiative', '2024-09-25 16:15:00', 'This vital initiative addresses the urgent need for access to clean and safe drinking water in communities that suffer from contaminated or insufficient water supplies. The project plans to implement sustainable water solutions, including drilling wells, installing filtration systems, and repairing existing infrastructure. Beyond providing immediate access to potable water, the initiative will educate community members on proper sanitation practices, water conservation, and maintenance of the systems to ensure long-term success. By targeting rural and underserved regions, the program seeks to drastically reduce waterborne diseases, improve overall health outcomes, and enhance the quality of life for thousands of people. This initiative underscores the belief that access to clean water is a fundamental human right, and it aims to create lasting change for future generations.', 20000, 'Various Locations', 1901, 'Health', 2),
	(4, 'Renewable Energy Campaign', '2024-10-05 14:00:00', 'This forward-thinking campaign is dedicated to accelerating the adoption of renewable energy technologies across the community, with a focus on sustainability, cost savings, and environmental impact. The project plans to install solar panels on public buildings, provide subsidies for residential solar systems, and introduce community wind and geothermal projects to reduce dependency on fossil fuels. Educational workshops and seminars will be conducted to inform residents about the benefits of renewable energy, including long-term savings and reduced carbon emissions. Additionally, the campaign aims to collaborate with local businesses and policymakers to create incentives for adopting green energy solutions. By fostering a culture of sustainability, the project envisions a future where renewable energy is not only accessible but also embraced as the norm in every household and organization in the community.', 25000, 'Citywide', 357.25, 'Energy', 4),
	(5, 'Tech for All Initiative', '2024-12-08 01:55:32', 'This innovative campaign focuses on bridging the digital divide by providing access to technology, digital literacy training, and career development resources to underserved communities. The project aims to distribute refurbished laptops, offer free coding and software development bootcamps, and create mentorship programs connecting tech professionals with aspiring students. Public tech hubs equipped with high-speed internet and learning materials will be established in local libraries and community centers. In collaboration with educational institutions and tech companies, the campaign will facilitate job placement programs and internships to empower individuals with employable digital skills. By fostering tech inclusivity, the project envisions a future where technological advancement is a shared opportunity for all.', 30000, 'Community Centers and Schools', 0, 'Education', 5),
	(6, 'Green City Gardens Project', '2024-12-08 01:56:28', 'This eco-friendly campaign aims to transform underutilized urban spaces into thriving community gardens, promoting food security, sustainability, and environmental stewardship. The project will establish rooftop gardens, vertical farms, and community plots where residents can grow fruits, vegetables, and herbs. Workshops on organic farming, composting, and water conservation will be conducted to educate participants on sustainable gardening practices. The initiative plans to partner with local restaurants, schools, and community organizations to distribute surplus produce and reduce food waste. By fostering a culture of urban agriculture, the project envisions greener neighborhoods, healthier diets, and stronger community bonds through shared gardening experiences.', 8000, 'Urban Neighborhoods and Public Spaces', 395, 'Environment', 3),
	(7, 'Youth Arts Empowerment Program', '2024-12-20 19:24:20', 'A project designed to offer free art classes, music lessons, and creative workshops for young people from low-income families. This initiative promotes self-expression and career development in the arts.', 15000, 'Art District Center', 0, 'Education', 2),
	(8, 'Eco-Friendly Housing Initiative', '2024-12-20 20:17:48', 'An innovative effort to construct eco-friendly, affordable housing units using sustainable materials and energy-efficient designs. The project targets low-income families to provide safe, green homes.', 25000, 'Greenwood Residential Area', 0, 'Environment', 5),
	(9, 'Local History Preservation Project', '2024-12-20 20:21:42', 'This project aims to document and preserve the local community\'s rich historical heritage through archival work, oral histories, and cultural exhibitions, ensuring future generations learn about their roots.', 6000, 'City Archives and Museums', 15, 'Culture', 1);

INSERT INTO `payments` (`id`, `amount`, `payment_date`, `project_id`, `user_id`) VALUES
	(1, 85, '2024-12-20 18:44:53.397000', 6, 1),
	(2, 5, '2024-12-20 18:45:10.971000', 6, 4),
	(3, 260, '2024-12-20 18:45:47.734000', 6, 2),
	(4, 50, '2024-12-20 18:46:36.442000', 6, 1),
	(5, 3100, '2024-12-20 18:47:26.417000', 2, 3),
	(6, 145, '2024-12-20 18:48:46.191000', 4, 1),
	(7, 82.25, '2024-12-20 18:48:54.076000', 4, 5),
	(8, 125, '2024-12-20 18:48:59.080000', 4, 2),
	(9, 5, '2024-12-20 18:49:02.100000', 4, 3),
	(10, 85, '2024-12-20 18:50:23.780000', 3, 1),
	(11, 1400, '2024-12-20 18:50:27.718000', 3, 4),
	(12, 400, '2024-12-20 18:50:32.190000', 3, 3),
	(13, 2, '2024-12-20 18:50:35.685000', 3, 4),
	(14, 14, '2024-12-20 18:50:39.051000', 3, 1),
	(15, 12, '2024-12-20 20:24:42.526000', 1, 5),
	(16, 15, '2024-12-20 21:12:38.548000', 9, 3);


INSERT INTO `project_images` (`id`, `image_order`, `image_url`, `project_id`) VALUES
	(1, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/community-garden-1.jpg', 1),
	(2, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/community-garden-2.jpg', 1),
	(3, 3, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/community-garden-3.jpg', 1),
	(4, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/school-library-1.jpg', 2),
	(5, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/school-library-2.jpg', 2),
	(6, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/clean-water-1.png', 3),
	(7, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/clean-water-2.jpg', 3),
	(8, 3, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/clean-water-3.jpg', 3),
	(9, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/renewable-energy-1.jpg', 4),
	(10, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/renewable-energy-2.webp', 4),
	(11, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/01d7ba7683552438f121d27552193424b11f57e6e568dde00ba8792feecb17bd', 5),
	(12, 3, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/fdac2894253ebe972e098e94aad6fa028435d63dba6b308849ff7e67fd6da980', 6),
	(13, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/0d584c7016d7d722ff9f0b62eba4245b7647de9636e8a738669ca77e6d47ed52', 6),
	(14, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/f60c94be8315b7ec442c90b04096e0509d22cf7ccb6de25404fa1be4f75593a3', 6),
	(15, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/88a6a229399633a0d90425a08313cfe9dce4bf64d36a8b7aeda2515f7991f7f7', 7),
	(16, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/4c05124425b9d0256e7b7b06b6d1e7cbdedc243fe61b926b33210e5605a899c4', 7),
	(17, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/33b4f447dbeb1ad7584af96b78e54ab13dd147466bcc4b4e90b98710a5a3a752', 8),
	(18, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/db87a9aa357f6e0da645d9383541faed067754af5e07e8203c075655dbc37734', 8),
	(19, 1, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/7b54f58121677ad25697c2126049c1b35d6e776e44eb91859025800ea9b0d708', 9),
	(20, 2, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/8f2080a9b108a5034cf9e0461f96b92c2a44df876b2a613231be3e8e2e1beead', 9),
	(21, 3, 'https://raisehub-crowdfunding-bucket.s3.eu-west-3.amazonaws.com/2e09f384d55063f9ec0d92042606008a617118caa21e61d5d581f8f9ac71de0e', 9);

