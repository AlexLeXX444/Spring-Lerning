package my.app.vaadinlibrary.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import my.app.vaadinlibrary.service.BookService;

@Route("book")
public class BookView extends VerticalLayout {

    private final BookService bookService;

    public BookView(BookService bookService) {
        this.bookService = bookService;

        add(new H1("Welcome to your new application"));
        add(new Paragraph(bookService.getAll().toString()));
    }
}
