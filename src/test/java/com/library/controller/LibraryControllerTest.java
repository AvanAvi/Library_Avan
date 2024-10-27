package com.library.controller;

import com.library.service.LibraryService;
import com.library.exception.LibraryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void shouldBorrowBookSuccessfully() throws Exception {
        mockMvc.perform(post("/api/library/borrow")
                .param("studentId", "1")
                .param("isbn", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book borrowed successfully"));

        verify(libraryService).borrowBook("1", "123");
    }

    @Test
    void shouldHandleBorrowBookError() throws Exception {
        doThrow(new LibraryException("Book not found"))
                .when(libraryService).borrowBook("1", "123");

        mockMvc.perform(post("/api/library/borrow")
                .param("studentId", "1")
                .param("isbn", "123"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book not found"));
    }

    @Test
    void shouldReturnBookSuccessfully() throws Exception {
        mockMvc.perform(post("/api/library/return")
                .param("studentId", "1")
                .param("isbn", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully"));

        verify(libraryService).returnBook("1", "123");
    }

    @Test
    void shouldHandleReturnBookError() throws Exception {
        doThrow(new LibraryException("Student not found"))
                .when(libraryService).returnBook("1", "123");

        mockMvc.perform(post("/api/library/return")
                .param("studentId", "1")
                .param("isbn", "123"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Student not found"));
    }
}