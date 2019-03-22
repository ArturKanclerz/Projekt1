package ProjektZespolowySpring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {

    @Autowired
    private TestRestTemplate template;

    private List<String> urlsBorrowsUsers = List.of("/borrows", "/users", "/borrows/", "/users/");
    private List<String> urlsBooksAuthorsBorrows = List.of("/books", "/authors", "/borrows",
            "/books/", "/authors/", "/borrows/");
    private List<String> urlsUsers = List.of("/users", "/users/");

    @Test
    public void testAnonymousGetBorrowsUsers() {
        for (var url : urlsBorrowsUsers) {
            HttpStatus status = getStatus(RequestEntity.get(URI.create(url)).build());
            assertEquals(HttpStatus.UNAUTHORIZED, status);
        }
    }

    @Test
    public void testUserGetBorrowsUsers() {
        for (var url : urlsBorrowsUsers) {
            HttpStatus status = getStatus("user", "user", RequestEntity.get(URI.create(url)).build());
            assertEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAdminGetBorrowsUsers() {
        for (var url : urlsBorrowsUsers) {
            HttpStatus status = getStatus("admin", "admin", RequestEntity.get(URI.create(url)).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAnonymousPostBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows) {
            HttpStatus status = getStatus(RequestEntity.post(URI.create(url)).build());
            assertEquals(HttpStatus.UNAUTHORIZED, status);
        }
    }

    @Test
    public void testUserPostBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows) {
            HttpStatus status = getStatus("user", "user", RequestEntity.post(URI.create(url)).build());
            assertEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAdminPostBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows) {
            HttpStatus status = getStatus("admin", "admin", RequestEntity.post(URI.create(url)).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAnonymousPutBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows.subList(0, 3)) {
            HttpStatus status = getStatus(RequestEntity.put(URI.create(url + "/1")).build());
            assertEquals(HttpStatus.UNAUTHORIZED, status);
        }
    }

    @Test
    public void testUserPutBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows.subList(0, 3)) {
            HttpStatus status = getStatus("user", "user", RequestEntity.put(URI.create(url + "/1")).build());
            assertEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAdminPutBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows.subList(0, 3)) {
            HttpStatus status = getStatus("admin", "admin", RequestEntity.put(URI.create(url + "/1")).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAnonymousDeleteBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows.subList(0, 3)) {
            HttpStatus status = getStatus(RequestEntity.delete(URI.create(url + "/1")).build());
            assertEquals(HttpStatus.UNAUTHORIZED, status);
        }
    }

    @Test
    public void testUserDeleteBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows.subList(0, 3)) {
            HttpStatus status = getStatus("user", "user", RequestEntity.delete(URI.create(url + "/1")).build());
            assertEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAdminDeleteBooksAuthorsBorrows() {
        for (var url : urlsBooksAuthorsBorrows.subList(0, 3)) {
            HttpStatus status = getStatus("admin", "admin", RequestEntity.delete(URI.create(url + "/1")).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAnonymousPostUsers() {
        for (var url : urlsUsers) {
            HttpStatus status = getStatus(RequestEntity.post(URI.create(url)).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testUserPostUsers() {
        for (var url : urlsUsers) {
            HttpStatus status = getStatus("user", "user", RequestEntity.post(URI.create(url)).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    @Test
    public void testAdminPostUsers() {
        for (var url : urlsUsers) {
            HttpStatus status = getStatus("admin", "admin", RequestEntity.post(URI.create(url)).build());
            assertNotEquals(HttpStatus.UNAUTHORIZED, status);
            assertNotEquals(HttpStatus.FORBIDDEN, status);
        }
    }

    private HttpStatus getStatus(RequestEntity<?> requestEntity) {
        return template.exchange(requestEntity, String.class).getStatusCode();
    }

    private HttpStatus getStatus(String username, String password, RequestEntity<?> requestEntity) {
        return template.withBasicAuth(username, password).exchange(requestEntity, String.class).getStatusCode();
    }

}
