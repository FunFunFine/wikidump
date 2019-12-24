# WikiDump
**[WIP]**

## Как запустить выгрузку
* скачать архив https://dumps.wikimedia.org/other/cirrussearch/current/ruwikiquote-YYYYMMDD-cirrussearch-general.json.gz
* разахивировать в папку с бинарниками
* передать название файла первым аргументом в `com.voi.wikidump.dump.Main`

## Как запустить сервис
* Запустить `com.voi.wikidump.api.Main`
* Пока можно потыкаться по пути /ping/status, чтобы проверить, что сервис работает
---
Необходимо иметь запущенную локально MongoDB на стандартных настройках с именем базы `wikidump_db`. Эти требования можно переопределить в `com.voi.wikidump.MongoCreation`

## What is coming
* Поиск в MongoDB
* Индекс в MongoDB
* Web API
* (Де)Сериализация данных в JSON/BSON корректно

## What is coming 2.0
* Поддержка MySql
* pretty Web API
