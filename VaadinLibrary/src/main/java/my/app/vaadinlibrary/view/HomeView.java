package my.app.vaadinlibrary.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("")
public class HomeView extends AppLayout {

    public HomeView() {
        DrawerToggle toggle = new DrawerToggle();
        SideNav nav = getSideNav();
        VerticalLayout layout = new VerticalLayout();

        H1 title = new H1("Библиотека");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        layout.add("Just string");


        addToDrawer(scroller);
        addToNavbar(toggle, title);
        setContent(layout);
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Главная", "/",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Читатели", "/readers",
                        VaadinIcon.USER_HEART.create()),
                new SideNavItem("Книги", "/books", VaadinIcon.LIST.create()),
                new SideNavItem("Выдачи", "/issues", VaadinIcon.EXCHANGE.create())
        );
        return sideNav;
    }
}
