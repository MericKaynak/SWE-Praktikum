package com.backend.backend;

import com.backend.terminplanungsassitent.TerminplanungsassistentApplication;
import com.backend.terminplanungsassitent.RESTController.TerminplanController;
import com.backend.terminplanungsassitent.databaseClasses.Lehrperson;
import com.backend.terminplanungsassitent.databaseClasses.LehrpersonRepository;
import com.backend.terminplanungsassitent.exceptions.LehrpersonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TerminplanungsassistentApplication.class)
@AutoConfigureMockMvc
public class TerminplanControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private LehrpersonRepository lehrpersonRepository;

	@InjectMocks
	private TerminplanController lehrpersonController;

	private Lehrperson sampleLehrperson;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		sampleLehrperson = new Lehrperson();
		sampleLehrperson.setId(1);
		sampleLehrperson.setName("John Doe");
		sampleLehrperson.setEmail("john.doe@example.com");
		sampleLehrperson.setRolle("Teacher");
		sampleLehrperson.setWochenarbeitsstunden(20);

		when(lehrpersonRepository.findById(1L)).thenReturn(java.util.Optional.of(sampleLehrperson));
	}

	@Test
	void testFindLPById() throws Exception {
		mockMvc.perform(get("/fetchlp/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", Matchers.equalTo("John Doe")))
				.andExpect(jsonPath("$.email", Matchers.equalTo("john.doe@example.com")))
				.andExpect(jsonPath("$.rolle", Matchers.equalTo("Teacher")))
				.andExpect(jsonPath("$.wochenarbeitsstunden", Matchers.equalTo(20)));
	}
}
