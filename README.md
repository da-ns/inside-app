# Тестовое задание для Inside

## Установка

Клонировать репозиторий:
```
git clone https://github.com/da-ns/inside-app.git
```

Перейти в корень проекта:
```
cd inside-app
```

Выполнить сборку приложения:
```
mvnw clean install
```

Запустить докер:
```
docker-compose up --build
```

Приложение станет доступно по адресу: http://localhost:8080.

Настройки доступа к БД:

```
database=inside
server=localhost
port=3306
username=app
password=12345
```

Для работы с приложением в БД внесен один пользователь. Данные его аккаунта:
```
login=panda
password=bamboo
```

## REST API

**GET /**<br>
Пользовательский интерфейс для работы с приложением.

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
