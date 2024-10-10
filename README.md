

# Weather Project

## Обзор и мотивация создания проекта

- Использование cookies и сессий для авторизации пользователей
- Работа с внешними API
- Также знакомство с Docker и Docker Compose
- Использование Тест-котейнера для тестов
- Средство миграций БД

Проект создан в образовательных целях
по [предоставленному ТЗ](https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/)
в контексте [java-backend-learning-course](https://zhukovsd.github.io/java-backend-learning-course/#%D1%82%D1%80%D0%B5%D0%B1%D1%83%D0%B5%D0%BC%D1%8B%D0%B5-%D0%B7%D0%BD%D0%B0%D0%BD%D0%B8%D1%8F-%D0%B8-%D1%82%D0%B5%D1%85%D0%BD%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D0%B8)
от Сергея Жукова.

## Используемые технологии/инструменты:

- Jakarta Servlets
- Maven
- Hibernate
- PostgreSQL
- Liquibase
- Thymeleaf
- HTML/CSS
- JUnit5
- Mockito
- Docker
- TestContainers

## Установка

### Требования
+ Docker, Docker Compose
+ Intellij IDEA

### Запуск проекта локально с помощью Докера 

1. Клонировать репозиторий и открыть проект в Intellij IDEA
2. В файле config.properties вставьте свой ключ от api OpenWeather
3. Далее используйте maven-install для того чтобы собрать WAR проекта 
3. Откройте командную строку находясь в корневой папке проекта и введите "docker build -t weather-app ." для того чтобы собрать образ проекта
4. Там же в командной строке введите команду docker-compose up --build для сборки и запуска компоуза
5. Зайдите на http://localhost:8080/
## Использование и функционал проекта
Деплой проекта: пока без деплоя( 

**Страница авторизации пользователей, при неправильном логине или пароле, выводит сообщение с ошибкой**
![sign-in.png](https://github.com/SahaPWNZ/imagesForGit/blob/main/image/weather-app/sign-in.png)


**Страница регистрации пользователей, при вводе не уникального логина и попытке регистрации, выводит сообщение с ошибкой**
![sign-on.png](https://github.com/SahaPWNZ/imagesForGit/blob/main/image/weather-app/sign-on.png)

**Главная страница приложения обладает функционалом: логаут пользователей, поиском локаций, выводом сохраненных локаций, удаление сохранённых локаций**

![main.png](https://github.com/SahaPWNZ/imagesForGit/blob/main/image/weather-app/main.png)

**Страница с найденными локациями позволяет: логаут пользователей, возвращение на главную страницу, сохранение найденной локации**

![getLocations.png](https://github.com/SahaPWNZ/imagesForGit/blob/main/image/weather-app/getLocations.png)
