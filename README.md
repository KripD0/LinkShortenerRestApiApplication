# LinkShortenerRestApiApplication
# Это REST API микросервис по сокращению ссылок, который будет использован для телеграм бота.
- Реализовано:
- Метод по сокращнию ссылки, добавление в БД.
- Метод по добавлению сокращенной ссылке которую отправил пользователь.
- Метод который перенаправляет с сокращенной ссылки на оригинальную страницу.
- Проверка каждый день в 9 утра ссылок, если ей больше 7 дней то она удалится.
- Написана документация Swagger.
- Написан телеграм бот, который обращается через Feign client к этому микросервису.
- Есть исключения по типу ненайденной ссылки или уже существующий, реализована их обработка и ответ в виде JSON.
