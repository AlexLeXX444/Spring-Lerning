package my.app.issueservice.service;

import lombok.RequiredArgsConstructor;
import my.app.issueservice.dto.BookDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

    private final String BASE_URL = "http://localhost:8081/api/book";

    private final RestTemplate restTemplate;

    public BookDto getBookById(long id) {
        try {
        String url = BASE_URL + "/" + id;
        ResponseEntity<BookDto> response = restTemplate.getForEntity(url, BookDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExistsById(long id) {
        return this.getBookById(id) != null;
    }

    public void updateBookCount(long id, int count) {
        String url = BASE_URL + "/" + id + "/count?count=" + count;
        try {
            ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.POST, null, BookDto.class);
            System.out.println("Book count was updated");
        } catch (Exception e) {
            return;
        }
    }

    public int getBookCount(long id) {
        BookDto response = this.getBookById(id);
        if (response != null) {
            return response.getCount();
        }
        return -1;
    }
}
