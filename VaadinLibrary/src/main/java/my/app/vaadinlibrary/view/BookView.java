package my.app.vaadinlibrary.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.*;
import com.vaadin.flow.router.Route;
import my.app.vaadinlibrary.model.Book;
import my.app.vaadinlibrary.service.BookService;

import java.util.List;

@Route("books")
public class BookView extends MainView {

    private final BookService bookService;

    public BookView (BookService bookService) {
        this.bookService = bookService;

        Tab allBooks = new Tab(new Span("Все книги"), createBadge((int) bookService.count()));
        Tab onLibrary = new Tab(new Span("В наличии"), createBadge((int) bookService.countByCount()));
        Tab onReaders = new Tab(new Span("Выданы"), createBadge(5));

        TabSheet tabs = new TabSheet();
        tabs.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        tabs.add(allBooks, allBooksGrid());
        tabs.add(onLibrary, new Div());
        tabs.add(onReaders, new Div());

        setContent(tabs);
    }

    private Span createBadge(int value) {
        Span badge = new Span(String.valueOf(value));
        badge.getElement().getThemeList().add("badge small contrast");
        badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        return badge;
    }

    private Div allBooksGrid() {
        Div main = new Div();
        main.setWidthFull();
        main.setHeightFull();

        Grid<Book> allBooks = new Grid<>(Book.class, false);
        allBooks.setClassName("force-focus-outline");
        allBooks.addColumn(Book::getAuthor).setKey("bookAuthor")
                .setHeader("Автор").setSortable(true);
        allBooks.addColumn(Book::getName).setKey("bookName")
                .setHeader("Название").setSortable(true);
        allBooks.addColumn(Book::getCount).setKey("bookCount")
                .setHeader("Наличие");

        List<Book> books = bookService.getAll();
        allBooks.setItems(books);

        main.add(allBooks);
        return main;
    }
}
