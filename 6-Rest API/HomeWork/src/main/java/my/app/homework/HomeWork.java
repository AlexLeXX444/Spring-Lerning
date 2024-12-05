package my.app.homework;

public class HomeWork {

    /**
     * +++ 1. Подключить OpenAPI 3 и swagger к проекту с библиотекой
     * +++ 2. Описать все контроллеры, эндпоинты и возвращаемые тела с помощью аннотаций OpenAPI 3
     * 3. В качестве результата, необходимо прислать скриншот(ы) страницы swagger (с ручками)
     *
     * Доп. задание (сдавать не нужно):
     * придумать какие-то доменные сервисы (по типу библиотеки и заметок) и попытаться спроектировать его API.
     */

    /**
     * Пользователь
     * CRUD...
     *
     * Заметки
     * 1. Создать заметку
     * 2. Прочитать заметку
     * 3. Обновить заметку
     * 4. Удалить заметку
     * CRUD (Create Read Update Delete)
     * 5. Загрузить все заметки
     *
     * Теги
     * 1. ... CruD над тегами
     *
     * TODO: Научиться теги мапить на заметки
     */

    /**
     *  User(id, name, ...)
     *  {
     *    "id": 5,
     *    "name": "FirstUser",
     *    ...
     *  }
     *  POST /user {BODY} - создать пользователя
     *  GET /user/{id} - получить пользователя по ID
     *  ... еще put на изменение, delete на удаление
     *
     *  GET /note/{id} - получить описание заметки
     *  {
     *    "id": 1,
     *    "title": "first",
     *    "content": "content of note",
     *    "createdAt": "2024-01-25T14:54",
     *    "deletedAt": "2023-02-15T14:33",
     *    "userId": 3,
     *    "tags": [
     *      "f", "important", "draft"
     *    ],
     *  }
     *
     *  GET /note?page=0&size=5 - получить список всех заметок
     *  {
     *    "notes": [
     *      {
     *       "id": 1,
     *       "title": "first",
     *       "createdAt": "2024-01-25T14:54",
     *       "tags": [1, 3, 4],
     *      },
     *      ...
     *    ],
     *    "paging": {
     *      "page": 0,
     *      "size": 5,
     *      "total": 120,
     *      "totalPages": 3
     *    }
     *  }
     *
     *  Tag
     *  {
     *    "title": "important",
     *    "color": "#FFAAEF",
     *    "description": "description",
     *    "userId": 3,
     *  }
     *
     *
     *  1. Получить список заметок по тегу
     *  -- GET /tag/{id}/note -> 404 (Not Found)
     *  GET /notes?tag={tagId} -> 204 (No Content)
     *
     *  2. Привязать тег к заметке
     *  PUT /note/{noteId}/tag/{tagId}
     *
     *  3. Получить заметки для пользователя
     *  Во всех запросах должен быть заголовок Authorization: Bearer tokenValue
     *
     *
     */
}
