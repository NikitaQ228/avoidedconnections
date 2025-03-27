BEGIN;

-- Вставляем посты о неприятных встречах (10 записей)
INSERT INTO public.story (id, date, user_id, head, city, img, text) VALUES
(1, '2024-03-10 08:15:00', 1, 'Агрессивный попрошайка', 'Москва', 'metro1.jpg', 'Метро Курская, вагон 5. Мужик лет 50 с бутылкой требует деньги, хватает за руки. Будьте осторожны!'),
(2, '2024-03-11 18:30:00', 2, 'Пьяная компания в автобусе', 'Санкт-Петербург', 'bus42.jpg', 'Маршрут 42, кричали и бросали банки. Двое в камуфляжных штанах.'),
(3, '2024-03-12 12:00:00', 3, 'Лже-волонтер', 'Екатеринбург', 'scam.jpg', 'У ТЦ "Гринвич" собирает деньги на несуществующий приют. Проверяйте документы!'),
(4, '2024-02-28 20:45:00', 4, 'Наркоман у детской площадки', 'Казань', 'park.jpg', 'Сидит возле горки, что-то колет шприцом. Вызвали полицию.'),
(5, '2024-03-01 07:15:00', 5, 'Воришка в электричке', 'Нижний Новгород', 'train.jpg', 'Курсирует между Дзержинском и НН. Крадет телефоны у спящих.'),
(6, '2024-03-05 09:00:00', 1, 'Мошенник с "упавшими деньгами"', 'Новосибирск', 'scam2.jpg', 'Возле вокзала пытается развести на деньги по старой схеме.'),
(7, '2024-03-08 22:10:00', 2, 'Гопники у метро Пушкинская', 'Ростов-на-Дону', 'gopniki.jpg', 'Требуют "покурить", преследуют до остановки.'),
(8, '2024-03-09 15:30:00', 3, 'Бомж с ножом в парке', 'Сочи', 'park_night.jpg', 'Спит на лавочке, при приближении достает перочинный нож.'),
(9, '2024-02-25 19:20:00', 4, 'Навязчивый гадалка', 'Владивосток', 'witch.jpg', 'У центрального рынка хватает за руки, пугает "проклятиями".'),
(10, '2024-03-07 11:45:00', 5, 'Пьяный водитель маршрутки', 'Калининград', 'minibus.jpg', 'Маршрут 145, ехал зигзагами, орал песни. Номера А123ВС 39 RUS.');

-- Добавляем комментарии-подтверждения (3 комментария к каждой истории) с датой
INSERT INTO public.comment (id, story_id, writer_id, text, date) VALUES
(1, 1, 2, 'Этот же был на Чкаловской вчера!', '2024-03-10 09:00:00'),
(2, 1, 4, 'Звоните 102 сразу, он опасен', '2024-03-10 09:05:00'),
(3, 1, 5, 'Видел его с полицией через час после поста', '2024-03-10 09:10:00'),
(4, 2, 1, 'Езжу этим маршрутом, теперь буду обходить', '2024-03-11 19:00:00'),
(5, 2, 3, 'Они каждый вечер там, знаю по опыту', '2024-03-11 19:05:00'),
(6, 2, 5, 'Вызовите транспортную полицию!', '2024-03-11 19:10:00'),
(7, 3, 1, 'Проверяйте удостоверение с печатью', '2024-03-12 13:00:00'),
(8, 3, 2, 'Собрал 5000₽ за час пока наблюдал', '2024-03-12 13:05:00'),
(9, 3, 5, 'Его уже задерживали в прошлом месяце', '2024-03-12 13:10:00'),
(10, 4, 1, 'Детям сейчас вообще страшно гулять', '2024-02-28 21:00:00'),
(11, 4, 2, 'Адрес точный? Отправлю участкового', '2024-02-28 21:05:00'),
(12, 4, 3, 'В этом районе целая проблема с этим', '2024-02-28 21:10:00'),
(13, 5, 1, 'Мой друг лишился iPhone так', '2024-03-01 08:30:00'),
(14, 5, 2, 'Работает в паре с женщиной-отвлекателем', '2024-03-01 08:35:00'),
(20 ,7 ,4 ,'Их банда из5 человек , осторожнее','2024 -03 -08 -22 :20 :00' ),
(21 ,7 ,5 ,'Переходите на другую сторону заранее','2024 -03 -08 -22 :25 :00' ),
(22 ,8 ,2 ,'Ночью там вообще адский квартал','2024 -03 -09 -16 :30 :00' ),
(23 ,8 ,4 ,'Полиция приезжает через40 минут...','2024 -03 -09 -16 :35 :00' ),
(24 ,8 ,5 ,'Заведите электрошокер по закону','2024 -03 -09 -16 :40 :00' ),
(25 ,9 ,1 ,'Брызгайте в лицо антисептиком','2024 -02 -25 -20 :30 :00' ),
(26 ,9 ,2 ,'Она следит за жертвами по часам','2024 -02 -25 -20 :35 :00' ),
(27 ,9 ,3 ,'Работает с2019 года , никак не поймают','2024 -02 -25 -20 :40 :00' ),
(28 ,10 ,1 ,'Видел этого водителя пьяным вчера!','2024 -03 -07 -12 :15 :00' ),
(29 ,10 ,2 ,'Номера записал , отправил в ГИБДД','2024 -03 -07 -12 :20 :00' ),
(30 ,10 ,4 ,'Маршрутчики-пьяницы-бич города','2024 -03 -07 -12 :25 :00');

-- Отмечаем пользователей которые сталкивались с этими ситуациями
INSERT INTO public.users_tag (story_id,user_id) VALUES
 (1 ,2) ,
 (1 ,3) ,
 (1 ,5) ,
 (2 ,4) ,
 (2 ,1) ,
 (3 ,3) ,
 (3 ,5) ,
 (4 ,1) ,
 (4 ,2) ,
 (5 ,3) ,
 (5 ,4) ,
 (6 ,2) ,
 (6 ,5) ,
 (7 ,3) ,
 (7 ,4) ,
 (8 ,2);

COMMIT;

