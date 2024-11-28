# Основы разработки информационных систем

### https://git.kpfu.ru/

https://git.kpfu.ru/KSEnikeev/dis301.git

## 18.11.2024
  ## Шаблонизатор
  - Шаблон - файл, содержащий текстовые данные для клиента, параметры для подстановки представлены переменными 
    типа ${param_name} или #{param_name}
  - Функции шаблонизатора:
    - найти и загрузить соответствующий файл, 
    - заменить переменные на значения, 
    - отправить клиенту
  - Для обработки шаблона используем специальный сервлет, который обычно ассоциирован с расширением файла
    ```
    @WebServlet("*.thtml")
    public class TemplateProcessorServlet extends HttpServlet {
      // ...
    }
    ```
  
  Задание 4:  
  
  Доработать шаблонизатор:
   1. Реализовать поиск и замену параметров в шаблоне на значения
   2. Подготовить 2 страницы с разными шаблонами
   3. Выводить содержимое страницы с учетом параметров (значение параметра можно получить через request.getParameter(paramName)


## 15.11.2024

### Сервлеты
  - Интерфейс Servlet
  - Базовая реализация HttpServlet (наличие методов doGet, doPost, ....)


## 13.11.2024

### Отдача динамического контента
- Обработка параметров запроса
  - пример GET запроса с параметрами: http://localhost:5000/resource_uri?param1_key=param1_value&param2_key=param2_value
    ```  
      GET /resource_uri?param1_key=param1_value&param2_key=param2_value HTTP/1.1\r\n
      host: localhost:5000\r\n\
    ```

  - пример POST запроса с параметрами: http://localhost:5000/resource_uri
    ```
    POST /resource_uri HTTP/1.1\r\n
    host: localhost:5000\r\n\
    Content-Type: application/x-www-form-urlencoded\r\n
    Content-Length: 46\r\n
    \r\n
    param1_key=param1_value&param2_key=param2_value
    ```
    Параметры передаются в теле запроса

- Реестр ресурсов:
  - http://localhost:5000/resource1_uri
  - http://localhost:5000/resource2_uri
  - http://localhost:5000/file.html
  - Map<String, IResourceHandler> resources
  ```
  class ResponceContent {
    String mimeType; //
    byte[] content; // html, png, jpg, json
  }
  
  interface IResourceHandler {
      ResponceContent handle(Map<String, String> params);
  }
  ```  

  - Задание 3: Реализовать динамическое формирование веб-страниц в зависимости от параметров запроса
    - Обрабатываем запрос:
      - Получаем имя ресурса
      - Получаем параметры
      - ищем обработчик в структуре resources, если находим - вызываем handle с параметрами запроса,
        иначе пытаемся найти файл



## 11.11.2024

- Протокол HTTP

- Задание 2: разработка web сервера
  - Создаем класс сервера для приема соединений:
    - ServerSocket server = new ServerSocket(SERVER_PORT);
    - Socket clientSocket = server.accept();
    - handle(clientSocket)
  - Класс обработчик запросов
    - Прочитать заголовок (считываем пока не встретим пустую строку)
    - Отдельно парсим 1-ю строку (Метод URI_ресурса Версия_протокола), проводим проверки <-
    - Парсим остальные заголовки (Имя: значение)
    - Если находим Content-Length, значит есть тело - считываем его
  - Класс, формирующий ответ сервера
    - Обрабатываем GET, возвращаем текст html странички (можно хранить в строковой переменной,
      и передавать эту строку через outputStream сокета)
    - Обрабатываем GET, возвращаем html страничку из файла (http://localhost:5000/example.html),
      файл надо считать с диска, и прередать через outputStream сокета
    - Готовим и отправляем заголовки ответа
    - После отправки заголовка отправляем контент

## 1.11.2024

- Протокол TCP/IP
- Socket (ClientSocket, ServerSocket)
- Задание 1:
  Создать чат-бот в архитектуре клиент-сервер. Клиент отправляет вопрос, сервер дает "осмысленный" ответ (не менее 5 вариантов вопросов)
  Сервер должен остановиться при появлении команды "Exit".
