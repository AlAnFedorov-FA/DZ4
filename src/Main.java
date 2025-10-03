import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Интерфейс для наблюдателей
interface Observer {
     // Методы для изменения состояний наблюдаемого объекта: newState, addedText - новые состояния
    void update(String newState);
    void onTextAppended(String addedText);
}

// Основной класс, реализующий шаблон Наблюдатель
class ObservableStringBuilder {
    private StringBuilder builder;// StringBuilder для хранения
    private List<Observer> observers; // Список всех наблюдателей
    private int notificationCounter; // Счетчик уведомлений

   // Конструктор класса
    ObservableStringBuilder() {
        this.builder = new StringBuilder();
        this.observers = new ArrayList<>();
        this.notificationCounter = 0; // Начальное значение счетчика
    }

// метод для чередования сообщений наблюдателей
    private void notifyObservers(String newState, String addedText) {
        notificationCounter++; // Увеличиваем счетчик
        for (Observer observer : observers) {
            if (notificationCounter % 2 == 1) { // Нечетное число - первый метод
                observer.update(newState);
            } else { // Четное число - второй метод
                observer.onTextAppended(addedText);
            }
        }
    }

    // Метод для добавления нового наблюдателя
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // метод для удаления наблюдателя
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    // Метод для очистки билдера
    public void clear() {
        builder.setLength(0);
    }
    // метод для уведомления всех наблюдателей
  private void notifyObservers() {
        String currentState = builder.toString();
        for (Observer observer : observers) {
            observer.update(currentState);
        }
    }

    // Делегирует метод append стандартного StringBuilder и уведомляет наблюдателей об изменении
    public ObservableStringBuilder append(String str) {
        builder.append(str);
        notifyObservers(builder.toString(), str);
        return this;
    }

    /**
     * Метод для вставки текста (offset - место вставки, str - что вставляем)
    public ObservableStringBuilder insert(int offset, String str) {
        builder.insert(offset, str);
        notifyObservers(builder.toString(), str);
        return this;
    }
    * В текущей реализации не используется */

    /**
     * Метод для удаления текста (start - начальная позиция удаления, end конечная позиция удаления)
    public ObservableStringBuilder delete(int start, int end) {
        builder.delete(start, end);
        notifyObservers(builder.toString(), "");
        return this;
    }
     * В текущей реализации не используется */

    /**
     * Текущее состояние StringBuilder в виде строки
    public String toString() {
        return builder.toString();
    }
      В текущей реализации не используется */
}

// Реализация
public class Main {
    private static ObservableStringBuilder osb;
    private static Scanner scanner;

    public static void main(String[] args) {
        staff(); // наблюдатели-сотрудники
        showMenu(); // консольное меню пользователя
    }
    private static void staff() {
        osb = new ObservableStringBuilder();
        scanner = new Scanner(System.in);

        // Первый наблюдатель-сотрудник
        Observer staff1 = new Observer() {
            @Override
            public void update(String newState) {
                System.out.println("Сотрудник 1: Я ушёл на обед, пусть заявку -" + newState + "- возьмёт кто-то другой");
            }

            @Override
            public void onTextAppended(String addedText) {
                System.out.println("Сотрудник 1: Давай попробуем!");
            }
        };
        osb.addObserver(staff1);

        // Второй наблюдатель
        Observer staff2 = new Observer() {
            @Override
            public void update(String newState) {
                System.out.println("Сотрудник 2: Любой каприз за ваши деньги!");
            }

            @Override
            public void onTextAppended(String addedText) {
                System.out.println("Сотрудник 2: Я ушёл в курилку, пусть заявку -" + addedText + "- возьмёт кто-то другой");
            }
        };
        osb.addObserver(staff2);
    }

    private static void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nНовая заявка");
            System.out.println("Отправить заявку в работу = 1");
            System.out.println("Зачем людей беспокоить, Выйти = 2");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    appendText();
                    break;
                case "2":
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор!");
            }
        }

        System.out.println("Правильный выбор! Пока!");
    }
// Метод для взаимодействия с пользователем и запуска обработки заявко
    private static void appendText() {
        System.out.print("Введите текст Заявки: ");
        String text = scanner.nextLine();
        osb.clear(); // Очищаем перед добавлением новой заявки
        osb.append(text);
    }
}