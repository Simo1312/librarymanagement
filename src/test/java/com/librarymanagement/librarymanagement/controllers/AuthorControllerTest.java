package com.librarymanagement.librarymanagement.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.librarymanagement.dto.AuthorDto;
import com.librarymanagement.librarymanagement.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc
class AuthorControllerTest {

  /*  @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AuthorService authorService;

    private static ObjectMapper mapper = new ObjectMapper();

    private AuthorDto testAuthor;

    @BeforeEach
    void setUp() {
        testAuthor = new AuthorDto();
        testAuthor.setId(1L);
        testAuthor.setName("Test Author");
        testAuthor.setBiography("Test Biography");
    }

    @Nested
    @DisplayName("GET /author/{id}")
    class FindAuthorById {
        @Test
        @DisplayName("should return author when exists")
        void shouldReturnAuthorWhenExists() throws Exception {
            when(authorService.findAuthorById(1L)).thenReturn(testAuthor);

            mvc.perform(get("/author/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Test Author"))
                    .andExpect(jsonPath("$.biography").value("Test Biography"));
        }

        @Test
        @DisplayName("should return 404 when author not found")
        void shouldReturn404WhenNotFound() throws Exception {
            when(authorService.findAuthorById(99L))
                    .thenThrow(new NoSuchElementException("Author not found"));

            mvc.perform(get("/author/99")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/admin/author/add")
    class AddAuthor {
        @Test
        @DisplayName("should add author successfully")
        @WithMockUser(roles = "ADMIN")
        void shouldAddAuthorSuccessfully() throws Exception {
            when(authorService.addAuthor(any(AuthorDto.class))).thenReturn(testAuthor);

            mvc.perform(post("/api/admin/author/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(testAuthor)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Test Author"));
        }

        @Test
        @DisplayName("should return 403 for non-admin users")
        @WithMockUser(roles = "USER")
        void shouldReturn403ForNonAdmin() throws Exception {
            mvc.perform(post("/api/admin/author/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(testAuthor)))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("PUT /api/admin/author/update/{id}")
    class UpdateAuthor {
        @Test
        @DisplayName("should update author successfully")
        @WithMockUser(roles = "ADMIN")
        void shouldUpdateAuthorSuccessfully() throws Exception {
            when(authorService.updateAuthor(eq(1L), any(AuthorDto.class))).thenReturn(testAuthor);

            mvc.perform(put("/api/admin/author/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(testAuthor)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Test Author"));
        }

        @Test
        @DisplayName("should return 404 when author not found")
        @WithMockUser(roles = "ADMIN")
        void shouldReturn404WhenNotFound() throws Exception {
            when(authorService.updateAuthor(eq(99L), any(AuthorDto.class)))
                    .thenThrow(new NoSuchElementException("Author not found"));

            mvc.perform(put("/api/admin/author/update/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(testAuthor)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/admin/author/delete/{id}")
    class DeleteAuthor {
        @Test
        @DisplayName("should delete author successfully")
        @WithMockUser(roles = "ADMIN")
        void shouldDeleteAuthorSuccessfully() throws Exception {
            when(authorService.deleteAuthor(1L)).thenReturn(testAuthor);

            mvc.perform(post("/api/admin/author/delete/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("should return 404 when author not found")
        @WithMockUser(roles = "ADMIN")
        void shouldReturn404WhenNotFound() throws Exception {
            when(authorService.deleteAuthor(99L))
                    .thenThrow(new NoSuchElementException("Author not found"));

            mvc.perform(post("/api/admin/author/delete/99")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }*/
}
