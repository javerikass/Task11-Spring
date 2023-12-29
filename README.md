## Реализация кэша с алгоритмами LRU и LFU с помощью паттерна Factory
Это проект Gradle, который предоставляет реализацию кэша с использованием алгоритмов LRU (Least Recently Used) и LFU (Least Frequently Used). 
Проект совместим с Java 17. Реализована печать документов в .pdf файл.

## Структура проекта
Проект организован по следующим слоям: servlet, service и dao. 
Servlet принимает запросы и передаёт на service, service вызывает слой dao.

## Зависимости проекта
### Проект использует следующие зависимости:

- Lombok
- Servlet
- JUnit
- Mockito
- Spring JDBC
- AspectJ
- HikariCP
- PostgreSQL
- Mapstruct
- Log4j
- Slf4j
- Gson
- ITextpdf

## Алгоритм кэширования
### Реализация кэша включает следующие операции:

- /get: Ищет данные в кэше. Если данные отсутствуют, извлекает объект из слоя dao, сохраняет его в кэше и возвращает.
- /save: Сохраняет данные в слое dao, а затем сохраняет их в кэше.
- /delete: Удаляет данные из слоя dao, а затем удаляет их из кэша.
- /update: Обновляет/вставляет данные в слое dao, а затем обновляет/вставляет их в кэше.
- /getAll: Выводит все данные постранично.

Алгоритм кэширования и максимальный размер коллекции кэша можно настроить в файле resources/application.yml.

## Сущность и DTO
В проекте присутствует класс User, который содержит поле id и как минимум четыре дополнительных поля. Слой service работает с UserDTO. Объекты, передаваемые в слой service, проходят валидацию, включая проверку с использованием регулярных выражений.

## Синхронизация с помощью прокси
Результат работы слоя dao синхронизируется с кэшем с использованием прокси AOP/AspectJ.

## Юнит-тесты
Реализация кэша и сервисы покрыты юнит-тестами

## Примеры запросов

### Получение пользователя по ID
##### Метод: GET
*URL: /users/get/{id}*
#### Пример запроса:
*GET /users/get/123e4567-e89b-12d3-a456-426614174000*
#### Пример ответа:
{
"id": "123e4567-e89b-12d3-a456-426614174000",
"firstName": "John",
"lastName": "Doe",
"mail": "johndoe@example.com",
"age": 25
}

### Создание пользователя
#### Метод: POST
*URL: /users*
#### Пример запроса:
*POST /users*

{
"firstName": "John",
"lastName": "Doe",
"mail": "johndoe@example.com",
"age": 25
}

### Удаление пользователя по идентификатору
#### Метод: POST
*URL: /users/delete/{id}*
#### Пример запроса:
*POST /users/delete/123e4567-e89b-12d3-a456-426614174000*

### Обновление пользователя
#### Метод: POST
*URL: /users/update*
#### Пример запроса:
*POST /users/update*

{
"id": "123e4567-e89b-12d3-a456-426614174000",
"firstName": "John",
"lastName": "Doe",
"mail": "johndoe@example.com",
"age": 30
}

### Обновление пользователя
#### Метод: GET
*URL: /users/getAll*
#### Пример запроса:
*GET /users/getAll?pageSize=5&pageNumber=1*
#### Пример ответа:

[
{
"id": "37bc2b04-8607-11ee-b9d1-0242ac120002",
"firstName": "John",
"lastName": "Doe",
"mail": "john.doe@mail.ru",
"age": 40
},
{
"id": "3ef4c1f6-8607-11ee-b9d1-0242ac120002",
"firstName": "Jane",
"lastName": "Smith",
"mail": "jane.smith@gmail.com",
"age": 30
},
{
"id": "43c4ec92-8607-11ee-b9d1-0242ac120002",
"firstName": "Alice",
"lastName": "Johnson",
"mail": "alice.johnson@gmail.com",
"age": 28
},
{
"id": "4a2198e8-8607-11ee-b9d1-0242ac120002",
"firstName": "Bob",
"lastName": "Williams",
"mail": "bob.williams@yahoo.com",
"age": 35
},
{
"id": "4f7ac3da-8607-11ee-b9d1-0242ac120002",
"firstName": "Eva",
"lastName": "Brown",
"mail": "eva.brown@hotmail.com",
"age": 25
}
]

