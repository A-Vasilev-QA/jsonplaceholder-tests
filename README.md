# Задание по jsonplaceholder.typicode.com

### Шаги для запуска проекта

* Склонировать проект
* Запустить тесты
```bash
gradle clean test
```
* Открыть allure-отчёт
```bash
gradle allureServe
```

* Три теста упадут из-за того, что у jsonplaceholder нарушена логика (например, не создаются новые посты), это ожидаемо
* Для запуска второй части задания просто выполнить run из класса PrintWordsMap