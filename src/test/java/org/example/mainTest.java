package org.example;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {

    @BeforeEach
    void setUp() {
        ToDoList.tasks.clear();
        File file = new File(ToDoList.FILE_NAME);
        if (file.exists()) file.delete();
    }

    @Test
    @DisplayName("Добавление задачи в список")
    void testAddTask() {
        Scanner scanner = new Scanner("Сходить в спортзал\n");
        ToDoList.addTask(scanner);

        assertEquals(1, ToDoList.tasks.size());
        assertEquals("Сходить в спортзал", ToDoList.tasks.get(0).getTitle());
    }

    @Test
    @DisplayName("Удаление задачи из списка")
    void testDeleteTask() {
        ToDoList.tasks.add(new ToDoList.Task("Купить молоко"));

        Scanner scanner = new Scanner("1\n");
        ToDoList.deleteTask(scanner);

        assertTrue(ToDoList.tasks.isEmpty());
    }

    @Test
    @DisplayName("Редактирование задачи")
    void testEditTask() {
        ToDoList.tasks.add(new ToDoList.Task("Старое название"));

        Scanner scanner = new Scanner("1\nНовое название\n");
        ToDoList.editTask(scanner);

        assertEquals("Новое название", ToDoList.tasks.get(0).getTitle());
    }

    @Test
    @DisplayName("Сохранение и загрузка из файла")
    void testFilePersistence() {
        ToDoList.tasks.add(new ToDoList.Task("Важная задача"));

        ToDoList.saveTasks();
        ToDoList.tasks.clear();
        ToDoList.loadTasks();

        assertEquals(1, ToDoList.tasks.size());
        assertEquals("Важная задача", ToDoList.tasks.get(0).getTitle());
    }
}
