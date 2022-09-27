package user.service.controller;

import user.service.model.User;
import user.service.service.UserService;
import user.service.dto.request.UserRequestDto;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public void shouldReturnAllUsersWithDateBirthBetweenTwoValues() {
        LocalDate from = LocalDate.of(1940, 10, 30);
        LocalDate to = LocalDate.of(2004, 10, 30);
        User user = new User();
        user.setId(32L);
        user.setFirstName("Bob");
        user.setLastName("Bee");
        user.setEmail("bob@gmail.com");
        user.setAddress("Kyiv");
        user.setBirthDate(LocalDate.of(1990, 12, 8));

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user);

        Mockito.when(userService.findAllByBirthDateBetween(from, to)).thenReturn(mockUsers);

        RestAssuredMockMvc.given()
                .queryParam("from", from)
                .queryParam("to", to)
                .when()
                .get("/users")
                .then()
                .body("size()", Matchers.equalTo(1))
                .body("[0]id", Matchers.equalTo(32))
                .body("[0]firstName", Matchers.equalTo("Bob"))
                .body("[0]secondName", Matchers.equalTo("Bee"))
                .body("[0]address", Matchers.equalTo("Kyiv"))
                .body("[0]email", Matchers.equalTo("bob@gmail.com"))
                .body("[0]birthDate", Matchers.equalTo(LocalDate.of(1990, 12, 8)));

    }

    @Test
    public void shouldCreateUser() {
        User userToSave = new User();
        userToSave.setFirstName("Alice");
        userToSave.setLastName("Bee");
        userToSave.setAddress("Kyiv");
        userToSave.setEmail("alice@gmail.com");
        userToSave.setBirthDate(LocalDate.of(1888, 12, 10));

        User user = new User();
        user.setId(32L);
        user.setFirstName("Alice");
        user.setLastName("Bee");
        user.setAddress("Kyiv");
        user.setEmail("alice@gmail.com");
        user.setBirthDate(LocalDate.of(1888, 12, 10));

        Mockito.when(userService.save(userToSave)).thenReturn(user);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(userToSave.getFirstName());
        userRequestDto.setLastName(userToSave.getLastName());
        userRequestDto.setEmail(userToSave.getEmail());
        userRequestDto.setAddress(userToSave.getAddress());
        userRequestDto.setBirthDate(userToSave.getBirthDate());

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(32))
                .body("firstName", Matchers.equalTo("Alice"))
                .body("secondName", Matchers.equalTo("Bee"))
                .body("address", Matchers.equalTo("Kyiv"))
                .body("email", Matchers.equalTo("alice@gmail.com"))
                .body("birthDate", Matchers.equalTo(LocalDate.of(1888, 12, 10)));
    }

    @Test
    public void shouldUpdateUser() {
        User userToUpdate = new User();
        userToUpdate.setFirstName("Alice");
        userToUpdate.setLastName("Bee");
        userToUpdate.setAddress("Kyiv");
        userToUpdate.setEmail("alice@gmail.com");
        userToUpdate.setBirthDate(LocalDate.of(1888, 12, 10));

        User user = new User();
        user.setId(31L);
        user.setFirstName("Bob");
        user.setLastName("Bee");
        user.setAddress("Lviv");
        user.setEmail("bob@gmail.com");
        user.setBirthDate(LocalDate.of(1889, 12, 10));

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
                .put("/users")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(31))
                .body("firstName", Matchers.equalTo("Bob"))
                .body("secondName", Matchers.equalTo("Bee"))
                .body("address", Matchers.equalTo("Lviv"))
                .body("email", Matchers.equalTo("bob@gmail.com"))
                .body("birthDate", Matchers.equalTo(LocalDate.of(1889, 12, 10)));

    }

    @Test
    public void shouldUpdateAddress() {
        User userToSave = new User();
        userToSave.setFirstName("Alice");
        userToSave.setLastName("Bee");
        userToSave.setAddress("Kyiv");
        userToSave.setEmail("alice@gmail.com");
        userToSave.setBirthDate(LocalDate.of(1888, 12, 10));

        User user = new User();
        user.setId(32L);
        user.setFirstName("Alice");
        user.setLastName("Bee");
        user.setAddress("Lviv");
        user.setEmail("alice@gmail.com");
        user.setBirthDate(LocalDate.of(1888, 12, 10));

        Mockito.when(userService.save(userToSave)).thenReturn(user);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(userToSave.getFirstName());
        userRequestDto.setLastName(userToSave.getLastName());
        userRequestDto.setEmail(userToSave.getEmail());
        userRequestDto.setAddress(userToSave.getAddress());
        userRequestDto.setBirthDate(userToSave.getBirthDate());

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .put("/users")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(32))
                .body("firstName", Matchers.equalTo("Alice"))
                .body("secondName", Matchers.equalTo("Bee"))
                .body("address", Matchers.equalTo("Lviv"))
                .body("email", Matchers.equalTo("alice@gmail.com"))
                .body("birthDate", Matchers.equalTo(LocalDate.of(1888, 12, 10)));
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
        Mockito.when(userService.deleteById((32L))).thenReturn("SUCCESS");
        mockMvc.perform(MockMvcRequestBuilders.delete("/{id}", 32L))
                .andExpect(status().isOk());
    }
}