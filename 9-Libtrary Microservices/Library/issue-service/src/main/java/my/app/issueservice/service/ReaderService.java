package my.app.issueservice.service;

import lombok.RequiredArgsConstructor;
import my.app.issueservice.dto.ReaderDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final String BASE_URL = "http://localhost:8082/api/reader";

    private final RestTemplate restTemplate;

    public ReaderDto getReaderById(Long id) {
        try {
            String url = BASE_URL + "/" + id;
            ResponseEntity<ReaderDto> response = restTemplate.getForEntity(url, ReaderDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExistsById(long id) {
        return this.getReaderById(id) != null;
    }

    public void updateBookAmount(Long readerId, int bookAmount) {
        String url = BASE_URL + "/" + readerId + "/book-amount?bookAmount=" + bookAmount;
        try {
            ResponseEntity<ReaderDto> response = restTemplate.exchange(url, HttpMethod.POST, null, ReaderDto.class);
            System.out.println("Reader book amount was updated");
        } catch (HttpClientErrorException e) {
            return;
        }
    }

    public int getBookAmount(long readerId) {
        ReaderDto reader = this.getReaderById(readerId);
        if (reader != null) {
            return reader.getBookAmount();
        }
        return -1;
    }
}
