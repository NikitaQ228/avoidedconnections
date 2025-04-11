# Avoided connections

Существует газета электронных объявлений, в которой есть раздел «Ищу тебя», где люди могут создать пост о человеке,
увиденном на улице или в любом общественном месте, который улыбнулся им или пересёкся взглядом. Они надеятся, что
человек прочитает это объявление и откликнется. Мы сделаем наоборот: про людей, которых вы увидели в общественном месте
и быстро отвернулись, надеясь, что они не заговорят с вами. Обычно это нищие, мошенники и алкаши в общественном
транспорте. Некоторые из этих типов людей могут делать эксцентричные вещи, особенно если они пьяные, так что это будет
прекрасная возможность поделиться историей.

Выполнено студентами группы 6301-010302D Мусаиловым Даниэлем и Бабошиным Никитой

Ссылка на
тесты: https://bold-flare-58914.postman.co/workspace/New-Team-Workspace~c5d4a00c-b124-4f48-b7fa-27efb6a1c9b7/collection/34548696-de5bf14a-7eec-4dfb-bba7-f659999e0fb2?action=share&creator=34548696


# 1. Проектирование архитектуры

Схема взаимодействия компонентов:
- Клиент (Frontend): Веб-приложение, через которое пользователи (авторы историй и комментаторы) взаимодействуют с системой.
- Сервер (Backend): Обрабатывает запросы от клиента, выполняет бизнес-логику и взаимодействует с базой данных.
- База данных (DB): Хранит информацию о пользователях, историях, комментариях и т.д.

Схема взаимодействия:
Клиент (Frontend) -> Сервер (Backend) -> База данных (DB)

# 2. Логическая схема базы данных

Сущности:
1. Comment (Комментарий):
    - `id` (Primary Key)
    - `writer_id` (Foreign Key к таблице User)
    - `story_id` (Foreign Key к таблице Story)
    - `date` (Дата)
    - `text` (Текст комментария)

2. Story (История):
    - `id` (Primary Key)
    - `user_id` (Foreign Key к таблице User)
    - `usersTag` (Foreign Key к связующей таблице users_tag)
    - `head` (Заголовок)
    - `img` (Ссылка на рисунок)
    - `text` (Текст истории)
    - `date` (Дата)
    - `city` (Город истории)

3. User (Пользователь):
    - `id` (Primary Key)
    - `stories` (Foreign Key к связующей таблице users_tag)
    - `name` (Логин пользователя)
    - `icon` (Ссылка на иконку пользователя)
    - `password` (Пароль)

Связи:
- Many-to-Many: Связь между ‘Story’ и ‘User’ с использованием связующей таблицы для связи.

# 3. Структура API

### 1. Аутентификация

#### Зарегистрироваться
- Метод: `POST`
- URL: `auth/registration`
  - Тело:
    {
    "name": "nikita22",
    "password": "pass123456"
    }
- Ответ:


#### Зайти
- Метод: `POST`
- URL: `auth/signIn`
    - Тело:
      {
      "name": "user1",
      "password": "pass1"
      }
- Ответ:
  {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTc0NDQwMzY1Nn0.7jDGTfjj0Ja3GVXAAS_d1NT7Lf1lLOVSjEiqdi4nhLD6Hm7vX8bE6jq5IhhU8OUQioUlzxRtYhraUfLOcYzORA",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTc0NDQ4NjQ1Nn0.Hodxbn2NcGRw2dsaa_IVrubxdn_IxuWycear5pHbgmIzUKL-dqJXgucCuH2TVrnO91fYHFEQMod1jRurQSLGOw"
  }

#### Выйти
- Метод: `POST`
- URL: `logout`
    - Тело:
- Ответ:

#### Обновить
- Метод: `POST`
- URL: `auth/refresh`
    - Тело:
      {
      "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuaWtpdGEyMiIsImV4cCI6MTc0NDQ1NTA0MX0.Ng6aTjwnfxtaKjLz4xpM45j0lHCMoive3Pj_nCsqPamSMpatD2Z5hUzNRWP7ldHeuF3L-BC_2yXJTESjHT73Yw"
      }
- Ответ:
  {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTc0NDQwMzY1Nn0.7jDGTfjj0Ja3GVXAAS_d1NT7Lf1lLOVSjEiqdi4nhLD6Hm7vX8bE6jq5IhhU8OUQioUlzxRtYhraUfLOcYzORA",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTc0NDQ4NjQ1Nn0.Hodxbn2NcGRw2dsaa_IVrubxdn_IxuWycear5pHbgmIzUKL-dqJXgucCuH2TVrnO91fYHFEQMod1jRurQSLGOw"
  }


### 2. main

#### Поиск по городу
- Метод: `GET`
- URL: `mainInfo?city=Москва`
- Ответ:
  [
  {
  "id": 1,
  "head": "Агрессивный попрошайка",
  "img": "metro1.jpg",
  "text": "Метро Курская, вагон 5. Мужик лет 50 с бутылкой требует деньги, хватает за руки. Будьте осторожны!",
  "date": "2024-03-10T04:15:00.000+00:00",
  "city": "Москва",
  "author": {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  }
  ]
  }
  ]

#### Поиск по городу и запросу
- Метод: `GET`
- URL: `mainInfo?city=Казань&query=Наркоман`
- Ответ:
  [
  {
  "id": 4,
  "head": "Наркоман у детской площадки",
  "img": "park.jpg",
  "text": "Сидит возле горки, что-то колет шприцом. Вызвали полицию.",
  "date": "2024-02-28T16:45:00.000+00:00",
  "city": "Казань",
  "author": {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  }
  ]
  }
  ]


#### Поиск по запросу
- Метод: `GET`
- URL: `mainInfo?query=Наркоман`
- Ответ:
  [
  {
  "id": 4,
  "head": "Наркоман у детской площадки",
  "img": "park.jpg",
  "text": "Сидит возле горки, что-то колет шприцом. Вызвали полицию.",
  "date": "2024-02-28T16:45:00.000+00:00",
  "city": "Казань",
  "author": {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  }
  ]
  }
  ]

#### ОПоиск по пустому запросу
- Метод: `GET`
- URL: `mainInfo?`
- Ответ:
  [
  {
  "id": 3,
  "head": "Лже-волонтер",
  "img": "scam.jpg",
  "text": "У ТЦ \"Гринвич\" собирает деньги на несуществующий приют. Проверяйте документы!",
  "date": "2024-03-12T08:00:00.000+00:00",
  "city": "Екатеринбург",
  "author": {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 2,
  "head": "Пьяная компания в автобусе",
  "img": "bus42.jpg",
  "text": "Маршрут 42, кричали и бросали банки. Двое в камуфляжных штанах.",
  "date": "2024-03-11T14:30:00.000+00:00",
  "city": "Санкт-Петербург",
  "author": {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  },
  {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 1,
  "head": "Агрессивный попрошайка",
  "img": "metro1.jpg",
  "text": "Метро Курская, вагон 5. Мужик лет 50 с бутылкой требует деньги, хватает за руки. Будьте осторожны!",
  "date": "2024-03-10T04:15:00.000+00:00",
  "city": "Москва",
  "author": {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 8,
  "head": "Бомж с ножом в парке",
  "img": "park_night.jpg",
  "text": "Спит на лавочке, при приближении достает перочинный нож.",
  "date": "2024-03-09T11:30:00.000+00:00",
  "city": "Сочи",
  "author": {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 7,
  "head": "Гопники у метро Пушкинская",
  "img": "gopniki.jpg",
  "text": "Требуют \"покурить\", преследуют до остановки.",
  "date": "2024-03-08T18:10:00.000+00:00",
  "city": "Ростов-на-Дону",
  "author": {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 10,
  "head": "Пьяный водитель маршрутки",
  "img": "minibus.jpg",
  "text": "Маршрут 145, ехал зигзагами, орал песни. Номера А123ВС 39 RUS.",
  "date": "2024-03-07T07:45:00.000+00:00",
  "city": "Калининград",
  "author": {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  },
  "usersTag": []
  },
  {
  "id": 6,
  "head": "Мошенник с \"упавшими деньгами\"",
  "img": "scam2.jpg",
  "text": "Возле вокзала пытается развести на деньги по старой схеме.",
  "date": "2024-03-05T05:00:00.000+00:00",
  "city": "Новосибирск",
  "author": {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 5,
  "head": "Воришка в электричке",
  "img": "train.jpg",
  "text": "Курсирует между Дзержинском и НН. Крадет телефоны у спящих.",
  "date": "2024-03-01T03:15:00.000+00:00",
  "city": "Нижний Новгород",
  "author": {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 4,
  "head": "Наркоман у детской площадки",
  "img": "park.jpg",
  "text": "Сидит возле горки, что-то колет шприцом. Вызвали полицию.",
  "date": "2024-02-28T16:45:00.000+00:00",
  "city": "Казань",
  "author": {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  }
  ]
  },
  {
  "id": 9,
  "head": "Навязчивый гадалка",
  "img": "witch.jpg",
  "text": "У центрального рынка хватает за руки, пугает \"проклятиями\".",
  "date": "2024-02-25T15:20:00.000+00:00",
  "city": "Владивосток",
  "author": {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  },
  "usersTag": []
  }
  ]

### 3. Story

#### История по id
- Метод: `GET`
- URL: `story/1`
- Ответ:
  {
  "id": 1,
  "head": "Агрессивный попрошайка",
  "img": "metro1.jpg",
  "text": "Метро Курская, вагон 5. Мужик лет 50 с бутылкой требует деньги, хватает за руки. Будьте осторожны!",
  "date": "2024-03-10T04:15:00.000+00:00",
  "city": "Москва",
  "author": {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  "usersTag": [
  {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  {
  "id": 3,
  "name": "user3",
  "icon": "1.png"
  },
  {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  }
  ]
  }

#### Комментарии к истории по id
- Метод: `GET`
- URL: `story/1/comment`
- Ответ:
  [
  {
  "writer": {
  "id": 2,
  "name": "user2",
  "icon": "1.png"
  },
  "storyId": 1,
  "date": "2023-09-17T10:32:55.123+00:00",
  "text": "Этот же был на Чкаловской вчера!"
  },
  {
  "writer": {
  "id": 4,
  "name": "user4",
  "icon": "1.png"
  },
  "storyId": 1,
  "date": "2023-09-17T10:32:55.123+00:00",
  "text": "Звоните 102 сразу, он опасен"
  },
  {
  "writer": {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  },
  "storyId": 1,
  "date": "2023-09-17T10:32:55.123+00:00",
  "text": "Видел его с полицией через час после поста"
  }
  ]

#### Добавить новый комментарий к истории по id
- Метод: `POST`
- URL: `story/1/addComment`
- Тело:
  Вот это да!
- Ответ:
  {
  "writer": {
  "id": 1,
  "name": "user1",
  "icon": "1.png"
  },
  "storyId": 1,
  "date": "2025-04-11T19:45:55.700+00:00",
  "text": "Вот это да!"
  }

#### Удалить историю по id
- Метод: `DELETE`
- URL: `story/10/delete`
- Ответ:
  {
  "id": 10,
  "head": "Пьяный водитель маршрутки",
  "img": "minibus.jpg",
  "text": "Маршрут 145, ехал зигзагами, орал песни. Номера А123ВС 39 RUS.",
  "date": "2024-03-07T07:45:00.000+00:00",
  "city": "Калининград",
  "author": {
  "id": 5,
  "name": "user5",
  "icon": "1.png"
  },
  "usersTag": []
  }

### 4. Profile

### 5. Add Story

# 4. Стек технологий

-	Frontend: HTML/CSS, JavaScript.
-	Backend: Spring (Java).
-	База данных: PostgreSQL.
-	API: RESTful API.
     Дополнительные технологии:
-	ORM: Hibernate (для работы с базой данных в Spring Boot).
-	Аутентификация: Spring Security (для управления доступом и аутентификацией).
-	Документация API: Postman.
-	Документация в GitHub: Markdown API