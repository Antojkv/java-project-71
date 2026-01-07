#Вычислитель отличий (java)

[![JavaCI](https://github.com/Antojkv/java-project-71/actions/workflows/build.yml/badge.svg)](https://github.com/Antojkv/java-project-71/actions/workflows/build.yml)
[![Actions Status](https://github.com/Antojkv/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Antojkv/java-project-71/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Antojkv_java-project-71&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Antojkv_java-project-71)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Antojkv_java-project-71&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Antojkv_java-project-71)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Antojkv_java-project-71&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Antojkv_java-project-71)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Antojkv_java-project-71&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Antojkv_java-project-71)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Antojkv_java-project-71&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Antojkv_java-project-71)

### Вычислитель отличий – программа, определяющая разницу между двумя структурами данных.

#### Поддержка входных форматов: yaml и json
#### Генерация отчета в виде plain text, stylish и json

### Установка

1. **Клонируйте репозиторий:**

git clone https://github.com/Antojkv/java-project-71.git

cd java-project-71/app

2. **Способы запуска:**

###### Запуск приложения напрямую
make run ARGS="-f json/stylish/plain filepath1.json filepath2.json"

###### Сборка проекта
make build

###### Запуск после сборки 
make run-dist ARGS="filepath1.json filepath2.json"

###### Очистка собранных файлов
make clean


### Аскинемы:
#### Запуск программы сравнения двух конфигурационных файлов:
##### .json: https://asciinema.org/a/Ww9aMIMH4f5XbSWkIhBDaYXjG
##### .yaml и .json: https://asciinema.org/a/PkqShxNuBDhOofbW0YskuCEHZ
##### Вложенные структуры (.yaml и .json) https://asciinema.org/a/ERFgZ0EP0mseZlYKkMaSulkqY
##### Форматтер "plain"  https://asciinema.org/a/ItEkMUC6Y0NFLAe9kuD4ZQuL0
##### Форматтер "json" https://asciinema.org/a/1BDcRU5skPNT7qZX 
