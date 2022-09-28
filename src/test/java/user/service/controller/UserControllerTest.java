package user.service.controller;

import java.time.LocalDate;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import user.service.dto.request.UserRequestDto;
import user.service.model.User;
import user.service.service.UserService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void shouldCreateUser() {
        User userToCreate = new User();
        userToCreate.setAddress("Kyiv");
        userToCreate.setBirthDate(LocalDate.of(1988, 12, 10));
        userToCreate.setEmail("alice1@gmail.com");
        userToCreate.setFirstName("Alice");
        userToCreate.setLastName("Bee");

        User user = new User();
        user.setId(2L);
        user.setAddress("Kyiv");
        user.setBirthDate(LocalDate.of(1988, 12, 10));
        user.setEmail("alice1@gmail.com");
        user.setFirstName("Alice");
        user.setLastName("Bee");

        Mockito.when(userService.save(userToCreate)).thenReturn(user);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(userToCreate.getFirstName());
        userRequestDto.setLastName(userToCreate.getLastName());
        userRequestDto.setEmail(userToCreate.getEmail());
        userRequestDto.setAddress(userToCreate.getAddress());
        userRequestDto.setBirthDate(userToCreate.getBirthDate());

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/users", user.getId(), userRequestDto)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("address", Matchers.equalTo("Kyiv"))
                .body("birthDate", Matchers.equalTo("1988-12-10"))
                .body("email", Matchers.equalTo("alice1@gmail.com"))
                .body("firstName", Matchers.equalTo("Alice"))
                .body("lastName", Matchers.equalTo("Bee"));
    }

    @Test
    public void shouldUpdateUser() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setFirstName("Alice");
        userToUpdate.setLastName("Bee");
        userToUpdate.setAddress("Kyiv");
        userToUpdate.setEmail("alice@gmail.com");
        userToUpdate.setBirthDate(LocalDate.of(1988, 12, 10));

        User user = new User();
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Bee");
        user.setAddress("Kyiv");
        user.setEmail("alice@gmail.com");
        user.setBirthDate(LocalDate.of(1988, 12, 10));

        Mockito.when(userService.save(userToUpdate)).thenReturn(user);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(userToUpdate.getFirstName());
        userRequestDto.setLastName(userToUpdate.getLastName());
        userRequestDto.setEmail(userToUpdate.getEmail());
        userRequestDto.setAddress(userToUpdate.getAddress());
        userRequestDto.setBirthDate(userToUpdate.getBirthDate());

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .put("users/{id}", user.getId(), userRequestDto)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("firstName", Matchers.equalTo("Alice"))
                .body("lastName", Matchers.equalTo("Bee"))
                .body("address", Matchers.equalTo("Kyiv"))
                .body("email", Matchers.equalTo("alice@gmail.com"))
                .body("birthDate", Matchers.equalTo("1988-12-10"));
    }

    @Test
    public void shouldReturnAllUsersWithDateBirthBetweenTwoValues() {
        LocalDate from = LocalDate.of(1940, 10, 30);
        LocalDate to = LocalDate.of(2004, 10, 30);

        User user = new User();
        user.setId(3L);
        user.setFirstName("Alice");
        user.setLastName("Bee");
        user.setAddress("Kyiv");
        user.setEmail("alice2@gmail.com");

        user.setBirthDate(LocalDate.of(1988, 12, 10));
        List<User> mockUsers = List.of(user);

        Mockito.when(userService.findAllByBirthDateBetween(from, to)).thenReturn(mockUsers);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .queryParam("from", from)
                .queryParam("to", to)
                .when()
                .get("/users/by-birthdate")
                .then()
                .body("[0]id", Matchers.equalTo(3))
                .body("[0]firstName", Matchers.equalTo("Alice"))
                .body("[0]lastName", Matchers.equalTo("Bee"))
                .body("[0]address", Matchers.equalTo("Kyiv"))
                .body("[0]email", Matchers.equalTo("alice2@gmail.com"))
                .body("[0]birthDate", Matchers.equalTo("1988-12-10"));
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
        User userToDelete = new User();
        userToDelete.setId(1L);
        userToDelete.setFirstName("Alice");
        userToDelete.setLastName("Bee");
        userToDelete.setAddress("Kyiv");
        userToDelete.setEmail("alice3@gmail.com");
        userToDelete.setBirthDate(LocalDate.of(1988, 12, 10));

        Mockito.when(userService.deleteById((32L))).thenReturn("SUCCESS");
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userToDelete.getId(), userToDelete))
                .andExpect(status().isOk());
    }
}
