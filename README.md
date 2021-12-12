# inside-app
Application for Inside

**GET /**<br>
Static page. UI.

**POST /user/login**<br>
Request JSON {*login*: string, *password*: string}<br>
Response JSON {*token*: string}

- *login* Имя пользователя
- *password* Пароль для авторизации
- *token* JWT-токен

**POST /message/send**<br>
Request JSON {*login*: string, *text*: string}<br>
Header Authorization: Bearer *token*<br>
Response JSON {*message*: string}

- *login* Имя пользователя
- *text* Текст сообщения
- *message* Результат сохранения сообщения

**GET /message/history/{*login*}/{*count*}**<br>
Header Authorization: Bearer *token*<br>
Response JSON {[{*id*: int, *text*: string, *date*: string}, ...]}

- *login* Имя пользователя
- *count* Количество сообщений для выгрузки
- *id* Идентификатор сообщения
- *text* Текст сообщения
- *date* Дата сообщения


